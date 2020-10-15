package View;

import Controller.NoteController;
import Model.NoteShapes.NoteCorrection;
import Model.NoteModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
/**
 * This class assigns the functionality for earsing the pixel in the panel
 * we detect the mouse click and drap position and calculate to get all pixel in
 * the panel. Finally we clear those pixel in the panel
 * @author JiaCheng Yang
 *
 */
class NoteErase {
    private double x;
    private double y;
    private NoteController controller;
    private NoteCorrection correction;
    private final double height = 30.0, width = 30.0;
    /**
     * simple constructor
     * @param controller  controller from main class
     */
    NoteErase(NoteController controller) {
        this.controller = controller;
    }

   /**
    * when the view is updated this method will be called
    * @param gc  the tool we use 
    * @param correction  correction has all position we need to use
    */
    static void process(GraphicsContext gc, NoteCorrection correction) {
        int i = correction.getSize() - 1;
        gc.clearRect(correction.getX(i), correction.getY(i), correction.width
                , correction.height);
    }
    /**
     * this function will add correction to controller 
     * and also add positions for this correction
     * @param event mouse press event
     */ 

    void erasePressed(MouseEvent event) {
      if(controller.getCurrentFunctionality()==NoteModel.Functionality.ERASE) {
        correction = new NoteCorrection(height, width);
        controller.addShape(correction);
        correction.add(event.getX(), event.getY());
        x = event.getX();
        y = event.getY();
      }
    }
    /**
     * this function add position for current correction
     * and also calculate all position between all of press point and drag point
     * 
     * @param event mouse drag event
     */

    void eraseDragged(MouseEvent event) {
    	 if(controller.getCurrentFunctionality()==NoteModel.Functionality.ERASE) {
        double des_x = event.getX(), des_y = event.getY();
        double k = (des_y - y) / (des_x - x);
        double delta = 0.1;
        double sign_x = des_x - x > 0 ? 1 : -1;
        double delta_x = sign_x * (Math.sqrt((delta * delta) / (1 + k * k)));
        double delta_y = k * delta_x;

        for (double i = x, j = y; (sign_x > 0 && i < des_x) || (sign_x <= 0 && i > des_x); i += delta_x, j += delta_y) {

            correction.add(i, j);
        }
        x = event.getX();
        y = event.getY();

    }
    }
    /**
     * this function add position for current correction
     * for add function will notify view to update 
     * @param event mouse release event
     */
  
    void eraseReleased(MouseEvent event) {
    	 if(controller.getCurrentFunctionality()==NoteModel.Functionality.ERASE) {
        correction.add(event.getX(), event.getY());
        correction = null;
    	 }
    }
}