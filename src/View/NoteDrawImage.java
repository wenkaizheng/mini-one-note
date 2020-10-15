package View;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import Controller.NoteController;
import Model.NoteShapes.NoteImage;
import Model.NoteModel;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * This class assigns the functionality of adding an image to the canvas.
 * @author Shu Lin
 *
 */
class NoteDrawImage extends Stage {
    private double x0, y0, x1, y1;
    private Canvas canvas;
    private NoteController controller;
	private File currentFile;


	/**
	 * This is the constructor.
	 * @param canvas The canvas for the current note.
	 * @param controller The controller for this entire program.
	 */
	NoteDrawImage(Canvas canvas, NoteController controller) {
		super();
		//Treats this like a modal dialogue
		initModality(Modality.APPLICATION_MODAL);
		this.canvas = canvas;
		this.controller = controller;
    	this.initModality(Modality.APPLICATION_MODAL);
    	this.setTitle("Image Insertion");
    	Label label= new Label("Please Enter The Image Address: ");
    	TextField input = new TextField();
    	Button button1= new Button("Cancel");
    	Button button2= new Button("Confirm");
    	button1.setOnAction(e -> this.close());
    	addImage(button2, input);
    	VBox layout= new VBox(3);
    	HBox buttons = new HBox(2);
    	buttons.setAlignment(Pos.TOP_RIGHT);
    	buttons.getChildren().addAll(button2, button1);
    	layout.getChildren().addAll(label, input, buttons);
    	Scene scene1= new Scene(layout, 400, 80);
    	this.setScene(scene1);
    }


	/**
	 * This function assign the event listeners to the note.
	 * @param button The ok button that confirms the url.
	 * @param input The textfield that user inputs the url.
	 */
    private void addImage(Button button, TextField input) {
		button.setOnAction((event)->{
			String url = input.getText();
			Image image;
			canvas.setCursor(Cursor.CROSSHAIR);
			//If the user didn't enter a number in the port, warns them and lets them try again.
			try {
				File file = new File(url);
				currentFile = file;
				FileInputStream fileinput = new FileInputStream(file);
				image = new Image(fileinput);
				//controller.setCurrentFunctionality(NoteModel.Functionality.IMAGE);

				canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 

		                new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent event) {
		            	if (controller.isCurrentFunctionality(NoteModel.Functionality.IMAGE)) {
							x0 = event.getX();
							y0 = event.getY();
							canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
		                }
		            }
		        });

		        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
		                new EventHandler<MouseEvent>(){

		            @Override
		            public void handle(MouseEvent event) {
		            	if (controller.isCurrentFunctionality(NoteModel.Functionality.IMAGE)) {

		                x1 = event.getX();
		                y1 = event.getY();

		                double x = (x0 > x1) ? x1 : x0;
		                double y = (y0 > y1) ? y1 : y0;
		                double w = (x0 > x1) ? x0-x1 : x1-x0;
		                double h = (y0 > y1) ? y0-y1 : y1-y0;

		                NoteImage imageObject = new NoteImage(x,y,w,h, currentFile);
		                controller.addShape(imageObject);
		                canvas.setCursor(Cursor.DEFAULT);

		               // imageObject.setfile(file);
		                imageObject.reDraw();
		                controller.setCurrentFunctionality(NoteModel.Functionality.NULL);
		                canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
		            	}
		            }
		        });


				//gc.drawImage(image, 10, 10, 200, 200);
			}catch(IOException ex) {
				Alert alert = new Alert(AlertType.ERROR);

				alert.setContentText("Invalid address: Image does not exist.");
				alert.showAndWait();

				return;
			}

			this.close();
		});
    }

    /**
     * The process of adding the image to the canvas.
     * @param gc The GraphicsContext
     * @param image The NoteImage object that will be added to the canvas.
     */
    static void process(GraphicsContext gc, NoteImage image) {
    	gc.drawImage(image.getImage(), image.getX(), image.getY(), image.getW(), image.getH());
    }
} 