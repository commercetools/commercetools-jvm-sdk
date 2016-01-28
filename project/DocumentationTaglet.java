import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//see http://docs.oracle.com/javase/8/docs/jdk/api/javadoc/doclet/com/sun/javadoc/Tag.html
public class DocumentationTaglet implements Taglet {

    public static final String FILE_SEPERATOR = System.getProperty("file.separator");
    public static final String UPDATEACTIONS_PACKAGE = "updateactions";
    public static final Predicate<File> FILE_CONTAINS_PUBLIC_UPDATEACTION_PREDICATE =
            file -> readAllLines(file)
                    .stream()
                    .anyMatch(line -> line.contains("public class " + file.getName().replace(".java", "")) || line.contains("public final class " + file.getName().replace(".java", "")));

    private static List<String> readAllLines(final File file) {
        try {
            return Files.readAllLines(Paths.get(file.toURI()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
            result = format("Provides a QueryDsl for %s to formulate predicates, search expressions and reference expansion path expressions. " +
                    "<p>For further information how to use the query API to consult the <a href='" + relativeUrlTo(tag, "io.sphere.sdk.meta.QueryDocumentation") +
                    "'>Query API documentation</a>.</p>", furtherArgs(tag));
        } else if (isEntityQueryClassBuilder(tag)) {
            final String queryClassSimpleName = getClassName(tag).replace("Builder", "");
            result = format("A Builder for <a href='%s.html'>%s</a>.", queryClassSimpleName, queryClassSimpleName);
        } else if (isQueryModelClass(tag)) {
            result = format("Provides a domain specific language to formulate predicates and search expressions for querying %s.", furtherArgs(tag));
        } else if (isUpdateCommandClass(tag) && tag.text().contains("list actions")) {
            final File currentFile = Objects.requireNonNull(tag.position().file(), "command dir not found");
            final File commandsDirectory = currentFile.getParentFile();
            final File updateactionsDirectory = new File(commandsDirectory, "updateactions");
            final List<String> updateActionNames =
                    asList(updateactionsDirectory.listFiles((file, name) -> name.endsWith(".java") && !name.contains("-")))
                            .stream()
                            .filter(FILE_CONTAINS_PUBLIC_UPDATEACTION_PREDICATE)
                            .map(file -> file.getName().replace(".java", ""))
                            .sorted()
                            .collect(toList());
            final StringBuilder builder = new StringBuilder("<p id=update-actions>Known UpdateActions</p><ul>");
            updateActionNames.forEach(name -> builder.append(format("<li><a href=\"%s/%s.html\">%s</a></li>", UPDATEACTIONS_PACKAGE, name, name)));
            builder.append("</ul>");
            result = builder.toString();
        } else if (isClientRequestList(tag)) {
            Path currentRelativePath = Paths.get("");
            final ClientRequestListFileVisitor visitor = new ClientRequestListFileVisitor();
            Files.walkFileTree(currentRelativePath, visitor);
            final StringBuilder builder = new StringBuilder("<table border=1><tr><th>resource</th><th>accesors</th><th>mutators</th></tr>");
            final Comparator<? super Map.Entry<String, ResourcesRequests>> comparator = Comparator.comparing(entry -> entry.getKey());
            visitor.getResources().entrySet().stream().sorted(comparator).forEach(entry -> {

                final Function<String, String> mapper = m -> {
                    final String fullClassName = m.substring(m.indexOf("/io/sphere/sdk")).replace(".java", "").replace("/", ".");
                    return "<a href='" + relativeUrlTo(tag, fullClassName) + "'>" + fullClassNameToSimple(fullClassName) + "</a>";
                };
                final List<String> accessors = entry.getValue().getAccessors().stream().map(mapper).collect(toList());
                final List<String> mutators = entry.getValue().getMutators().stream().map(mapper).collect(toList());
                final int neededLines = Math.max(accessors.size(), mutators.size());
                builder.append("<tr><td rowspan=\"").append(neededLines).append("\">").append(entry.getKey()).append("</td><td>").append(accessors.isEmpty() ? "" : accessors.get(0)).append("</td><td>").append(mutators.isEmpty() ? "" : mutators.get(0)).append("</td></tr>").append("\n");
                for (int i = 1; i < neededLines; i++) {
                    builder.append("<tr><td>").append(accessors.size() > i ? accessors.get(i) : "").append("</td><td>").append(mutators.size() > i ? mutators.get(i) : "").append("</td></tr>").append("\n");
                }
            });
            builder.append("</table>");
            result = builder.toString();
        } else if (isFileInclude(tag)) {
            final String[] columns = tag.text().split(" ", 3);
            final String relativePathFile = columns[2];
            final String content = new String(Files.readAllBytes(Paths.get(relativePathFile)));
            result = content;
        } else if (isIntro(tag)) {
            result = renderIntro(tag);
        }

        //final String s = String.format("firstSentenceTags() %s\n<br>holder() %s\n<br>inlineTags() %s\n<br>kind() %s\n<br>position() %s\n<br>text()\n<br> %s\n<br>toS %s", Arrays.toString(tag.firstSentenceTags()), tag.holder(), Arrays.toString(tag.inlineTags()), tag.kind(), tag.position(), tag.text(), tag.toString());
        if (result == null) {
            throw new RuntimeException(tag.name() + " is not prepared to be used here: " + tag.position());
        }
        return result;
    }

    private boolean isEntityQueryClassBuilder(final Tag tag) {
        final String className = getClassName(tag);
        return className.endsWith("QueryBuilder");
    }

    private String renderIntro(final Tag tag) {
        final File updateActionFile = tag.position().file();
        if (isUpdateActionIntro(tag)) {
            final File updateCommand = updateActionFile.getParentFile().getParentFile().listFiles((dir, fileName) -> fileName.endsWith("UpdateCommand.java"))[0];
            final String updateCommandClassName = updateCommand.getName().replace(".java", "");
            final String entityName = updateCommandClassName.replace("UpdateCommand", "");
            return format("<p>See also <a href=\"../%s.html\">%s</a>.<p>", updateCommandClassName, updateCommandClassName);
        } else {
            return null;
        }
    }

    private boolean isUpdateActionIntro(final Tag tag) {
        return "updateactions".equals(getLastPackageName(tag));
    }

    private boolean isIntro(final Tag tag) {
        return tag.text().startsWith("intro");
    }

    private boolean isFileInclude(final Tag tag) {
        return tag.text().startsWith("include file ");
    }

    private String fullClassNameToSimple(final String fullClassName) {
        final String[] elements = fullClassName.split("\\.");
        return elements[elements.length - 1];
    }

    private static class ResourcesRequests {
        private final List<String> accessors = new LinkedList<>();
        private final List<String> mutators = new LinkedList<>();

        public void addAccessor(final String element) {
            accessors.add(element);
        }

        public void addMutator(final String element) {
            mutators.add(element);
        }

        public List<String> getAccessors() {
            return accessors;
        }

        public List<String> getMutators() {
            return mutators;
        }

        @Override
        public String toString() {
            return "ResourcesRequests{" +
                    "accessors=" + accessors +
                    ", mutators=" + mutators +
                    '}';
        }
    }

    private static class ClientRequestListFileVisitor implements FileVisitor<Path> {

        private final Map<String, ResourcesRequests> resources = new HashMap<>();

        public Map<String, ResourcesRequests> getResources() {
            if (resources.containsKey("sdk")) {
                resources.remove("sdk");
            }
            return resources;
        }

        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
            FileVisitResult result = FileVisitResult.CONTINUE;
            final String name = dir.getFileName().toFile().getName();
            if (name.equals("target") || name.equals("test") || name.equals("it") || name.startsWith(".")) {
                result = FileVisitResult.SKIP_SUBTREE;
            }
            return result;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {

            final File asFile = file.toFile();
            final String name = asFile.getName();
            if(name.endsWith("Command.java")) {
                final String resourceName = asFile.getParentFile().getParentFile().getName();
                get(resourceName).addMutator(asFile.getCanonicalPath());
            } else if(name.endsWith("Query.java") || name.endsWith("Search.java") || name.endsWith("Get.java")) {
                final String resourceName = asFile.getParentFile().getParentFile().getName();
                get(resourceName).addAccessor(asFile.getCanonicalPath());
            }
            return FileVisitResult.CONTINUE;
        }

        private DocumentationTaglet.ResourcesRequests get(final String resourceName) {
            final ResourcesRequests value = resources.getOrDefault(resourceName, new ResourcesRequests());
            resources.put(resourceName, value);
            return value;
        }

        @Override
        public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    private boolean isUpdateCommandClass(final Tag tag) {
        return getClassName(tag).endsWith("UpdateCommand");
    }

    private boolean isQueryModelClass(final Tag tag) {
        return getClassName(tag).endsWith("QueryModel");
    }

    private boolean isClientRequestList(final Tag tag) {
        return tag.text().equals("list clientrequests");
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
            return asList(file.getCanonicalPath().split(FILE_SEPERATOR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUpdateactionsPackage(final Tag tag) {
        return getLastPackageName(tag).equals(UPDATEACTIONS_PACKAGE);
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

    private String getFullPackage(final Tag tag) {
        final String absolutePath = tag.position().file().getAbsolutePath();
        final String dir = "src/main/java";
        final int codeRootOfThisModule = absolutePath.indexOf(dir) + dir.length() + 1;
        final String substring = absolutePath.substring(codeRootOfThisModule);
        return substring.replace(tag.position().file().getName(), "").replace('/', '.');
    }

    private boolean isPackage(final Tag tag) {
        return tag.position().file().getName().equals("package-info.java");
    }

    private String relativeUrlTo(final Tag tag, final String fullClassName) {
        final String[] split = getFullPackage(tag).split("\\.");
        final int countBack = split.length;
        final StringBuilder builder = new StringBuilder();
        for (final String aSplit : split) {
            builder.append("../");
        }
        return builder.toString() + fullClassName.replace('.', '/') + ".html";
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
