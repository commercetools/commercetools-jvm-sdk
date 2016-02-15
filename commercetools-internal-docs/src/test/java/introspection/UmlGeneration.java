package introspection;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import static introspection.IntrospectionUtils.readClassNames;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

//test:runMain introspection.UmlGeneration
public class UmlGeneration {

    public static void main(String[] args) throws Exception {
        final Function<Class<?>, String> classStringFunction = clazz -> {
            final String superClassinfo = clazz.getSuperclass() != null && !asList("Object", "Base").contains(clazz.getSuperclass().getSimpleName()) ? String.format("%s <|-- %s", clazz.getSuperclass().getCanonicalName(), clazz.getCanonicalName()) : "";
            final String interfaces = Arrays.stream(clazz.getInterfaces()).filter(e -> e != null).map(interf -> String.format("interface %s <|-- %s", interf.getCanonicalName(), (clazz.isInterface() ? "interface " : "") + clazz.getCanonicalName())).collect(joining("\n"));

            return superClassinfo + "\n" + interfaces;
        };

        writeUml(classStringFunction, readClassNames().sorted(), "target/uml.svg");
        writeUml(classStringFunction, readClassNames().filter(name -> name.startsWith("io.sphere.sdk.categories")).sorted(), "target/category-uml.svg");


    }

    private static void writeUml(final Function<Class<?>, String> classStringFunction, final Stream<String> classNames, final String pathname) throws IOException {
        final Stream<String> entryStream = classNames
                .map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
//                .filter(clazz -> clazz.getSuperclass() != null)
                //TODO, also for superinterface type interface?
                .map(classStringFunction);


        final String content = "@startuml\n" +
                entryStream.collect(joining("\n")) +
                "\n@enduml";


        final SourceStringReader sourceStringReader = new SourceStringReader(content);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        sourceStringReader.generateImage(os, new FileFormatOption(FileFormat.SVG));
        os.close();


        FileUtils.writeByteArrayToFile(new File(pathname), os.toByteArray());
    }
}
