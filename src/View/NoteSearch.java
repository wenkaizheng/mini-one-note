package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Controller.NoteController;
import Model.NoteModel;
import Model.NoteShapes.NoteLink;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
/**
 * This class assigns the functionality for enable user to give url and visit websites 
 * @author Wenkai Zheng
 *
 */
class NoteSearch extends Stage {
	private double x0, y0, x1, y1;
	private static NoteController controller;
	private Canvas canvas;
	private static Group group;
	//private static WebView view;
	//private static WebEngine enige;

	private static List<WebView> listPlayer = new ArrayList<>();
	private static List<BorderPane> listPane = new ArrayList<>();
	private static List<NoteLink> listLink = new ArrayList<>();
    /**
     * constructor
     * @param gc          get canvas from gc and used to drag 
     * @param controller  the controller 
     * @param group   the group for all view
     */
	NoteSearch(GraphicsContext gc, NoteController controller, Group group) {

		super();
		// Treats this like a modal dialogue
		initModality(Modality.APPLICATION_MODAL);
		this.canvas = gc.getCanvas();
		NoteSearch.group = group;
		this.setAlwaysOnTop(false);
		NoteSearch.controller = controller;
		this.setTitle("Website Insertion");
		Label label = new Label("Please Enter Website: ");

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
		this.setScene(scene1);
	}

    /**
     * add shape link to model for redraw ,
     * if address is invalid we have the alert
     * make canvas have press and drag
     * the search can be drag to all place in app
     * @param button  the OK button for enter address
     * @param input   the text field for user to enter 
     */
	private void addVideo(Button button, TextField input) {
		button.setOnAction((event) -> {
			final String url = input.getText();

			canvas.setCursor(Cursor.CROSSHAIR);
			try {
				new URL(url).toURI();
				// video.setfile(url);

			}

			// If there was an Exception
			// while creating URL object
			catch (Exception e) {
				setupWarning();
				return;
			}

			controller.setCurrentFunctionality(NoteModel.Functionality.SEARCH);

			canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,

					new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if (controller.isCurrentFunctionality(NoteModel.Functionality.SEARCH)) {
								x0 = event.getX();
								y0 = event.getY();
								canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
							}
						}
					});

			canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (controller.isCurrentFunctionality(NoteModel.Functionality.SEARCH)) {

						x1 = event.getX();
						y1 = event.getY();

						double x = (x0 > x1) ? x1 : x0;
						double y = (y0 > y1) ? y1 : y0;
						double w = (x0 > x1) ? x0 - x1 : x1 - x0;
						double h = (y0 > y1) ? y0 - y1 : y1 - y0;

						NoteLink videoObject = new NoteLink(x, y, w, h, url);
						controller.addShape(videoObject);
						videoObject.reDraw();
						canvas.setCursor(Cursor.DEFAULT);

						controller.setCurrentFunctionality(NoteModel.Functionality.NULL);
						canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
					}
				}
			});

			this.close();
		});
	}
	/**
	 * when view need to update if will work 
	 * @param video the shape which notifies view
	 */

	static void process( NoteLink video) {
		// public static void process(GraphicsContext gc, NoteLink video) {
		double x = video.getFinalX();
		double y = video.getFinalY();
		double w = video.getW();
		double h = video.getH();
		String url = video.getUrl();
		addVideo(x, y, w, h, url, video);
		// }
	}
    /**
     * when view need to update it will call process
     * and process call this function
     * web view will be put into a border pane
     * the border pane can be drag and it can be close as well
     * when the web view is close we delete it from both border bane
     * and model we also update player list link list and pane list
     * @param x  the correct place for x
     * @param y   the correct place for y
     * @param w   the width
     * @param h    the height
     * @param url   the address from text input
     * @param video  the shape which notifies view
     */
	private static void addVideo(double x, double y, double w, double h, String url,
                          NoteLink video) {
		/*
		 * Media media = new Media(new File(url).toURI().toString()); MediaPlayer player
		 * = new MediaPlayer(media); MediaView view = new MediaView(player);
		 * player.setAutoPlay(true); // oracleVid.setCycleCount(MediaPlayer.INDEFINITE);
		 * player.setCycleCount(MediaPlayer.INDEFINITE);
		 * 
		 * /** WebView view =new WebView(); view.getEngine().load(url);
		 */

        WebView view = new WebView();
		listPlayer.add(view);
        WebEngine engine = view.getEngine();
		// while(true) {

		engine.load(url);

		/*
		 * Pane box = new Pane();
		 * 
		 * box.setTranslateX(x); box.setTranslateY(y); box.setPrefHeight(h);
		 * box.setPrefWidth(w); // view.setFitHeight(h);
		 *
		 */
		BorderPane box = new BorderPane();
		box.setTranslateX(x);
		box.setTranslateY(y);
		box.setPrefHeight(h);
		box.setPrefWidth(w);
		box.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #661a33)");

		box.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			// if(controller.getCurrentFunctionality()==NoteModel.Functionality.SEARCH) {

			video.setOriginalX(e.getSceneX());
			video.setOriginalY(e.getSceneY());
			video.setXChanged(box.getTranslateX());
			video.setYChanged(box.getTranslateY());

			box.toFront();
			// }
		});

		box.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			// if(controller.getCurrentFunctionality()==NoteModel.Functionality.SEARCH) {
			double offsetX = e.getSceneX() - video.getOriginalX();
			double offsetY = e.getSceneY() - video.getOriginalY();
			video.setFinalX(video.getXChanged() + offsetX);
			video.setFinalY(video.getYChanged() + offsetY);

			box.setTranslateX(video.getFinalX());
			box.setTranslateY(video.getFinalY());
			// }
		});

		// view.setFitWidth(w);
		view.setPrefSize(w, h);
		// box.getChildren().add(view);
		box.setCenter(view);
		Button closeButton = new Button("Close");

		// closeButton.toFront();
		closeButton.setAlignment(Pos.TOP_LEFT);

		closeButton.setOnMouseClicked((e) -> {
			// if(controller.getCurrentFunctionality()==NoteModel.Functionality.SEARCH) {
			int index = listPane.indexOf(box);
			stop(listPlayer.get(index), index);
			group.getChildren().remove(box);
			controller.deleteShape(video);

			// }

		});
		// box.getChildren().add(closeButton);
		box.setTop(closeButton);
		group.getChildren().add(box);
		listPane.add(box);
		listLink.add(video);
	}
    /**
     * set up alert if address in incorrect
     */
	private static void setupWarning() {
		Alert alert = new Alert(AlertType.ERROR);

		alert.setContentText("Invalid address: Please re-enter a url ");
		alert.showAndWait();

	}
    /**
     * if stop the current web and set the current address from NoteLink
     * @param player the player need to stop
     * @param index  to remove list link list pane and list player
     */
	private static void stop(WebView player, int index) {
		String url = (String) player.getEngine().executeScript("window.location.href;");
		if (!url.equals("about:blank")) {
			listLink.get(index).setfile(url);
		}
		player.getEngine().load(null);
		listLink.remove(index);
		listPane.remove(index);
		listPlayer.remove(index);
	}
    /**
     * this one stop all players and clean all list 
     */
	static void stop() {
		while (!listPlayer.isEmpty()) {
			stop(listPlayer.get(0), 0);
		}
	}

}