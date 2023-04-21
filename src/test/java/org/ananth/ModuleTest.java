//package org.example;
//
//import org.example.module.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ModuleTest {
//    BusinessModule bmOne;
//    List<Module> mList;
//
//    @BeforeEach
//    void setUp() {
//         bmOne = new BusinessModule("business",mList, repo);
//         mList = new ArrayList<>();
//        mList.add(bmOne);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void businessModuleShouldHaveName() {
//        BusinessModule bm = new BusinessModule("test",mList, repo);
//        assertEquals("test", bm.name());
//    }
//
//    @Test
//    void generalModuleShouldHaveName() {
//        GeneralModule bm = new GeneralModule("test",mList, repo);
//        assertEquals("test", bm.name());
//    }
//
//    @Test
//    void persistenceModuleShouldHaveName() {
//        PersistenceModule bm = new PersistenceModule("test",mList, repo);
//        assertEquals("test", bm.name());
//    }
//
//    @Test
//    void presentationModuleShouldHaveName() {
//        PresentationModule bm = new PresentationModule("test", mList, repo);
//        assertEquals("test", bm.name());
//    }
//
//    @Test
//    void webModuleShouldHaveName() {
//        WebModule bm = new WebModule("test");
//        assertEquals("test", bm.name());
//    }
//}