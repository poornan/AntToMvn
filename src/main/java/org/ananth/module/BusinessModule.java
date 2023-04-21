package org.ananth.module;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BusinessModule extends SubModule {
    private final Path pattern = Paths.get("src", "main", "java", "doradus", "enterprise", name, "business");

    public BusinessModule(String name, MultiModule parent) {
        super(name, parent);
        addModule();
    }

    @Override
    Path getPattern() {
        return pattern;
    }

    @Override
    String subModuleName() {
        return "business";
    }

    @Override
    String packaging() {
        return "ejb";
    }

    @Override
    String dependentSubModule() {
        return "persistence";
    }

    @Override
    void addModule() {
        if (this.isExist())
            parent.subModules().put("BusinessModule", this);
    }

    public static void of(String name, MultiModule parent) {
        new BusinessModule(name, parent);
    }
}
