package org.ananth.exception;

import java.nio.file.Path;

public class PomNotFoundException extends RuntimeException {
    Path path;

    public PomNotFoundException(Path path) {
        this.path = path;

    }

    @Override
    public String getMessage() {
        return "PomNotFoundException " + path.toAbsolutePath();
    }
}
