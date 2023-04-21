package org.ananth.module;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PresentationModule extends SubModule {
    private final Path pattern = Paths.get("src", "main", "java", "doradus", "enterprise", name, "presentation");

    public PresentationModule(String name, MultiModule parent) {
        super(name, parent);
        addModule();
    }

    @Override
    Path getPattern() {
        return pattern;
    }

    @Override
    String subModuleName() {
        return "presentation";
    }

    @Override
    String packaging() {
        return "jar";
    }

    @Override
    String dependentSubModule() {
        return "business";
    }

    @Override
    void addModule() {
        if (this.isExist())
            parent.subModules().put("PresentationModule", this);
    }

    public static void of(String name, MultiModule parent) {
        new PresentationModule(name, parent);
    }
}
