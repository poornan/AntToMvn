package org.ananth.module;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WebModule extends SubModule {
    private final Path pattern = Paths.get("src", "main", "webapp", name);

    public WebModule(String name, MultiModule parent) {
        super(name, parent);
        addModule();
    }

    @Override
    Path getPattern() {
        return pattern;
    }

    @Override
    String subModuleName() {
        return "web";
    }

    @Override
    String packaging() {
        return "war";
    }

    @Override
    String dependentSubModule() {
        return "presentation";
    }

    @Override
    void addModule() {
        if (this.isExist())
            parent.subModules().put("WebModule", this);
    }

    public static void of(String name, MultiModule parent) {
        new WebModule(name, parent);
    }
}
