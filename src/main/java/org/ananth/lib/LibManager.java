package org.ananth.lib;

import org.ananth.pom.MvnCoordinates;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LibManager {

    static File mvnHomePath;
    JarScanner jarScanner;
    Map<String, List<Path>> jars;

    static {
        String mvnHome = System.getenv("MVN_HOME");
        if (mvnHome == null) throw new RuntimeException("MVN_HOME not set as Environment variable");
        mvnHomePath = new File(mvnHome);
    }

    public LibManager(JarScanner jarScanner) {
        this.jarScanner = jarScanner;
    }

    public static void execute(InvocationRequest request) {
        try {
            Invoker invoker = new DefaultInvoker();
            invoker.setMavenHome(mvnHomePath);
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            throw new RuntimeException(e);
        }
    }


    public static void execute(List<InvocationRequest> requests) {
        requests.stream().parallel().forEach(LibManager::execute);
    }

    public static void executeCommands(List<String> commands) {
        execute(getRequests(commands));
    }

    private static List<InvocationRequest> getRequests(List<String> cmd) {
        return cmd.stream().parallel().map(a -> {
            InvocationRequest request = new DefaultInvocationRequest();
            request.setBatchMode(true);
            request.setGoals(Collections.singletonList(a));
            return request;
        }).collect(Collectors.toList());
    }

    public static void scanJars(Path path) {
    }

    public static MvnCoordinates pathToCoordinates(Path path, String scope) {
        return MvnCoordinates.builder()
                .version("1.0-SNAPSHOT")//TODO ${project.version}
                .groupId("com.group")
                .artifactId(path.getFileName().toString())
                .path(path)
                .scope(scope)
                .build();
    }

    public static String coordinatesCommands(MvnCoordinates a) {
        String cmd = "install:install-file -Dfile=%s  -DgroupId=%s -DartifactId=%s -Dversion=%s -Dpackaging=jar";
        return String.format(cmd, a.path().toAbsolutePath(), a.groupId(), a.artifactId(), a.version());
    }

    public void scanLibrary() {
        jars = jarScanner.getAllJars();
    }
}
