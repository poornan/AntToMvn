package org.ananth;

public class Configuration {
    public String repoRoot() {
        return System.getProperty("repo.root", "D:/projects/Enterprise");
    }

    public String buildPath() {
        return System.getProperty("build.path", "_environment/build");
    }

    public String buildOrderFile() {
        return System.getProperty("build.order.file", "build.properties");
    }

    public String buildOrderKey() {
        return System.getProperty("build.order.key", "modules.to.include");
    }

    public String[] subModuleNames() {
        return new String[]{"business", "general", "persistence", "presentation", "web"};
    }

    public String rootPomTemplate() {
        return System.getProperty("template.pom.root", "root/pom.xml");
    }

    public String modulePomTemplate() {
        return System.getProperty("template.pom.module", "module/pom.xml");
    }

    public String businessPomTemplate() {
        return System.getProperty("template.pom.business", "business/pom.xml");
    }

    public String generalPomTemplate() {
        return System.getProperty("template.pom.general", "general/pom.xml");
    }

    public String persistencePomTemplate() {
        return System.getProperty("template.pom.persistence", "persistence/pom.xml");
    }

    public String presentationPomTemplate() {
        return System.getProperty("template.pom.presentation", "presentation/pom.xml");
    }

    public String webPomTemplate() {
        return System.getProperty("template.pom.web", "web/pom.xml");
    }

    public String businessPattern() {
        return System.getProperty("business.presents.checker", "business");
    }

    public String generalPattern() {
        return System.getProperty("general.presents.checker", "general");
    }

    public String persistencePattern() {
        return System.getProperty("persistence.presents.checker", "persistence");
    }

    public String presentationPattern() {
        return System.getProperty("presentation.presents.checker", "presentation");
    }

    public String webPattern() {
        return System.getProperty("web.presents.checker", "web");
    }

    public String resourcePattern() {
        return System.getProperty("resource.dir", "resource");
    }
}
