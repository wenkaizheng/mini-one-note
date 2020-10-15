package View;

import Controller.NoteController;
import Model.NoteModel;
import Model.NoteShapes.NotePath;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * This class draw the everything for user when user uses the mouse to drag or move in the 
 * panel, and get done when user releases mouse. (Support the size and color)
 * @author Wenkai Zheng
 *
 */
class NotePen {

    private NotePath notePath;
    private NoteController controller;
    /**
     * simple constructor
     * @param controller a instance of controller
     */
    NotePen(NoteController controller) {
        this.controller = controller;
    }
    /**
     *  this function is called after view is updated
     *  it will check all position with certain position
     * @param gc the tool we use 
     * @param notePath the note path which notify view
     */

    static void process(GraphicsContext gc, NotePath notePath) {
        int i = notePath.getLength() - 1;
        if (notePath.getOperationAt(i).equals("press")) {
            move2(gc, notePath.getColor(), notePath.getWidth(),
                    notePath.getXAt(i), notePath.getYAt(i));
        } else if (notePath.getOperationAt(i).equals("lineTo")) {
            line2(gc, notePath.getXAt(i), notePath.getYAt(i));
        } else {
            release(gc, notePath.getColor());
        }

    }

    /**
     * this method is work when mouse press event is touched 
     * @param gc  a tool we use
     * @param color the color for pen
     * @param width  the width for pen
     * @param x  x position for user press
     * @param y  y position for user press
     */
    private static void move2(GraphicsContext gc, String color, double width,
                              double x, double y) {
        gc.beginPath();
        gc.setLineWidth(width);
        gc.moveTo(x, y);
        gc.lineTo(x, y);
        gc.setStroke(Color.web(color, 1.0));
        gc.stroke();
    }
    /**
     * this method is work when mouse drag event is touched
     * @param gc a tool we use
     * @param x  position for user drag
     * @param y  position for user drag
     */
    private static void line2(GraphicsContext gc, double x, double y) {
        gc.lineTo(x, y);
        gc.stroke();
    }
    /**
     * this method is work when mouse release is touched
     * @param gc  a tool we use
     * @param color color for all line 
     */
    private static void release(GraphicsContext gc, String color) {
        gc.setStroke(Color.web(color, 1.0));
    }
    /**
     *  this method is work when mouse press is touched
     *  it will create path instance and then add position for them
     *  also includes some commands
     * @param event mouse press event
     */
    void mousePressed(MouseEvent event) {
    	 if(controller.getCurrentFunctionality()==NoteModel.Functionality.DRAW) {
            notePath = new NotePath(controller.getColor(), controller.getPenSize());
            controller.addShape(notePath);
            notePath.add(event.getX(), event.getY(), "press");
            //notePath.add(event.getX(), event.getY(), "lineTo");
    	 }
    }
    /**
     * this method is work when mouse drag is touched
     * it will add position for path 
     * @param event mouse drag event
     */
    void mouseDragged(MouseEvent event) {
    	 if(controller.getCurrentFunctionality()==NoteModel.Functionality.DRAW) {
            notePath.add(event.getX(), event.getY(), "lineTo");
    	 }

    }
    /**
     * this method is work when mouse release is touched
     * @param event mouse release event
     */
    void mouseReleased(MouseEvent event) {
    	 if(controller.getCurrentFunctionality()==NoteModel.Functionality.DRAW) {
            notePath.add(-1.0, -1.0, "release");
            notePath = null;
    	 }

    }
}
