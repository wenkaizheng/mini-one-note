package Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import Model.NoteModel;
import Model.NotePage;
import Model.NoteSection;
import Model.NoteShapes.NotePath;

import org.junit.jupiter.api.Test;

/**
 * This class tests NoteModel, NoteSection, and NotePage
 * @author Jiacheng Yang, Wenkai Zheng
 */
class ModelTest  {

    /**
     * test constructors
     */
    @Test
    void modelConstructorTest(){
        NoteModel model = new NoteModel();
        NotePath  path =new NotePath("#000000",3.0);
        FakeView fv =new FakeView(model);
        assertEquals(model.currentColor,"#000000");
        assertEquals(model.getPenSize(),1.0);
        model.setPenSize(2.0);
        assertEquals(model.getPenSize(),2.0);
        NotePage firstPage = new NotePage(1,"derrick");
        NotePage SecondPage = new NotePage(2,"Towns");
        NotePage ThirdPage  = new NotePage(3,"zwk");
        
        NotePage pageOne =new NotePage(1,"mi");
        NotePage pageTwo =new NotePage(2,"long");
        NotePage pageThree =new NotePage(3,"ansheng");
        
        NoteSection firstSection = new NoteSection(1,"Rose");
        firstSection.addPage(3, ThirdPage);
        firstSection.addPage(1,firstPage);
        firstSection.addPage(2, SecondPage);
        
        
        NoteSection secondSection =new NoteSection(2,"luo");
        secondSection.addPage(1, pageOne);
        secondSection.addPage(2, pageTwo);
        secondSection.addPage(3, pageThree);


        assertEquals(model.currentFunctionality, NoteModel.Functionality.NULL);
        assertEquals(model.getObservers().size(),0);
        assertEquals(model.getNextSectionIdWithIncrement(),0);
        assertEquals(model.getNextSectionIdWithIncrement(),1);
        HashMap<Integer, NoteSection> map =new HashMap<>();
        map.put(1, firstSection);
        map.put(2,secondSection);

        model.setSectionMap(map);
        assertEquals(model.getSectionMap().size(),2);

        model.setCurrentSectionId(1);
        assertEquals(model.getCurrentSectionId(),1);

        model.setCurrentPageId(1);
        assertEquals(model.getCurrentPageId(),1);
        assertEquals(model.getAllSections().size(),2);
       
        assertEquals(model.getShapesInCurrentPage().size(),0);
        model.addObserver(fv);
        assertEquals(model.getObservers().size(),1);
        model.addShapeToCurrentPage(path);
        NotePage findPage =model.getSectionMap().get(1).getPage(1);
        assertEquals(findPage.getShapes().size(),1);
        
        model.setCurrentSectionId(2);
        model.setCurrentPageId(1);
        assertEquals(model.getCurrentSectionId(),2);
        assertEquals(model.getCurrentPageId(),1);
        model.addShapeToCurrentPage(path);
        NotePage findPage2 =model.getSectionMap().get(2).getPage(1);
        assertEquals(findPage2.getShapes().size(),1);
        model.deleteShapeFromCurrentPage(path);
        assertEquals(findPage2.getShapes().size(),0);
        assertTrue(model.removeSection(2));
        assertTrue(model.removeSection(1));
    }

    /**
     * Test an empty model
     */
    @Test
    void testEmptyModel(){
        NoteModel model = new NoteModel();
        model.setNextSectionId(10);
        assertEquals(model.getNextSectionIdWithIncrement(),10);
        assertEquals(model.getCurrentSectionId(), -1);
        assertNull(model.getSection(0));
        assertFalse(model.removeSection(0));
        model.deleteShapeFromCurrentPage(null);
        model.addShapeToCurrentPage(null);
        model.setCurrentSectionId(4);
        assertEquals(model.getCurrentSectionId(), -1);
        assertEquals(model.getCurrentPageId(),-1);
    }

    /**
     * test note page
     */
    @Test
    void testNotePage(){
        NotePage page1 = new NotePage(0, "1st");
        NotePage page2 = new NotePage(1, "2nd");
        assertEquals(page1.getId(), 0);
        assertEquals(page2.getId(), 1);
        assertTrue(page1.compareTo(page2)<0);
        assertEquals(page1.getName(), "1st");
        assertEquals(page2.getName(), "2nd");
        page1.setName("1st?");
        assertEquals(page1.getName(), "1st?");

    }


