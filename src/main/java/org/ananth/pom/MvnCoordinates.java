package org.ananth.pom;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.nio.file.Path;

@Value
@Accessors(fluent = true, chain = false)
@SuperBuilder
public class MvnCoordinates {
    String groupId;
    String artifactId;
    String version;
    Path path;
    String scope;

}
