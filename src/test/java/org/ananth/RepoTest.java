package org.ananth;

import org.ananth.module.MultiModule;
import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepoTest {
    public static final Path ROOT = Paths.get("C:/Users/AnanthaneshanElampoo/Desktop/repo");
    Repo repo;

    @BeforeEach
    public void setUp() {
        repo = new Repo(ROOT, new String[]{"projectA"});
    }

    @AfterEach
    public void tearDown() {
    }

    @Order(1)
    @Test
    public void repoShouldThrowExceptionIfRootNotExist() {
        assertThrowsExactly(RuntimeException.class, () -> new Repo(Paths.get("test"), new String[]{"projectA"}));
    }

    @Order(2)
    @Test
    public void repoShouldHaveRoot() {
        assertEquals(ROOT, repo.root());
    }

    @Order(3)
    @Test
    public void repoShouldHaveModules() {
        assertEquals(1, repo.multiModules().length);
    }

    @Order(4)
    @Test
    public void repoShouldHaveMultiModule() {
        assertEquals(1, repo.multiModules().length);
    }

    @Order(5)
    @Test
    public void repoShouldHaveModuleDirExist() {
        assertThrowsExactly(RuntimeException.class, () -> new Repo(ROOT, new String[]{"project0"}));
    }

    @Order(6)
    @Test
    public void moduleMayNotHaveSubModules() {
        for (MultiModule a :
                repo.multiModules()) {
            if (a.subModules().size() > 0) fail();
        }
    }

    @Order(7)
    @Test
    public void moduleShouldHaveBusinessModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        for (MultiModule a :
                withBusiness.multiModules()) {
            if (a.subModules().size() == 0) fail();
            assertNotNull(a.subModules().get("BusinessModule"));

        }
    }

    @Order(8)
    @Test
    public void moduleShouldHaveGeneralModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        for (MultiModule a :
                withBusiness.multiModules()) {
            if (a.subModules().size() == 0) fail();
            assertNotNull(a.subModules().get("GeneralModule"));
        }
    }

    @Order(9)
    @Test
    public void moduleShouldHavePersistenceModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        for (MultiModule a :
                withBusiness.multiModules()) {
            if (a.subModules().size() == 0) fail();
            assertNotNull(a.subModules().get("PersistenceModule"));
        }
    }

    @Order(10)
    @Test
    public void moduleShouldHavePresentationModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        for (MultiModule a :
                withBusiness.multiModules()) {
            if (a.subModules().size() == 0) fail();
            assertNotNull(a.subModules().get("PresentationModule"));
        }
    }

    @Order(11)
    @Test
    public void moduleShouldHaveWebModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        for (MultiModule a :
                withBusiness.multiModules()) {
            if (a.subModules().size() == 0) fail();
            assertNotNull(a.subModules().get("WebModule"));
        }
    }

    @Order(11)
    @Test
    public void moduleShouldHaveAllModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        for (MultiModule a :
                withBusiness.multiModules())
            if (a.subModules().size() != 5) fail();
    }

    @Order(12)
    @Test
    public void moduleShouldHaveTwoMultiModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB", "projectC"});
        if (withBusiness.multiModules().length != 2) fail();
    }

    @Order(11)
    @Test
    public void moduleShouldCreateMavenDirStruct() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        withBusiness.prepareMavenDir();
    }

    @Order(13)
    @Test
    public void moduleShouldValidateMavenDirStruct() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        withBusiness.validateMavenDir();
    }

    @Order(14)
    @Test
    public void moduleShouldCreatePomFile() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        withBusiness.createPomFiles();
        withBusiness.isPomExists();
    }

    @Order(14)
    @Test
    public void shouldBeAbleToGeneratePom() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB"});
        withBusiness.generatePom();
    }

    @Order(15)
    @Test
    public void repoShouldReturnPrvModule() {
        Repo withBusiness = new Repo(ROOT, new String[]{"projectB", "projectC"});

        MultiModule mod1 = withBusiness.previousModule(withBusiness.multiModules()[1]);
        assertEquals(mod1, withBusiness.multiModules()[0]);

    }

    @Order(16)
    @Test
    public void createPomWithTwoProjectRepo() {
        assertDoesNotThrow(() -> {
            Repo withBusiness = new Repo(ROOT, new String[]{"projectB", "projectC"});
            withBusiness.prepareMavenDir();
            withBusiness.validateMavenDir();
            withBusiness.createPomFiles();
            withBusiness.isPomExists();
            withBusiness.generatePom();
        });


    }
}