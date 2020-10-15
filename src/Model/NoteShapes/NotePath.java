package Model.NoteShapes;

import Model.NoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores all necessary information related NotePen.
 * @author Jiacheng Yang, Wenkai Zheng
 */
public class NotePath extends NoteShape {

    private List<Double> xs;
    private List<Double> ys;
    private List<String> operations;
    private int size;
    private String color;
    private double width;
    private final NoteModel.Functionality sign = NoteModel.Functionality.DRAW;

    /**
     * simple constructor
     * @param color current color
     * @param width current width 
     */
    public NotePath(String color, double width) {
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        operations = new ArrayList<>();
        this.color = color;
        this.width = width;
        size = 0;
    }
    /**
     * this method is used for redrawing from save model
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
     * add all positions and commands from user event
     * @param x x position 
     * @param y y position
     * @param s command to execute different function
     */
    public void add(Double x, Double y, String s) {
        xs.add(x);
        ys.add(y);
        operations.add(s);
        size++;
        setChanged();
        notifyObservers(this);
    }
    /**
     * return the sign from model
     */
    @Override
    public NoteModel.Functionality getThisFunctionality() {
        return this.sign;
    }
    /**
     * simple getter
     * @return color 
     */
    public String getColor() {
        return color;
    }
    /**
     * simple getter
     * @return width
     */
    public double getWidth() {
        return width;
    }
    /**
     * simple getter
     * @return size from current list
     */
    public int getLength() {
        return size;
    }
    /**
     * simple getter
     * @param i certain index
     * @return x position from certain index
     */

    public double getXAt(int i) {
        return xs.get(i);
    }
    /**
     * simple getter
     * @param i certain index
     * @return y position from certain index
     */
    public double getYAt(int i) {
        return ys.get(i);
    }
    /**
     * simple getter
     * @param i certain index
     * @return command from certain index
     */

    public String getOperationAt(int i) {
        return operations.get(i);
    }
}
