package Model;

import Model.NoteShapes.NoteShape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents a page in Note book
 * @author Jiacheng Yang, Wenkai Zheng
 */
public class NotePage extends Observable implements Serializable,
        Comparable<NotePage> {

    /**
     * This variable is the id of this page
     */
    private final int id;

    /**
     * This is a list of NoteShape instances
     */
    private List<NoteShape> shapes;

    /**
     * This is the name of this page. It can be modified.
     */
    private String name;

    /**
     * This is the constructor for NotePage
     * @param id a integer that is the id of this page
     * @param name the name of this page.
     */
    public NotePage(int id, String name) {
        super();
        shapes = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    /**
     * getter for name
     * @return a string of name
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for name
     * @param name a string of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for id
     * @return a integer
     */
    public int getId() {
        return id;
    }



    /**
     * This method add a NoteShape to a list and assign observers to the NoteShape instance.
     *
     * @param noteShape a instance of NoteShape that has not been added into the shapes list.
     */
    void addShape(NoteShape noteShape) {
        shapes.add(noteShape);
    }

    /**
     * This method remove a NoteShape to a list and assign observers to the NoteShape instance.
     *
     * @param noteShape a instance of NoteShape that has not been added into the shapes list.
     */
    void deleteShape(NoteShape noteShape) {
        if (noteShape==null){
            return;
        }
        shapes.remove(noteShape);
    }


    /**
     * This method add a Observer to all shapes in its shapes list.
     *
     * @param o a Observer instance.
     */
    @Override
    public void addObserver(Observer o) {
        for (NoteShape noteShape : shapes) {
            noteShape.addObserver(o);
        }
        super.addObserver(o);
    }

    /**
     * This is the getter of shape list
     * @return a list of shapes that are in this page
     */
    public List<NoteShape> getShapes() {
        return shapes;
    }

    /**
     * This is defined for sorting
     * @param o a instance of NotePage
     * @return a integer that is less than one when this id is less than
     * other's id, and is bigger than one when this id is bigger than other's
     * id.
     */
    @Override
    public int compareTo(NotePage o) {
        return Integer.compare(this.id, o.id);
    }


}
