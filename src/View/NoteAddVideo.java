package View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controller.NoteController;
import Model.NoteModel;
import Model.NoteShapes.NoteVideo;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * This class assigns the functionality of adding an video to the note.
 * @author Shu Lin
 *
 */
class NoteAddVideo extends Stage {
	private double x0, y0, x1, y1;
	private static NoteController controller;
	private Canvas canvas;
	private static Group group;
	// private static MediaPlayer player;
	private static List<MediaPlayer> listPlayer;
	private static List<Pane> listPane;


	/**
	 * This is the constructor.
	 * 
	 * @param gc The GraphicsContext of this note.
	 * @param controller The controller for this program.
	 * @param group The Group objects of the javafx.
	 */
    NoteAddVideo(GraphicsContext gc, NoteController controller, Group group) {

		super();
		// Treats this like a modal dialogue
		initModality(Modality.APPLICATION_MODAL);
		this.canvas = gc.getCanvas();
        NoteAddVideo.group = group;
        NoteAddVideo.controller = controller;
		this.setTitle("Video Insertion");
		Label label = new Label("Please Enter The Video Address: ");
		TextField input = new TextField();
		Button button1 = new Button("Cancel");
		Button button2 = new Button("Confirm");
		button1.setOnAction(e -> this.close());
		addVideo(button2, input);
		VBox layout = new VBox(3);
		HBox buttons = new HBox(2);
		buttons.setAlignment(Pos.TOP_RIGHT);
		buttons.getChildren().addAll(button2, button1);
		layout.getChildren().addAll(label, input, buttons);
		Scene scene1 = new Scene(layout, 400, 80);
		if (listPlayer == null)
			listPlayer = new ArrayList<>();
		if (listPane == null)
			listPane = new ArrayList<>();
		this.setScene(scene1);
	}

    /**
     * The warning message of file does not exist.
     */
    private void setWarning() {
    	Alert alert = new Alert(AlertType.ERROR);

		alert.setContentText("Invalid address: This is not a video.");
		alert.showAndWait();

    }
    
    /**
     * This function assign the event listeners to the note.
	 * @param button The ok button that confirms the url.
	 * @param input The textfield that user inputs the url.
     */
	private void addVideo(Button button, TextField input) {
		button.setOnAction((event) -> {
			final String url = input.getText();
			File file = new File(url);
			try {
				
			Media media = new Media(new File(url).toURI().toString());
			if (file.exists()) {
			canvas.setCursor(Cursor.CROSSHAIR);


			controller.setCurrentFunctionality(NoteModel.Functionality.VIDEO);

			canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,

					new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {

							if (controller.isCurrentFunctionality(NoteModel.Functionality.VIDEO)) {
								x0 = event.getX();
								y0 = event.getY();
								canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
							}
						}
					});

			canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

					if (controller.isCurrentFunctionality(NoteModel.Functionality.VIDEO)) {

						x1 = event.getX();
						y1 = event.getY();

						double x = (x0 > x1) ? x1 : x0;
						double y = (y0 > y1) ? y1 : y0;
						double w = (x0 > x1) ? x0 - x1 : x1 - x0;
						double h = (y0 > y1) ? y0 - y1 : y1 - y0;

						NoteVideo videoObject = new NoteVideo(x, y, w, h, url);
						controller.addShape(videoObject);
						videoObject.reDraw();
						canvas.setCursor(Cursor.DEFAULT);

						controller.setCurrentFunctionality(NoteModel.Functionality.NULL);
						canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
					}
				}
			});
			} else {
				setWarning();

				return;
			}
			} catch (MediaException ex) {
				
                setWarning();
				return;
			}

			this.close();
		});
	}

	/**
     * The process of adding the image to the canvas.
     * @param video The NoteVideo object that will be added to the note.
     */
	static void process(NoteVideo video) {
		double x = video.getFinalX();
		double y = video.getFinalY();
		double w = video.getW();
		double h = video.getH();
		String url = video.getUrl();

		addVideo(x, y, w, h, url, video);
	}

	/**
	 * This function add the video.
	 * 
	 * @param x The x coordination
	 * @param y The y coordination
	 * @param w The width of the video
	 * @param h The height of the video
	 * @param url The url of the video
	 * @param video The NoteVideo object
	 */
	private static void addVideo(double x, double y, double w, double h, String url
            , NoteVideo video) {

		Media media = new Media(new File(url).toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		listPlayer.add(player);

		MediaView view = new MediaView(player);
		player.setAutoPlay(true);
		// oracleVid.setCycleCount(MediaPlayer.INDEFINITE);
		// player.setCycleCount(MediaPlayer.INDEFINITE);

		/*
		 * WebView view =new WebView(); view.getEngine().load(url);
		 */

		Pane box = new Pane();
		box.setTranslateX(x);
		box.setTranslateY(y);
		box.setPrefHeight(h);
		box.setPrefWidth(w);
		// view.setFitHeight(h);

		box.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			// if(controller.getCurrentFunctionality()==NoteModel.Functionality.VIDEO) {
			video.setOriginalX(e.getSceneX());
			video.setOriginalY(e.getSceneY());
			video.setXChanged(box.getTranslateX());
			video.setYChanged(box.getTranslateY());

			box.toFront();
			// }
		});

		box.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			// if(controller.getCurrentFunctionality()==NoteModel.Functionality.VIDEO) {
			double offsetX = e.getSceneX() - video.getOriginalX();
			double offsetY = e.getSceneY() - video.getOriginalY();
			video.setFinalX(video.getXChanged() + offsetX);
			video.setFinalY(video.getYChanged() + offsetY);

			box.setTranslateX(video.getFinalX());
			box.setTranslateY(video.getFinalY());
			// }
		});

		box.setOnMouseClicked((e) -> {
			// if(controller.getCurrentFunctionality()==NoteModel.Functionality.VIDEO) {
			if (e.getButton() == MouseButton.SECONDARY) {
				// listPlayer.get(listPane.indexOf(box)).stop();
				int index = listPane.indexOf(box);
				MediaPlayer play = listPlayer.get(index);
				player.stop();
				group.getChildren().remove(box);
				controller.deleteShape(video);
				listPane.remove(box);
				listPlayer.remove(play);

			}
			// }
		});

		view.setFitWidth(w);
		view.setFitHeight(h);
		// view.setPrefSize(w, h);
		box.getChildren().add(view);
		group.getChildren().add(box);
		listPane.add(box);
	}

	/**
	 * This function stop the video from playing.
	 */
	static void stop() {
		for (MediaPlayer o : listPlayer) {
			o.stop();
		}
		listPlayer.clear();
		listPane.clear();
	}
}
