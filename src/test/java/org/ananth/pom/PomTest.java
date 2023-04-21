package org.ananth.pom;

import org.ananth.pom.Dependency;
import org.ananth.pom.MvnCoordinates;
import org.ananth.pom.Pom;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.xmlunit.assertj3.XmlAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PomTest {
    MvnCoordinates coordinate = MvnCoordinates.builder()
            .groupId("groupId")
            .artifactId("artifactId")
            .version("version")
            .build();
    MvnCoordinates coordinate1 = MvnCoordinates.builder()
            .groupId("com.group")
            .artifactId("artifactId")
            .version("1.0-SNAPSHOT")
            .build();

    @Order(1)
    @Test
    public void thereShouldBeAPom() {
        Pom a = Pom.of(coordinate);
        assertNotNull(a);
    }

    @Order(2)
    @Test
    public void pomShouldHaveModelVersion() {
        Pom a = Pom.of(coordinate);
        assertEquals("4.0.0", a.modelVersion());
    }

    @Order(3)
    @Test
    public void pomShouldHaveGroupId() {
        Pom a = Pom.of(coordinate);
        assertEquals("groupId", a.groupId());
    }

    @Order(4)
    @Test
    public void pomShouldHaveArtifactId() {
        Pom a = Pom.of(coordinate);
        assertEquals("artifactId", a.artifactId());
    }

    @Order(5)
    @Test
    public void pomShouldHaveCoordinates() {

        Pom a = Pom.of(coordinate1);
        assertEquals("1.0-SNAPSHOT", a.version());
    }

    @Order(6)
    @Test
    public void pomMayHaveName() {

        Pom a = Pom.builder()
                .coordinates(coordinate1)
                .name("projectA")
                .build();
        assertEquals("projectA", a.name());
    }

    @Order(7)
    @Test
    public void pomMayHavePackaging() {

        Pom a = Pom.builder()
                .coordinates(coordinate1)
                .name("projectA")
                .packaging("pom")
                .build();
        assertEquals("pom", a.packaging());
    }

    @Order(7)
    @Test
    public void pomMayHaveModules() {

        Pom a = Pom.builder()
                .coordinates(coordinate1)
                .modules(new String[]{"module"})
                .build();
        assertArrayEquals(new String[]{"module"}, a.modules());
    }

    @Order(8)
    @Test
    public void compareXml() {
        String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
        String testXml = "<struct><boolean>false</boolean><int>3</int></struct>";

        assertThat(controlXml).and(testXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .areSimilar();
    }

    @Order(9)
    @Test
    public void comparePom() {
        Pom a = Pom.builder()
                .coordinates(coordinate1)
                .name("projectA")
                .packaging("pom")
                .modules(new String[]{"moduleA", "moduleB"})
                .build();
        String testXml = a.pomXml();
//        System.out.println(testXml);
        assertThat(pom1).and(testXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .ignoreWhitespace()
                .areSimilar();
    }

    @Order(10)
    @Test
    public void pomMayHaveParent() {
        MvnCoordinates coordinate2 = MvnCoordinates.builder()
                .groupId("com.group")
                .artifactId("artifactId2")
                .version("1.0-SNAPSHOT")
                .build();
        Pom a = Pom.builder()
                .coordinates(coordinate2)
                .modules(new String[]{"module"})
                .parent(coordinate1)
                .build();
        assertEquals(coordinate1, a.parent());
    }

    @Order(11)
    @Test
    public void pomMayHaveParentPom() {
        MvnCoordinates coordinate2 = MvnCoordinates.builder()
                .groupId("com.group")
                .artifactId("artifactId2")
                .version("1.0-SNAPSHOT")
                .build();
        Pom a = Pom.builder()
                .coordinates(coordinate2)
                .modules(new String[]{"module"})
                .parent(coordinate1)
                .build();
        String testXml = a.pomXml();
//        System.out.println(testXml);
        assertThat(pom2).and(testXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .ignoreWhitespace()
                .areSimilar();
    }

    @Order(12)
    @Test
    public void pomMayHaveDependencies() {
        MvnCoordinates coordinate2 = MvnCoordinates.builder()
                .groupId("com.group")
                .artifactId("artifactId2")
                .version("1.0-SNAPSHOT")
                .build();
        List<Dependency> dependency = List.of(Dependency.builder()
                .coordinates(coordinate2)
                .scope("provided")
                .build());
        Pom a = Pom.builder()
                .coordinates(coordinate2)
                .modules(new String[]{"module"})
                .parent(coordinate1)
                .dependencies(dependency)
                .build();
        assertEquals(dependency, a.dependencies());
    }

    @Order(13)
    @Test
    public void pomMayHaveDependenciesInPom() {
        MvnCoordinates coordinate2 = MvnCoordinates.builder()
                .groupId("com.group")
                .artifactId("artifactId2")
                .version("1.0-SNAPSHOT")
                .build();
        List<Dependency> dependency = List.of(Dependency.builder()
                .coordinates(coordinate2)
                .scope("provided")
                .build());
        Pom a = Pom.builder()
                .coordinates(coordinate2)
                .modules(new String[]{"module"})
//                .parent(coordinate1)
                .dependencies(dependency)
                .build();
        String testXml = a.pomXml();
//        System.out.println(testXml);
        assertThat(pom3).and(testXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .ignoreWhitespace()
                .areSimilar();
    }

    static final String pom1 = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <groupId>com.group</groupId>\n" +
            "  <artifactId>artifactId</artifactId>\n" +
            "  <version>1.0-SNAPSHOT</version>\n" +
            "  <packaging>pom</packaging>\n" +
            "  <properties>\n" +
            "    <maven.compiler.source>1.8</maven.compiler.source>\n" +
            "    <maven.compiler.target>1.8</maven.compiler.target>\n" +
            "    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
            "  </properties>\n" +
            "  <modules>\n" +
            "    <module>moduleA</module>\n" +
            "    <module>moduleB</module>\n" +
            "  </modules>\n" +
            "</project>";
    static final String pom2 = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <parent>\n" +
            "    <artifactId>artifactId</artifactId>\n" +
            "    <groupId>com.group</groupId>\n" +
            "    <version>1.0-SNAPSHOT</version>\n" +
            "  </parent>\n" +
            "  <groupId>com.group</groupId>\n" +
            "  <artifactId>artifactId2</artifactId>\n" +
            "  <version>1.0-SNAPSHOT</version>\n" +
            "  <properties>\n" +
            "    <maven.compiler.source>1.8</maven.compiler.source>\n" +
            "    <maven.compiler.target>1.8</maven.compiler.target>\n" +
            "    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
            "  </properties>\n" +
            "  <modules>\n" +
            "    <module>module</module>\n" +
            "  </modules>\n" +
            "</project>";
    static final String pom3 = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "  <modelVersion>4.0.0</modelVersion>\n" +
            "  <groupId>com.group</groupId>\n" +
            "  <artifactId>artifactId2</artifactId>\n" +
            "  <version>1.0-SNAPSHOT</version>\n" +
            "  <properties>\n" +
            "    <maven.compiler.source>1.8</maven.compiler.source>\n" +
            "    <maven.compiler.target>1.8</maven.compiler.target>\n" +
            "    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
            "  </properties>\n" +
            "  <modules>\n" +
            "    <module>module</module>\n" +
            "  </modules>\n" +
            "  <dependencies>\n" +
            "    <dependency>\n" +
            "      <artifactId>artifactId2</artifactId>\n" +
            "      <groupId>com.group</groupId>\n" +
            "      <version>1.0-SNAPSHOT</version>\n" +
            "    </dependency>\n" +
            "  </dependencies>\n" +
            "</project>";

    String build= "<build>\n" +
            "    <sourceDirectory>../src/main/java</sourceDirectory>\n" +
            " \n" +
            "    <plugins>\n" +
            "      <plugin>\n" +
            "        <artifactId>maven-compiler-plugin</artifactId>\n" +
            "        <version>2.0.2</version>\n" +
            "        <configuration>\n" +
            "          <includes><include>**/core/**</include></includes>\n" +
            "        </configuration>\n" +
            "      </plugin>\n" +
            "    </plugins>\n" +
            "  </build>";
    //https://maven.apache.org/guides/mini/guide-using-one-source-directory.html
    //https://maven.apache.org/ref/3.6.3/maven-model-builder/super-pom.html
}