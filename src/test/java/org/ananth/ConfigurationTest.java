package org.ananth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationTest {
    Configuration a = new Configuration();

    @Test
    public void shouldHaveRoot() {
        assertEquals("D:/projects/Enterprise", a.repoRoot());
    }

    @Test
    public void shouldHaveBuildPath() {
        assertEquals("_environment/build", a.buildPath());
    }

    @Test
    public void shouldHaveBuildOrderFile() {
        assertEquals("build.properties", a.buildOrderFile());
    }

    @Test
    public void shouldHaveBuildOrderKey() {
        assertEquals("modules.to.include", a.buildOrderKey());
    }

    @Test
    public void shouldHaveSubModuleNames() {
        assertArrayEquals(new String[]{"business", "general", "persistence", "presentation", "web"}, a.subModuleNames());
    }

    @Test
    public void shouldHaveRootPomTemplate() {
        assertEquals("root/pom.xml", a.rootPomTemplate());
    }

    @Test
    public void shouldHaveModulePomTemplate() {
        assertEquals("module/pom.xml", a.modulePomTemplate());
    }

    @Test
    public void shouldHaveBusinessPomTemplate() {
        assertEquals("business/pom.xml", a.businessPomTemplate());
    }

    @Test
    public void shouldHaveGeneralPomTemplate() {
        assertEquals("general/pom.xml", a.generalPomTemplate());
    }

    @Test
    public void shouldHavePersistencePomTemplate() {
        assertEquals("persistence/pom.xml", a.persistencePomTemplate());
    }

    @Test
    public void shouldHavePresentationPomTemplate() {
        assertEquals("presentation/pom.xml", a.presentationPomTemplate());
    }

    @Test
    public void shouldHaveWebPomTemplate() {
        assertEquals("web/pom.xml", a.webPomTemplate());
    }

    @Test
    public void shouldHaveBusinessPattern() {
        assertEquals("business", a.businessPattern());
    }

    @Test
    public void shouldHaveGeneralPattern() {
        assertEquals("general", a.generalPattern());
    }

    @Test
    public void shouldHavePersistencePattern() {
        assertEquals("persistence", a.persistencePattern());
    }

    @Test
    public void shouldHavePresentationPattern() {
        assertEquals("presentation", a.presentationPattern());
    }

    @Test
    public void shouldHaveWebPattern() {
        assertEquals("web", a.webPattern());
    }

    @Test
    public void shouldHaveResourcePattern() {
        assertEquals("resource", a.resourcePattern());
    }

}
