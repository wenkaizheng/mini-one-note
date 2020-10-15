package Test;

import Controller.NoteController;
import Model.NoteModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is just a fake view since we cannot call the real view
 * @author Jiacheng Yang
 */
public class FakeView implements Observer {


    List<NoteModel.Functionality> operations;
    private NoteController controller;
    private NoteModel model;


    /**
     * the constructor
     * @param controller a instance of NoteController
     */
    FakeView(NoteController controller) {
        operations = new LinkedList<>();
        this.controller = controller;
        this.model = null;
    }

    /**
     * the other constructor
     * @param model a instance of NoteModel
     */
    FakeView(NoteModel model) {
        this.model = model;
        this.controller = null;
    }

    /**
     * required update method. this records all functionality
     * @param o a observable function
     * @param arg a object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (this.controller != null)
            operations.add(controller.getCurrentFunctionality());
    }

}

