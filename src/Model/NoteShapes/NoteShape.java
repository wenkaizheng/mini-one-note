package Model.NoteShapes;


import Model.NoteModel;

import java.io.Serializable;
import java.util.Observable;

/**
 * This is the abstract class for all other classes in NoteShapes. Every
 * other classes in NoteShapes should extend this class
 * @author Jiacheng Yang
 */
public abstract class NoteShape extends Observable implements Serializable {
    /**
     * for serializable
     */
    private static final long serialVersionUID = 2L;

    /**
     * set changed and notify should be called.
     */
    abstract public void reDraw();

    /**
     * This method returns the functionality bounded to the class.
     * @return a instance of NoteModel.Functionality
     */
    abstract public NoteModel.Functionality getThisFunctionality();
}
