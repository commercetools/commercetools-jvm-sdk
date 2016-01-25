import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.String.format;

public class CodeTaglet implements Taglet {

    /**
     * Generates the String output for a tag
     * @param tag
     * @return
     */
    public String toString(Tag tag) {
        try {
            return getString(tag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private enum Position {
        START, IMPORTS, CODE
    }

    private String getString(final Tag tag) throws IOException {
        try {
            int pos = tag.text().indexOf("#");
            final boolean fullFileRequested = pos == -1;
            if (fullFileRequested) {
                pos = tag.text().length();
            }
            final String fullyQualifiedClassName = tag.text().substring(0, pos);
            final String partialFilePath = fullyQualifiedClassName.replace('.', '/').concat(".java");


            final File testFile = findFile(fullyQualifiedClassName, partialFilePath, tag);

            String imports = "";
            String res = "";
            if (fullFileRequested) {
                //partially from http://stackoverflow.com/a/326448
                final int fileLength = (int) testFile.length();
                final StringBuilder fileContents = new StringBuilder(fileLength);
                final StringBuilder importStatements = new StringBuilder(fileLength);
                String lineSeparator = System.getProperty("line.separator");
                try (Scanner scanner = new Scanner(testFile)) {
                    Position position = Position.START;
                    while (scanner.hasNextLine()) {
                        final String line = scanner.nextLine();
                        final String trimmedLine = line.trim();
                        if (position != Position.CODE && "".equals(trimmedLine)) {
                            //ignore
                        } else if (position == Position.START && trimmedLine.startsWith("package")) {
                            position = Position.IMPORTS;
                        } else if (position == Position.IMPORTS && trimmedLine.startsWith("import")) {
                            importStatements.append(line).append(lineSeparator);
                        } else if (position == Position.IMPORTS || position == Position.CODE) {
                            position = Position.CODE;
                            fileContents.append(line).append(lineSeparator);
                        } else {
                            throw new IllegalStateException("can't parse Java file");
                        }
                    }
                    res = fileContents.toString();
                    imports = importStatements.toString();
                }
            } else {
                final String testName = tag.text().substring(pos + 1);
                final Scanner scanner = new Scanner(testFile);
                List<String> lines = new ArrayList<>();
                boolean endFound = false;
            while(scanner.hasNext() && !endFound) {
                    String current = scanner.findInLine("(public|private|protected) .* " + testName + "\\(.*");
                    final boolean methodStartFound = current != null;
                    if (methodStartFound) {
                        scanner.nextLine();
                        do {
                            current = scanner.nextLine();
                            endFound = current.equals("    }") || current.contains("//end example parsing here");
                            if (!endFound) {
                                final String currentWithoutLeadingWhitespace = current.replaceFirst("        ", "");
                                lines.add(currentWithoutLeadingWhitespace);
                            }
                        } while (!endFound);
                    } else {
                        scanner.nextLine();
                    }
                }

                for (String s : lines) {
                    res += s + "\n";
                }
            }
            final String htmlEscapedBody = htmlEscape(res);
            if ("".equals(htmlEscapedBody)) {
                throw new RuntimeException("Empty example for " + tag.text());
            }
            final String htmlEscapedImports = htmlEscape(imports);
        final String tagId = tag.text().replaceAll("[^a-zA-Z0-9]","-");


            final String pathToGitHubTestFile = testFile.getAbsolutePath().replace(new File(".").getAbsoluteFile().getCanonicalPath(), "https://github.com/sphereio/sphere-jvm-sdk/blob/master");


            return "<div id=\"" + tagId + "%s\" class=code-example>"
                    + (fullFileRequested ?
                    "<button type='button' style='display: none;' class='reveal-imports'>show/hide imports</button>"
                            + "<pre class='hide code-example-imports'><code class='java'>" + htmlEscapedImports + "</code></pre>"
                    : "")
                    + "<pre><code class='java'>" + htmlEscapedBody + "</code><p>See the <a href=\"" + pathToGitHubTestFile + "\" target=\"_blank\">test code</a>.</pre>"
                    + "</div>";
        } catch (final Exception e) {
            System.err.println(e);
            System.err.println("in");
            System.err.println(tag);
            System.err.println(tag.position());
            throw e;
        }

    }

    private String htmlEscape(final String res) {
        return res.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private File findFile(String fullyQualifiedClassName, String partialFilePath, final Tag tag) throws IOException {
        final File cwd = new File(".").getAbsoluteFile();
        final File[] directories = cwd.listFiles(file -> file.isDirectory() && !file.getName().startsWith("."));
        boolean found = false;
        File result = null;
        for (final File directory : directories) {
            final List<String> possibleSubfolders = Arrays.asList("/src/test/java", "/src/it/java", "/test/java", "/it/java");
            for (int subIndex = 0; subIndex < possibleSubfolders.size(); subIndex++) {
                final String pathToTest = "/" + directory.getName() + possibleSubfolders.get(subIndex) + "/" + partialFilePath;
                final File attempt = new File(".", pathToTest).getCanonicalFile();
                if (attempt.exists()) {
                    if (found) {
                        throw new RuntimeException(format("the class %s exists multiple times.", fullyQualifiedClassName));
                    } else {
                        result = attempt;
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            throw new RuntimeException("cannot find file for " + fullyQualifiedClassName + " for " + tag.position());
        }
        return result;
    }

    private  List<String> fileToArray(File testFile) throws FileNotFoundException {
        final Scanner scanner = new Scanner(testFile);
        List<String> lines = new ArrayList<>();
        while(scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    public String getName() {
        return "include.example";
    }

    public boolean inField() {
        return true;
    }

    public boolean inConstructor() {
        return true;
    }

    public boolean inMethod() {
        return true;
    }

    public boolean inOverview() {
        return true;
    }

    public boolean inPackage() {
        return true;
    }

    public boolean inType() {
        return true;
    }

    public boolean isInlineTag() {
        return true;
    }

    @SuppressWarnings("unused")//used by the Javadoc tool
    public static void register(Map<String, Taglet> tagletMap) {
        final CodeTaglet createdTaglet = new CodeTaglet();
        final Taglet t = tagletMap.get(createdTaglet.getName());
        if (t != null) {
            tagletMap.remove(createdTaglet.getName());
        }
        tagletMap.put(createdTaglet.getName(), createdTaglet);
    }

    //only needed for block taglets
    public String toString(Tag[] tags) {
        return null;
    }
}