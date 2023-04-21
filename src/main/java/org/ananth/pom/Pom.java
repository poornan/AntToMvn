package org.ananth.pom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = false)
public class Pom {
    final String modelVersion = "4.0.0";
    MvnCoordinates parent;
    MvnCoordinates coordinates;
    String name;
    String packaging;
    String[] modules;
    List<Dependency> dependencies;

    public static Pom of(MvnCoordinates coordinates) {
        return Pom.builder()
                .coordinates(coordinates)
                .build();
    }

    public String groupId() {
        return coordinates.groupId();
    }

    public String artifactId() {
        return coordinates.artifactId();
    }

    public String version() {
        return coordinates.version();
    }

    String getModulesElement() {
        if (null != modules) {
            StringBuilder module = new StringBuilder();
            module.append("\t<modules>\n");
            for (String a : modules) {
                module.append("\t\t<module>").append(a).append("</module>\n");
            }
            module.append("\t</modules>\n");
            return module.toString();
        }
        return "";
    }

    String getDependenciesElement() {
        if (null != dependencies) {
            StringBuilder dependenciesStr = new StringBuilder();
            for (Dependency dep :
                    dependencies) {
                dependenciesStr.append(getDependencyElement(dep));
            }
            return "\t<dependencies>\n" +
                    dependenciesStr +
                    "\t</dependencies>\n";
        }
        return "";
    }

    String getDependencyElement(Dependency dependency) {
        if (null != dependency) {
            return "\t\t<dependency>\n" +
                    "\t\t\t<artifactId>" + dependency.artifactId() + "</artifactId>\n" +
                    "\t\t\t<groupId>" + dependency.groupId() + "</groupId>\n" +
                    "\t\t\t<version>" + dependency.version() + "</version>\n" +
                    "\t\t</dependency>\n";
        }
        return "";
    }

    String getParentElement() {
        if (null != parent) {
            return "\t<parent>\n" +
                    "\t\t<artifactId>" + parent.artifactId() + "</artifactId>\n" +
                    "\t\t<groupId>" + parent.groupId() + "</groupId>\n" +
                    "\t\t<version>" + parent.version() + "</version>\n" +
                    "\t</parent>\n";
        }
        return "";
    }

    String getPackagingElement() {
        if (packaging != null) return String.format("<packaging>%s</packaging>\n", packaging);
        return "";
    }

    public String pomXml() {
        return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                        "    <modelVersion>%s</modelVersion>\n" +
                        "\n%s" +
                        "    <groupId>%s</groupId>\n" +
                        "    <artifactId>%s</artifactId>\n" +
                        "    <version>%s</version>\n" +
                        "    %s" +
                        "\n" +
                        "    <properties>\n" +
                        "        <maven.compiler.source>1.8</maven.compiler.source>\n" +
                        "        <maven.compiler.target>1.8</maven.compiler.target>\n" +
                        "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
                        "    </properties>\n" +
                        "\n%s" +
                        "\n%s" +
                        "</project>", modelVersion, getParentElement(), groupId(), artifactId(), version(),
                getPackagingElement(), getModulesElement(), getDependenciesElement());
    }
}
