package org.ananth.module;

import lombok.experimental.Accessors;
import org.ananth.Module;

import java.nio.file.Path;

@Accessors(fluent = true, chain = false)
//@SuperBuilder
public abstract class BaseModule implements Module {
    String name;


    public BaseModule(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void name(String name) {
        this.name = name;
    }

    public abstract Path pomFilePath();
}
