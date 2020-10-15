package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

import Model.NoteModel;
import Model.NotePage;
import Model.NoteSection;
import Model.NoteShapes.NoteShape;


/**
 * This is the controller for the model. View should deal with the model
 * through this controller.
 *
 * @author Jiacheng Yang, Wenkai Zheng
 */
public class NoteController {
    /**
     * This is a instance of model
     */
    private NoteModel model;

    /**
     * This method is the constructor of the controller.
     *
     * @param model a instance of NoteModel
     */
    public NoteController(NoteModel model) {
        this.model = model;
    }

    /**
     * This method makes a new section in model. It makes sure that the id is
     * not duplicated. (unless exceeding the upper bound of java integer 2^32)
     */
    public void newSection() {
        int id = model.getNextSectionIdWithIncrement();
        model.getSectionMap().put(id, new NoteSection(id, "Section " + id));
    }

    /**
     * This method makes a new page in the section which is specified by
     * parameter id.
     *
     * @param sectionId a integer that represents the section.
     */
    public void newPage(int sectionId) {
        NoteSection section = model.getSectionMap().get(sectionId);
        int id = section.getNextPageIDWithIncrement();
        section.getPageMap().put(id, new NotePage(id, "Page " + id));
    }

    /**
     * This method sets a section's name.
     *
     * @param id   a integer that represents the section.
     * @param name a string that is the section's new name.
     */
    public void setSectionName(int id, String name) {
        model.getSection(id).setName(name);
    }

    /**
     * This method sets a page's name.
     *
     * @param sectionId a integer that represents the section.
     * @param pageId    a integer that represents the page.
     * @param name      a string that is the section's new name.
     */
    public void setPageName(int sectionId, int pageId, String name) {
        model.getSection(sectionId).getPage(pageId).setName(name);
    }

    /**
     * This method removes a section from model.
     *
     * @param sectionId a integer that represents the section.
     */
    public void removeSection(int sectionId) {
        model.removeSection(sectionId);
    }

    /**
     * This method remove a page from a section
     *
     * @param sectionId a integer that represents the section.
     * @param pageId    a integer that represents the page.
     */
    public void removePageFromSection(int sectionId, int pageId) {
        model.getSection(sectionId).removePage(pageId);
    }

    /**
     * This method returns the current color in model
     *
     * @return a string of color.
     */
    public String getColor() {
        return model.currentColor;
    }

    /**
     * This method sets the current color in model
     *
     * @param color a string of color.
     */
    public void setColor(String color) {
        model.currentColor = color;
    }

    /**
     * This method add a shape to current page.
     *
     * @param noteShape a instance of noteShape.
     */
    public void addShape(NoteShape noteShape) {
        model.addShapeToCurrentPage(noteShape);
    }

    /**
     * This method sets the model's current functionality
     *
     * @param functionality a enumeration type
     */
    public void setCurrentFunctionality(NoteModel.Functionality functionality) {
        model.currentFunctionality = functionality;
    }

    /**
     * This method checks if the given functionality is the same
     * as the one stored in model.
     *
     * @param functionality a enumeration type
     * @return a boolean that indicates if the given functionality is the same
     * as the one stored in model.
     */
    public boolean isCurrentFunctionality(NoteModel.Functionality functionality) {
        return model.currentFunctionality == functionality;
    }

    /**
     * This method returns the currentFunctionality in model
     *
     * @return a enumeration type.
     */
    public NoteModel.Functionality getCurrentFunctionality() {
        return model.currentFunctionality;
    }

    /**
     * This method returns the pen size stored in model
     *
     * @return a double that represents the pen size
     */
    public double getPenSize() {
        return model.getPenSize();
    }

    /**
     * This method sets the pen size.
     *
     * @param s a double that represents the pen size.
     */
    public void setPenSize(double s) {
        model.setPenSize(s);
    }

