package org.ananth.module;

import org.ananth.exception.PomNotFoundException;
import org.ananth.pom.Dependency;
import org.ananth.pom.MvnCoordinates;
import org.ananth.pom.Pom;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public abstract class SubModule extends BaseModule {
    MultiModule parent;

    public SubModule(String name, MultiModule parent) {
        super(name);
        this.parent = parent;
    }

    abstract Path getPattern();

    abstract String subModuleName();


    abstract String packaging();

    abstract String dependentSubModule();

    abstract void addModule();

    boolean isExist() {
        Path mod = parent.getPath();
//        try {
//            Files.createDirectories(mod.resolve(getPattern()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return Files.exists(mod.resolve(getPattern()));
    }

    public void prepareMavenDir() {
        try {
            Files.createDirectories(parent.getPath().resolve(subModuleName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateMavenDir() {
        if (Files.notExists(parent.getPath().resolve(subModuleName())))
            throw new RuntimeException("Submodule Dir not available!");
    }

    public void createPomFiles() {
        createFile();
    }

    private void createFile() {
        try {
            Files.createFile(pomFilePath());
        } catch (FileAlreadyExistsException e) {
//            System.out.println("FileAlreadyExists " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path pomFilePath() {
        return parent.getPath().resolve(subModuleName()).resolve("pom.xml");
    }

    public void isPomExists() {
        Path a = pomFilePath();
        if (Files.notExists(a)) throw new PomNotFoundException(a);
    }

    public void generatePom(MultiModule multiModule) {
        List<Dependency> dependency = getDependencyList(multiModule);

        Pom a = Pom.builder()
                .parent(coordinates(name))
                .coordinates(coordinates(name + "-" + subModuleName()))
                .name(subModuleName())
                .dependencies(dependency)
                .packaging(packaging())
                .build();
        Path pomPath = pomFilePath();
        try {
            Files.write(pomPath, a.pomXml().getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Dependency> getDependencyList(MultiModule multiModule) {
        List<Dependency> dependency = new ArrayList<>();
        if (dependentSubModule() != null) {
            dependency.add(Dependency.builder()
                    .coordinates(coordinates(name + "-" + dependentSubModule()))
                    .scope("provided")
                    .build());

        }
        if (multiModule != null) {
            dependency.add(Dependency.builder()
                    .coordinates(coordinates(multiModule.name()))
                    .scope("provided")
                    .build());

        }
        return dependency.isEmpty() ? null : dependency;
    }

    private MvnCoordinates coordinates(String artifactId) {
        return MvnCoordinates.builder()
                .groupId("com.ananthaneshan")
                .artifactId(artifactId)
                .version("1.0-SNAPSHOT")
                .build();
    }
}