    /**
     * test note section
     */
    @Test
    void testNoteSection(){
        NoteSection section1 = new NoteSection(0, "1st");
        NoteSection section2 = new NoteSection(1, "2nd");
        assertEquals(section1.getNextPageIDWithIncrement(), 0);
        assertEquals(section1.getNextPageIDWithIncrement(), 1);
        assertEquals(section1.getNextPageIDWithIncrement(), 2);
        assertEquals(section1.getNextPageIDWithIncrement(), 3);
        assertEquals(section2.getNextPageIDWithIncrement(), 0);
        assertEquals(section2.getNextPageIDWithIncrement(), 1);
        assertEquals(section1.getName(), "1st");
        assertEquals(section2.getName(), "2nd");
        section1.setName("1st?");
        assertEquals(section1.getName(), "1st?");
        assertEquals(section1.getID(), 0);
        assertEquals(section2.getID(), 1);
        assertTrue(section1.compareTo(section2)<0);
        assertNull(section1.getPage(0));
        assertNull(section1.getPage(-1));
        assertNull(section1.getPage(1));
        NotePage page1 = new NotePage(0, "1stPage");
        NotePage page2 = new NotePage(1, "2ndPage");
        section1.addPage(0,page1);
        assertEquals(page1.compareTo(section1.getPage(0)),0);
        section1.addPage(1,page2);
        assertTrue(section1.contains(0));
        assertTrue(section1.contains(1));
        assertEquals(section1.getPageMap().size(),2);
        assertEquals(section1.getAllPage().size(),2);
        section1.removePage(-1);
        assertTrue(section1.contains(0));
        assertTrue(section1.contains(1));
        section1.removePage(0);
        assertFalse(section1.contains(0));
        assertTrue(section1.contains(1));

        NoteModel model = new NoteModel();
        int id=model.getNextSectionIdWithIncrement();
        model.getSectionMap().put(id, new NoteSection(id, "1st"));
        NoteSection section = model.getSection(id);
        for (id=section.getNextPageIDWithIncrement(); id<1000;id=
                section.getNextPageIDWithIncrement()){
            section.addPage(id, new NotePage(id, String.valueOf(id)));
        }
        model.setCurrentSectionId(0);
    }

    /**
     * test model section page with view.
     */
    @Test
    void testModelSectionPageWithView(){
        NoteModel model = new NoteModel();
        FakeView view =  new FakeView(model);

        int id = model.getNextSectionIdWithIncrement();
        model.getSectionMap().put(id, new NoteSection(id, String.valueOf(id)));
        id = model.getNextSectionIdWithIncrement();
        model.getSectionMap().put(id, new NoteSection(id, String.valueOf(id)));

        id = model.getSection(0).getNextPageIDWithIncrement();
        model.getSection(0).addPage(id, new NotePage(id, String.valueOf(id)));
        id = model.getSection(0).getNextPageIDWithIncrement();
        model.getSection(0).addPage(id, new NotePage(id, String.valueOf(id)));

        model.setCurrentSectionId(0);
        model.setCurrentPageId(0);
        model.addShapeToCurrentPage(new FakeShape(NoteModel.Functionality.NULL));

        model.addObserver(view);

        model.deleteShapeFromCurrentPage(null);

    }

    /**
     * test section
     */
    @Test
    void testSection(){
        NoteSection section = new NoteSection(1, "1");
        section.addPage(7607, new NotePage(7607, "7607"));
        section.addPage(6312, new NotePage(6312, "6312"));
        section.addPage(3053, new NotePage(3053, "3053"));
        section.addPage(6799, new NotePage(6799, "6799"));
        section.addPage(4649, new NotePage(4649, "4649"));
        section.addPage(9112, new NotePage(9112, "9112"));
        section.addPage(8574, new NotePage(8574, "8574"));
        section.addPage(5104, new NotePage(5104, "5104"));
        section.addPage(6932, new NotePage(6932, "6932"));

        NoteModel model = new NoteModel();
        model.getSectionMap().put(0, section);
        model.setCurrentSectionId(0);
        assertEquals(3053, model.getCurrentPageId());
    }
}