    /**
     * This methods saves the model's hashmap to the given file.
     *
     * @param file a file object
     * @throws IOException a IOException that is thrown when the file cannot
     *                     be written.
     */
    public void saveModelTo(File file) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(model.getSectionMap());
            System.out.println("Your note is saved...");
        }
    }

    /**
     * This method reads a saved file and sets up model
     *
     * @param file a file object
     * @throws IOException a IOException that is thrown when the file cannot
     *                     be read.
     */
    public void readModelFrom(File file) throws IOException {
        // read red file
        HashMap<?, ?> sectionMap;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            sectionMap = (HashMap<?, ?>) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load your note...");
            e.printStackTrace();
            return;
        }
        System.out.println("Loading your note...");
        // find next section if and current section.
        int nextSectionId = -1;
        int currentSection = -1;
        model.setSectionMap((HashMap<Integer, NoteSection>) sectionMap);
        List<Observer> observers = model.getObservers();
        for (Object o : sectionMap.values()) {
            // currentSection is the one with smallest id.
            if (((NoteSection) o).getID() < currentSection || currentSection == -1) {
                currentSection = ((NoteSection) o).getID();
            }
            // add observer to NoteSections.
            for (Observer observer : observers) {
                ((NoteSection) o).addObserver(observer);
            }
            // nextSectionId is the biggest id +1.
            if (nextSectionId < ((NoteSection) o).getID()) {
                nextSectionId = ((NoteSection) o).getID();
            }
        }
        model.setCurrentSectionId(currentSection);
        model.setNextSectionId(nextSectionId + 1);// we +1 here
        // reset current functionality.
        model.currentFunctionality = NoteModel.Functionality.NULL;
    }

    /**
     * This method deletes a shape from current page.
     *
     * @param noteShape a instance of noteShape
     */
    public void deleteShape(NoteShape noteShape) {
        model.deleteShapeFromCurrentPage(noteShape);
    }


    /**
     * This methods returns a sorted list of NoteSections.
     *
     * @return a sorted a ArrayList of NoteSections.
     */
    public ArrayList<NoteSection> getAllSection() {
        Collection<NoteSection> sections = model.getAllSections();
        ArrayList<NoteSection> sectionList = new ArrayList<>(sections);
        Collections.sort(sectionList);
        return sectionList;
    }

    /**
     * This method returns a sorted list of Pages in given section.
     *
     * @param id a integer that represents the section.
     * @return a sorted list of Pages in given section.
     */
    public ArrayList<NotePage> getAllPageInSection(int id) {
        Collection<NotePage> pages = model.getSection(id).getAllPage();
        ArrayList<NotePage> pageList = new ArrayList<>(pages);
        Collections.sort(pageList);
        return pageList;
    }

    /**
     * This method call all NoteShape objects in current page to reDraw
     * themselves.
     *
     * @param sectionId a integer that represents the section.
     * @param pageId    a integer that represents the page.
     */
    public void reDraw(int sectionId, int pageId) {
        List<NoteShape> shapes = model.getSection(sectionId).getPage(pageId).getShapes();

        for (NoteShape shape : shapes) {
            model.currentFunctionality = shape.getThisFunctionality();
            shape.reDraw();
        }
        // reset current functionality
        model.currentFunctionality = NoteModel.Functionality.NULL;
    }

    /**
     * This method sets the current section id
     *
     * @param id a integer that represents the section.
     */
    public void setCurrentSection(int id) {
        model.setCurrentSectionId(id);
    }

    /**
     * This method sets the current page id
     *
     * @param id a integer that represents the page.
     */
    public void setCurrentPage(int id) {
        model.setCurrentPageId(id);
    }

    /**
     * This method returns the current section id
     *
     * @return a integer that represents the section.
     */
    public int getCurrentSection() {
        return model.getCurrentSectionId();
    }

    /**
     * This method returns the current page id
     *
     * @return a integer that represents the page.
     */
    public int getCurrentPage() {
        return model.getCurrentPageId();
    }

}