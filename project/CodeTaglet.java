import com.sun.tools.doclets.Taglet;
import com.sun.javadoc.*;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

    private String getString(Tag tag) throws IOException {
        int pos = tag.text().indexOf("#");
        final boolean fullFileRequested = pos == -1;
        if (fullFileRequested) {
            pos = tag.text().length();
        }
        final String fullyQualifiedClassName = tag.text().substring(0, pos);
        final String partialFilePath = fullyQualifiedClassName.replace('.', '/').concat(".java");


        final File testFile = findFile(fullyQualifiedClassName, partialFilePath);

        String res = "";
        if (fullFileRequested) {
            //partially from http://stackoverflow.com/a/326448
            File file = testFile;
            StringBuilder fileContents = new StringBuilder((int)file.length());
            Scanner scanner = new Scanner(file);
            String lineSeparator = System.getProperty("line.separator");
            try {
                while(scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine() + lineSeparator);
                }
                res = fileContents.toString();
            } finally {
                scanner.close();
            }
        } else {
            final String testName = tag.text().substring(pos + 1);
            final Scanner scanner = new Scanner(testFile);
            List<String> lines = new ArrayList<String>();
            while(scanner.hasNext()) {
                String current = scanner.findInLine(testName);
                final boolean methodStartFound = current != null;
                if (methodStartFound) {
                    scanner.nextLine();
                    boolean endFound = false;
                    do {
                        current = scanner.nextLine();
                        endFound = current.equals("    }");
                        if (!endFound) {
                            final String currentWithoutLeadingWhitspace = current.replaceFirst("        ", "");
                            lines.add(currentWithoutLeadingWhitspace);
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
        final String htmlEscaped = res.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        return "<pre><code class='java'>" + htmlEscaped + "</code></pre>";
    }

    private File findFile(String fullyQualifiedClassName, String partialFilePath) throws IOException {
        final File cwd = new File(".").getAbsoluteFile();
        final File[] directories = cwd.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && !file.getName().startsWith(".");
            }
        });
        boolean found = false;
        File testFile = null;
        for (int i = 0; !found && i < directories.length; i++) {
            final List<String> possibleSubfolders = Arrays.asList("/src/test/scala", "/src/it/scala", "/src/test/java", "/src/it/java", "/test", "/it");
            for (int subIndex = 0; !found && subIndex < possibleSubfolders.size(); subIndex++) {
                final String pathToTest = "/" + directories[i].getName() + possibleSubfolders.get(subIndex) + "/" + partialFilePath;
                testFile = new File(".", pathToTest).getCanonicalFile();
                if (testFile.exists()) {
                    found = true;
                }
            }
        }
        if (!found) {
            throw new RuntimeException("cannot find file for " + fullyQualifiedClassName);
        }
        return testFile;
    }

    private  List<String> fileToArray(File testFile) throws FileNotFoundException {
        final Scanner scanner = new Scanner(testFile);
        List<String> lines = new ArrayList<String>();
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