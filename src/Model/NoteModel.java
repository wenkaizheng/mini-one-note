package Model;

import java.io.Serializable;
import java.util.*;

import Model.NoteShapes.*;

/**
 * This class is the model for this project. It organizes every sub
 * components in the model package and provides useful public methods for
 * controller. It implements serializable just in case that this needs to
 * notify view.
 * @author Jiacheng Yang, Wenkai Zheng
 */
public class NoteModel implements Serializable {
    /**
     * This enumeration has all features that is supported by this model.
     * Every new feature is required to add a new key to this.
     */
    public enum Functionality {
        NULL,
        DRAW, TYPE, ERASE, IMAGE, VIDEO, SEARCH
    }

    /**
     * currentFunctionality is a reference for NoteModel.Functionality. This
     * variable makes sure the desired feature is used.
     */
    public Functionality currentFunctionality;

    /**
     * The currentColor that the user selected
     */
    public String currentColor;

    /**
     * The size of pen that the user selected
     */
    private double penSize;

    /**
     * the current Section id
     */
    private int currentSectionId;

    /**
     * the current page id
     */
    private int currentPageId;

    /**
     * This hash table maps the id of section to the section instance
     */
    private Map<Integer, NoteSection> sectionMap;

    /**
     * This list keeps track all observers that is added to the model.
     */
    private List<Observer> observers;

    /**
     * This variable is the next section id.
     */
    private int nextSectionId;

    /**
     * This is the constructor for the model. It sets default values to
     * variables.
     */
    public NoteModel() {// black
        currentColor = "#000000";
        penSize = 1.0;
        currentFunctionality = Functionality.NULL;
        observers = new LinkedList<>();
        sectionMap = new HashMap<>();
        nextSectionId = 0;
        currentSectionId=-1;
        currentPageId = -1;
    }


    /**
     * This is the getter for next section id. It automatically increments by
     * 1
     * @return a integer that is not used as id before
     */
    public int getNextSectionIdWithIncrement() {
        return nextSectionId++;
    }

    /**
     * This method returns all observers that has been added to this model.
     * @return a list of observers
     */
    public List<Observer> getObservers() {
        return observers;
    }

    /**
     * This method sets the map that is used for tracking sections. This
     * method should only be called when reading a saved object.
     * @param map a map whose key is integer and value is NoteSection object.
     */
    public void setSectionMap(HashMap<Integer, NoteSection> map) {
        sectionMap = map;
    }

    /**
     * This method is the getter for currentSectionId
     * @return a integer that is current section id
     */
    public int getCurrentSectionId() {
        return currentSectionId;
    }

    /**
     * This method is the getter for currentPageId
     * @return a integer that is current page id
     */
    public int getCurrentPageId() {
        return currentPageId;
    }

    /**
     * This method sets the next section id. This method should only be
     * called when reading a saved object.
     * @param nextSectionId a integer that has not been used as section id
     *                      and is bigger than all existing sections' ids.
     */
    public void setNextSectionId(int nextSectionId) {
        this.nextSectionId = nextSectionId;
    }

    /**
     * This method is the getter for section map
     * @return a map whose key is integer and value is NoteSection object.
     */
    public Map<Integer, NoteSection> getSectionMap() {
        return sectionMap;
    }

    /**
     * This method gets pages object that is in current section and has the
     * same id as currentPageId.
     * @return a object of NotePage
     */
    private NotePage getCurrentNotePage(){
        if (sectionMap.size() == 0) {
            return null;
        }
        return sectionMap.get(currentSectionId).getPage(currentPageId);
    }


    /**
     * This method add a NoteShape to a list and assign observers to the NoteShape instance.
     *
     * @param noteShape a instance of NoteShape that has not been added into the shapes list.
     */
    public void addShapeToCurrentPage(NoteShape noteShape) {
        for (Observer o : observers) {
            noteShape.addObserver(o);
        }
        NotePage page = getCurrentNotePage();
        if (page == null) {
            return;
        }
        page.addShape(noteShape);
    }

    /**
     * This method remove a NoteShape to a list and assign observers to the NoteShape instance.
     *
     * @param noteShape a instance of NoteShape that has not been added into the shapes list.
     */
    public void deleteShapeFromCurrentPage(NoteShape noteShape) {
        NotePage page = getCurrentNotePage();
        if (page == null) {
            return;
        }
        page.deleteShape(noteShape);
    }

    /**
     * This method add a Observer to all shapes in its shapes list.
     *
     * @param o a Observer instance.
     */
    public void addObserver(Observer o) {
        for (NoteSection section : sectionMap.values()) {
            section.addObserver(o);
        }
        observers.add(o);
    }

    /**
     * This method is the geteer for pen size
     * @return a double that represents the size of pe
     */
    public double getPenSize() {
        return penSize;
    }

    /**
     * This method is the setter for penSize
     * @param s a double that represents the size of pe
     */
    public void setPenSize(double s) {
        penSize = s;
    }

    /**
     * This method get all shapes in current page.
     * @return a list of NoteShapes.
     */
    public List<NoteShape> getShapesInCurrentPage() {
        return sectionMap.get(currentSectionId).getPage(currentPageId).getShapes();
    }


    /**
     * This method is the setter for currentSectionId. This method checks if
     * the corresponding sections exists in the map. If so, it sets
     * currentSectionId and resets currentPageId. Otherwise, does nothing.
     * @param id a integer
     */
    public void setCurrentSectionId(int id) {
        if (sectionMap.containsKey(id)) {
            currentSectionId = id;
            currentPageId = sectionMap.get(id).getDefaultPageID();
        }
    }

    /**
     * This method is the setter for currentPageId. Un like
     * setCurrentSectionId, this method does not checks if the currentPageId
     * exists in the current section.
     * @param id a integer
     */
    public void setCurrentPageId(int id) {
        currentPageId = id;
    }

    /**
     * This method removes a section from the section map by its id
     * @param id a integer
     * @return a boolean that indicates if the id was in the map or not.
     */
    public boolean removeSection(int id) {
        if (sectionMap.containsKey(id)) {
            sectionMap.remove(id);
            return true;
        }
        return false;
    }

    /**
     * This method return a collection of existing NoteSections
     * @return a collection of NoteSections
     */
    public Collection<NoteSection> getAllSections() {
        return sectionMap.values();
    }

    /**
     * This method finds section by id.
     * @param id a integer
     * @return a instance of NoteScetion.
     */
    public NoteSection getSection(int id) {
        return sectionMap.get(id);
    }

}
