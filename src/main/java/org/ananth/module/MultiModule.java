package org.ananth.module;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.ananth.Repo;
import org.ananth.exception.PomNotFoundException;
import org.ananth.pom.MvnCoordinates;
import org.ananth.pom.Pom;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MultiModule extends BaseModule {
    @Getter
    @Accessors(fluent = true, chain = false)
    private final Map<String, SubModule> subModules = new HashMap<>(5);
    @Getter
    private final Repo repo;

    @Builder
    public MultiModule(String name, Repo repo) {
        super(name);
        this.repo = repo;
        Path mod = getPath();
        if (Files.notExists(mod)) throw new RuntimeException("the module not exists!! " + mod);

        GeneralModule.of(name, this);
        PersistenceModule.of(name, this);
        BusinessModule.of(name, this);
        PresentationModule.of(name, this);
        WebModule.of(name, this);
    }

    Path getPath() {
        return repo.root().resolve(name);
    }

    public void prepareMavenDir() {
        subModules.values().forEach(SubModule::prepareMavenDir);
    }

    public void validateMavenDir() {
        subModules.values().forEach(SubModule::validateMavenDir);
    }

    public void createPomFiles() {
        subModules.values().forEach(SubModule::createPomFiles);
        try {
            Files.createFile(pomFilePath());
        } catch (FileAlreadyExistsException e) {
//            System.out.println("FileAlreadyExists " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void isPomExists() {
        subModules.values().forEach(SubModule::isPomExists);
        Path a = repo.root().resolve(name).resolve("pom.xml");
        if (Files.notExists(a)) throw new PomNotFoundException(a);
    }

    public void generatePom() {
        MvnCoordinates parent = MvnCoordinates.builder()
                .groupId("com.ananthaneshan")
                .artifactId("parent-project")
                .version("1.0-SNAPSHOT")
                .build();
        MvnCoordinates coordinate1 = MvnCoordinates.builder()
                .groupId("com.ananthaneshan")
                .artifactId(name)
                .version("1.0-SNAPSHOT")
                .build();
        List<String> mods = subModules.values().stream().map(SubModule::subModuleName).collect(Collectors.toList());
        Pom a = Pom.builder()
                .coordinates(coordinate1)
                .parent(parent)
                .name(name)
                .packaging("pom")
                .modules(mods.toArray(mods.toArray(new String[0])))
                .build();
        Path pomPath = repo.root().resolve(name).resolve("pom.xml");
        try {
            Files.write(pomPath, a.pomXml().getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subModules.values().forEach(sub -> sub.generatePom(repo.previousModule(this)));
    }

    @Override
    public Path pomFilePath() {
        return repo.root().resolve(name).resolve("pom.xml");
    }
}
