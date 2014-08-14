import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

//see http://docs.oracle.com/javase/8/docs/jdk/api/javadoc/doclet/com/sun/javadoc/Tag.html
public class DocumentationTaglet implements Taglet {

    public static final String FILE_SEPERATOR = System.getProperty("file.separator");

    @Override
    public boolean inField() {
        return true;
    }

    @Override
    public boolean inConstructor() {
        return true;
    }

    @Override
    public boolean inMethod() {
        return true;
    }

    @Override
    public boolean inOverview() {
        return true;
    }

    @Override
    public boolean inPackage() {
        return true;
    }

    @Override
    public boolean inType() {
        return true;
    }

    @Override
    public boolean isInlineTag() {
        return true;
    }

    @Override
    public String getName() {
        return "doc.gen";
    }

    @Override
    public String toString(final Tag tag) {
        try {
            return getString(tag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getString(final Tag tag) throws IOException {
        String result = null;
        if (isPackage(tag)) {
            if (isSummary(tag)) {
                if (isCommandPackage(tag)) {
                    result = format("Provides types to change the state of %s.", furtherArgs(tag));
                } else if (isUpdateactionsPackage(tag)) {
                    result = format("Provides the possible operations which can be performed on update commands for %s.", furtherArgs(tag));
                } else if (isQueriesPackage(tag)) {
                    result = format("Provides types to retrieve the state of %s.", furtherArgs(tag));
                } else {//model package
                    result = format("Provides model classes and builders for %s.", furtherArgs(tag));
                }
            }
        } else if (isEntityQueryClass(tag)) {
            result = format("Provides a QueryDsl for %s to formulate predicates, search expressions and reference expansion path expressions.", furtherArgs(tag));
        }

        //final String s = String.format("firstSentenceTags() %s\n<br>holder() %s\n<br>inlineTags() %s\n<br>kind() %s\n<br>position() %s\n<br>text()\n<br> %s\n<br>toS %s", Arrays.toString(tag.firstSentenceTags()), tag.holder(), Arrays.toString(tag.inlineTags()), tag.kind(), tag.position(), tag.text(), tag.toString());
        if (result == null) {
            throw new RuntimeException(tag.name() + " is not prepared to be used here: " + tag.position());
        }
        return result;
    }

    private boolean isEntityQueryClass(final Tag tag) {
        final String className = getClassName(tag);
        return className.endsWith("Query");
    }

    private String furtherArgs(final Tag tag) {
        final String allArgs = tag.text().trim();
        final int startSecondArg = allArgs.indexOf(" ");
        return allArgs.substring(startSecondArg);
    }

    private boolean isSummary(final Tag tag) {
        return tag.text().startsWith("summary");
    }

    private List<String> fileNamePathSegments(final File file) {
        try {
            return Arrays.asList(file.getCanonicalPath().split(FILE_SEPERATOR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUpdateactionsPackage(final Tag tag) {
        return getLastPackageName(tag).equals("updateactions");
    }

    private boolean isCommandPackage(final Tag tag) {
        return getLastPackageName(tag).equals("commands");
    }

    private boolean isQueriesPackage(final Tag tag) {
        return getLastPackageName(tag).equals("queries");
    }

    private boolean isModelPackage(final Tag tag) {
        return !(isCommandPackage(tag) || isQueriesPackage(tag));
    }

    private String getLastPackageName(final Tag tag) {
        final List<String> strings = fileNamePathSegments(tag.position().file());
        return strings.get(strings.size() - 2);
    }

    private String getClassName(final Tag tag) {
        return tag.position().file().getName().replace(".java", "");
    }

    private boolean isPackage(final Tag tag) {
        return tag.position().file().getName().equals("package-info.java");
    }



    @Override
    public String toString(final Tag[] tags) {
        return null;
    }

    @SuppressWarnings("unused")//used by the Javadoc tool
    public static void register(Map<String, Taglet> tagletMap) {
        final DocumentationTaglet createdTaglet = new DocumentationTaglet();
        final Taglet t = tagletMap.get(createdTaglet.getName());
        if (t != null) {
            tagletMap.remove(createdTaglet.getName());
        }
        tagletMap.put(createdTaglet.getName(), createdTaglet);
    }
}
