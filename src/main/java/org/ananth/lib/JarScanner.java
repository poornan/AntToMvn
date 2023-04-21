package org.ananth.lib;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface JarScanner {
    Map<String, List<Path>> getAllJars();
}
