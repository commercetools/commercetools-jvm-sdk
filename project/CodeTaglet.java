import com.sun.tools.doclets.Taglet;
import com.sun.javadoc.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        final String pathToTest = "/java-client/src/test/scala/" + partialFilePath;
        final File testFile = new File(".", pathToTest).getCanonicalFile();

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
            List<String> lines = new ArrayList<>();
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
    public static void register(Map tagletMap) {
        CodeTaglet createdTaglet = new CodeTaglet();
        Taglet t = (Taglet) tagletMap.get(createdTaglet.getName());
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