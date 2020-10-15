package Test;

import Model.NoteModel;
import Model.NoteShapes.NoteShape;

/**
 * This is a FakeShape since some time we dont want to use real shape
 * @author Jiacheng Yang
 */
class FakeShape extends NoteShape {
    private NoteModel.Functionality functionality;


    /**
     * the constructor
     * @param functionality a NoteModel.Functionality
     */
    FakeShape(NoteModel.Functionality functionality){
        this.functionality = functionality;
    }

    /**
     * required reDraw method
     */
    @Override
    public void reDraw() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * required getter
     * @return a NoteModel.Functionality
     */
    @Override
    public NoteModel.Functionality getThisFunctionality() {
        return functionality;
    }
}
