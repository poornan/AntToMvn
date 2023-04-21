package org.ananth.module;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneralModule extends SubModule {
    private final Path pattern = Paths.get("src", "main", "java", "doradus", "enterprise", name, "general");

    public GeneralModule(String name, MultiModule parent) {
        super(name, parent);
        addModule();
    }

    @Override
    Path getPattern() {
        return pattern;
    }

    @Override
    String subModuleName() {
        return "general";
    }

    @Override
    String packaging() {
        return "jar";
    }

    @Override
    String dependentSubModule() {
        return null;
    }

    @Override
    void addModule() {
        if (this.isExist())
            parent.subModules().put("GeneralModule", this);
    }

    public static void of(String name, MultiModule parent) {
        new GeneralModule(name, parent);
    }
}
