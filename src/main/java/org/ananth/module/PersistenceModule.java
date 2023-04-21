package org.ananth.module;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PersistenceModule extends SubModule {
    private final Path pattern = Paths.get("src", "main", "java", "doradus", "enterprise", name, "persistence");

    public PersistenceModule(String name, MultiModule parent) {
        super(name, parent);
        addModule();
    }

    @Override
    Path getPattern() {
        return pattern;
    }

    @Override
    String subModuleName() {
        return "persistence";
    }

    @Override
    String packaging() {
        return "jar";
    }

    @Override
    String dependentSubModule() {
        return "general";
    }

    @Override
    void addModule() {
        if (this.isExist())
            parent.subModules().put("PersistenceModule", this);
    }

    public static void of(String name, MultiModule parent) {
        new PersistenceModule(name, parent);
    }
}
