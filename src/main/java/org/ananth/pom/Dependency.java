package org.ananth.pom;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true, chain = false)
@Builder
public class Dependency {
    MvnCoordinates coordinates;
    String scope;

    String version() {
        return coordinates.version();
    }

    String groupId() {
        return coordinates.groupId();
    }

    String artifactId() {
        return coordinates.artifactId();
    }
}
