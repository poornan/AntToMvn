package org.ananth;

import lombok.experimental.Accessors;
import org.ananth.exception.PomNotFoundException;
import org.ananth.module.BaseModule;
import org.ananth.module.MultiModule;
import org.ananth.pom.MvnCoordinates;
import org.ananth.pom.Pom;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Accessors(fluent = true, chain = false)
public class Repo {

    private final Path root;
    private final MultiModule[] multiModules;

    public Repo(Path root, String[] projectOrder) {
        if (!Files.exists(root)) throw new RuntimeException("No Root Dir exist!!");
        this.root = root;
        multiModules = Arrays.stream(projectOrder).map(pro -> MultiModule.builder()
                .name(pro)
                .repo(this)
                .build()).toArray(MultiModule[]::new);
        prepareMavenDir();
        validateMavenDir();
        createPomFiles();
        isPomExists();
        generatePom();
    }

    public MultiModule getModule(String name) {
        Optional<MultiModule> found = Arrays.stream(multiModules)
                .filter(mod -> mod.name().equals(name))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple elements: " + a + ", " + b);
                });
        if (found.isPresent()) return found.get();
        throw new RuntimeException("No module found with the name: " + name);

    }

    public void prepareMavenDir() {
        Arrays.stream(multiModules).forEach(MultiModule::prepareMavenDir);
    }

    public void validateMavenDir() {
        Arrays.stream(multiModules).forEach(MultiModule::validateMavenDir);
    }

    public Path root() {
        return this.root;
    }

    public MultiModule[] multiModules() {
        return this.multiModules;
    }

    public void createPomFiles() {
        Arrays.stream(multiModules).forEach(MultiModule::createPomFiles);
        try {
            Files.createFile(root.resolve("pom.xml"));
        } catch (FileAlreadyExistsException e) {
//            System.out.println("FileAlreadyExists " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void isPomExists() {
        Arrays.stream(multiModules).forEach(MultiModule::isPomExists);
        if (Files.notExists(root.resolve("pom.xml"))) throw new PomNotFoundException(root.resolve("pom.xml"));
    }

    public void generatePom() {
        MvnCoordinates coordinate1 = MvnCoordinates.builder()
                .groupId("com.ananthaneshan")
                .artifactId("parent-project")
                .version("1.0-SNAPSHOT")
                .build();
        List<String> mods = Arrays.stream(multiModules).map(BaseModule::name).collect(Collectors.toList());
        Pom a = Pom.builder()
                .coordinates(coordinate1)
                .name("parent-project")
                .packaging("pom")
                .modules(mods.toArray(mods.toArray(new String[0])))
                .build();
        try {
            Files.write(root.resolve("pom.xml"), a.pomXml().getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(multiModules).forEach(MultiModule::generatePom);
    }

    public MultiModule previousModule(MultiModule mod) {
        for (int i = 1; i < multiModules.length; i++) {
            if (mod.name().equals(multiModules[i].name())) return multiModules[i - 1];
        }
        return null;
    }
}
