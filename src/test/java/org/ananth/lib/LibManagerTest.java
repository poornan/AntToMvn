package org.ananth.lib;

import org.ananth.pom.MvnCoordinates;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.ananth.lib.LibManager.execute;
import static org.ananth.lib.LibManager.executeCommands;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * mvn install:install-file -Dfile= -DgroupId= -DartifactId= -Dversion -Dpackaging=
 */
public class LibManagerTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

//    @Test
    public void mvnShouldInstallJar() {
        assertDoesNotThrow(() -> {
            InvocationRequest request = new DefaultInvocationRequest();
//            String cmd = "install:install-file -Dfile=target/dependency-management-rf-1.0-SNAPSHOT.jar  -DgroupId=adfs -DartifactId=asdf -Dversion=adsf -Dpackaging=jar";
            String cmd = "-version";
            request.setGoals(Collections.singletonList(cmd));
            execute(request);
        });
    }

//    @Test
    public void mvnShouldExecuteMultipleRequestsInParallels() {
        assertDoesNotThrow(() -> {
            InvocationRequest request = new DefaultInvocationRequest();
            request.setGoals(Collections.singletonList("-version"));

            execute(getNRequests(3));
        });
    }

//    @Test
    public void mvnShouldExecuteMultipleCmdInParallels() {
        assertDoesNotThrow(() -> {
            List.of("-version", "-version");
            executeCommands(List.of("-version", "-version"));
        });
    }

    private List<InvocationRequest> getNRequests(int n) {
        List<InvocationRequest> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            InvocationRequest request = new DefaultInvocationRequest();
            request.setBatchMode(true);
            request.setGoals(Collections.singletonList("-version"));
            a.add(request);
        }
        return a;
    }

    @Test
    public void libMangerScanForJar() {
        assertDoesNotThrow(() -> {
//            Map<String,List<Path>> jars = new LibManager().scanLibrary();
        });
    }

//    @Test
    public void pathToCoordinates() {
        assertDoesNotThrow(() -> {
            Path a = Paths.get("test");
            MvnCoordinates co = LibManager.pathToCoordinates(a, "");
        });
    }

    @Test
    public void coordinatesCommands() {
        assertDoesNotThrow(() -> {
            MvnCoordinates a = MvnCoordinates.builder().path(Paths.get("test")).build();
            String co = LibManager.coordinatesCommands(a);
        });
    }
}