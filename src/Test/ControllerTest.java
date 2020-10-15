package Test;

import static org.junit.jupiter.api.Assertions.*;

import Controller.NoteController;
import Model.NoteModel;
import Model.NoteSection;
import Model.NoteShapes.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * This tests the controller and some model components
 * @author Jiacheng Yang
 */
class ControllerTest {
    FakeView view;
    NoteController controller;
    NoteModel model;

    /**
     * Setup before every test method is called
     */
    @BeforeEach
    void beforeEach() {
        model = new NoteModel();
        controller = new NoteController(model);
        view = new FakeView(controller);
        model.addObserver(view);
    }

    /**
     * delete saved file
     */
    @AfterAll
    static void try2cleanUp() {
        File file = new File("test.data");
        file.deleteOnExit();
    }

    /**
     * This method tests getters
     */
    @Test
    void controllerGetters() {
        assertEquals(-1, controller.getCurrentSection());
        assertEquals(-1, controller.getCurrentPage());
        assertEquals(NoteModel.Functionality.NULL, controller.getCurrentFunctionality());
        controller.setCurrentFunctionality(NoteModel.Functionality.ERASE);
        assertEquals(NoteModel.Functionality.ERASE, controller.getCurrentFunctionality());
        assertTrue(controller.isCurrentFunctionality(NoteModel.Functionality.ERASE));
        assertFalse(controller.isCurrentFunctionality(NoteModel.Functionality.NULL));
        assertEquals("#000000", controller.getColor());
        controller.setColor("this is not a color");
        assertEquals("this is not a color", controller.getColor());
        assertEquals(1.0, controller.getPenSize());
        controller.setPenSize(2.0);
        assertEquals(2.0, controller.getPenSize());
        controller.setPenSize(0);
        assertEquals(0, controller.getPenSize());


    }

    /**
     * a general test for controller
     */
    @Test
    void controllerTest() {
        // test new section
        controller.newSection();
        assertFalse(controller.getAllSection().isEmpty());
        assertEquals(0, controller.getAllSection().get(0).getID());

        // test new page
        controller.newPage(0);
        assertNotNull(controller.getAllSection().get(0).getPage(0));

        // test set section name
        controller.setSectionName(0, "newSectionName");
        assertEquals("newSectionName", controller.getAllSection().get(0).getName());

        // test set page name
        controller.newPage(0);
        controller.setPageName(0, 1, "newPage");
        assertEquals("newPage", controller.getAllSection().get(0).getPage(1).getName());

        // test set current section id
        controller.setCurrentSection(0);
        assertEquals(0, controller.getCurrentSection());
        assertEquals(0, controller.getCurrentPage());

        // test set current page
        controller.setCurrentPage(1);
        assertEquals(1, controller.getCurrentPage());


        // test remove page from section and getAllPageInSection
        controller.removePageFromSection(0, 1);
        assertEquals(1, controller.getAllPageInSection(0).size());
        assertNotNull(controller.getAllSection().get(0).getPage(0));
        assertNull(controller.getAllSection().get(0).getPage(1));


        // test add shape and remove
        controller.setCurrentSection(0);
        FakeShape shape = new FakeShape(NoteModel.Functionality.SEARCH);
        controller.addShape(shape);
        assertNotEquals(0,
                controller.getAllSection().get(0).getPage(0).getShapes().size());
        assertEquals(shape,
                controller.getAllSection().get(0).getPage(0).getShapes().get(0));
        controller.deleteShape(shape);
        assertEquals(0, controller.getAllSection().get(0).getPage(0).getShapes().size());


        // test remove section
        controller.newSection();
        assertEquals(2, controller.getAllSection().size());
        controller.removeSection(0);
        assertEquals(1, controller.getAllSection().size());
        assertEquals(1, controller.getAllSection().get(0).getID());


    }


    /**
     * test saving and reading file
     * @throws IOException a IOException
     */
    @Test
    void controllerSaveAndRead() throws IOException {
        HashMap<Integer, NoteSection> map = new HashMap<>();
        map.put(7607, new NoteSection(7607, "7607"));
        map.put(6312, new NoteSection(6312, "6312"));
        map.put(3053, new NoteSection(3053, "3053"));
        map.put(6799, new NoteSection(6799, "6799"));
        map.put(4649, new NoteSection(4649, "4649"));
        map.put(9112, new NoteSection(9112, "9112"));
        map.put(8574, new NoteSection(8574, "8574"));
        map.put(5104, new NoteSection(5104, "5104"));
        map.put(6932, new NoteSection(6932, "6932"));
        model.setSectionMap(map);
        controller.newPage(6932);
        controller.newPage(6932);
        controller.newPage(6932);
        File file = new File("test.data");
        controller.saveModelTo(file);
        assertTrue(file.exists());
        NoteModel newModel = new NoteModel();
        newModel.addObserver(new FakeView(controller));
        controller = new NoteController(newModel);
        controller.readModelFrom(file);
    }

    /**
     * test reDraw method.
     */
    @Test
    void controllerDraw(){
        controller.newSection();
        controller.newPage(0);
        controller.setCurrentSection(0);
        NoteCorrection correction = new NoteCorrection(10, 10);
        correction.add(1,2);
        correction.add(2,3);
        controller.addShape(correction);
        controller.addShape(new NoteImage(1,2,3,4, null));
        controller.addShape(new NoteLink(1,2,3,4,"url2"));
        NotePath path = new NotePath("color1", 3);
        path.add(1.0,2.0,"1st");
        path.add(1.0,2.0,"2nd");
        controller.addShape(path);
        controller.addShape(new NoteTextBox());
        controller.addShape(new NoteVideo(1,2,3,4,"url3"));
        controller.addShape(new FakeShape(NoteModel.Functionality.NULL));
        controller.reDraw(0,0);
        assertEquals(9, view.operations.size());
        assertEquals(view.operations.get(0), NoteModel.Functionality.ERASE);
        assertEquals(view.operations.get(1), NoteModel.Functionality.ERASE);
        assertEquals(view.operations.get(2), NoteModel.Functionality.IMAGE);
        assertEquals(view.operations.get(3), NoteModel.Functionality.SEARCH);
        assertEquals(view.operations.get(4), NoteModel.Functionality.DRAW);
        assertEquals(view.operations.get(5), NoteModel.Functionality.DRAW);
        assertEquals(view.operations.get(6), NoteModel.Functionality.TYPE);
        assertEquals(view.operations.get(7), NoteModel.Functionality.VIDEO);
        assertEquals(view.operations.get(8), NoteModel.Functionality.NULL);


    }
}
