package Model.NoteShapes;
import Model.NoteModel;

import java.util.ArrayList;
import java.util.List;


/**
 * This class stores all necessary information related NoteErase.
 * @author Jiacheng Yang, Wenkai Zheng
 */
public class NoteCorrection extends NoteShape {

    private List<Double> xs;
    private List<Double> ys;
    public final double height, width;
    private final NoteModel.Functionality sign = NoteModel.Functionality.ERASE;
    private int size;
    /**
     * simple construction
     * @param height  the height for eraser
     * @param width  the width for eraser
     */
    public NoteCorrection(double height, double width){
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        this.height = height;
        this.width = width;
        size=0;
    }
    /**
     * add all x y and operations to list and notify view to update
     * @param x  x position
     * @param y  y position
     */
    public void add(double x, double y){
        xs.add(x);
        ys.add(y);
        size++;
        setChanged();
        notifyObservers(this);
    }
    /**
     * redraw for reading the saved model
     */
    @Override
    public void reDraw() {
        int i = size;
        for (size = 1; size<=i;size++){
            setChanged();
            notifyObservers(this);
        }
        size = i;
    }
    /**
     * get the sign from model
     */
    @Override
    public NoteModel.Functionality getThisFunctionality() {
        return this.sign;
    }
    /**
     * return certain x from x list
     * @param i index
     * @return index from all x position
     */
    public double getX(int i){
        return xs.get(i);
    }
    /**
     * return certain y from y list
     * @param i index
     * @return index from all y position
     */

    public double getY(int i){
        return ys.get(i);
    }
    /**
     * get all length for all list and they should be the same
     * @return integer which is a size for list 
     */
    public int getSize() {
        return size;
    }
}
