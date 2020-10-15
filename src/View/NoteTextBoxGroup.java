package View;

import Controller.NoteController;
import Model.NoteShapes.NoteTextBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class is used to perform view functions in NoteTextBox. It connects to the controller and add the new
 * NoteTextBox into the NoteModel.shape using controller. It also help process the data when loading the object files.
 * 
 * @author Guoxin Huang
 *
 */
class NoteTextBoxGroup {

	private static NoteController controller;

	/**
	 * Constructor
	 * 
	 * @param controller: controller connecting to the model
	 */
	NoteTextBoxGroup(NoteController controller) {
		NoteTextBoxGroup.controller = controller;
	}

	/**
	 * Called when loading object noteTextBox from saved file. This method help restore noteTextBox and put them into
	 * the Group.
	 * 
	 * @param group: group in the NoteView class
	 * @param noteTextBox: NoteTextBox to be restored
	 */
	static void process(Group group, NoteTextBox noteTextBox) {
		setUpTextArea(noteTextBox, group);
	}

	/**
	 * Add a new NoteTextBox
	 * 
	 * @param group: group in the NoteView class
	 */
	static void addTextBox(Group group) {
		NoteTextBox noteTextBox = new NoteTextBox();
		setUpTextArea(noteTextBox, group);
		controller.addShape(noteTextBox);
	}

	/**
	 * This helper function adds remove function and choosing font color and size function for a text box
	 * 
	 * @param noteTextBox a instance of NoteTextBox
	 * @param             group: group in the NoteView class
	 */
	private static void setUpTextArea(NoteTextBox noteTextBox, Group group) {
		VBox vBox = addGroup(noteTextBox, group);
		HBox hBox = (HBox) vBox.getChildren().get(0);
		Button closeButton = (Button) hBox.getChildren().get(hBox.getChildren().size() - 1);
		closeButton.setOnAction(e -> {
			group.getChildren().remove(vBox);
			controller.deleteShape(noteTextBox);
		});
	}

	/**
	 * add the current Vbox containing TextArea to the group
	 * @param curTextBox a instance of NoteTextBox
	 * @param group: group in the NoteView class
	 * @return a instance VBox
	 */
	private static VBox addGroup(NoteTextBox curTextBox, Group group) {
		TextArea textarea = new TextArea(curTextBox.getContent());
		textarea.setMaxSize(curTextBox.getWidth(), curTextBox.getHeight());
		textarea.setPrefHeight(curTextBox.getHeight());
		textarea.setPrefWidth(curTextBox.getWidth());
		textarea.setStyle("-fx-font-size: " + String.valueOf(curTextBox.getFontSize()) + "pt;"
				+ "-fx-text-inner-color: " + curTextBox.getColor() + ";");
		HBox hbox = new HBox();

		ColorPicker colorPicker = new ColorPicker();
		colorPicker.setMaxWidth(30);
		colorPicker.setValue(StringToColor(curTextBox.getColor()));
		colorPicker.setOnAction((ActionEvent t) -> {
			curTextBox.setColor(convertColor(colorPicker.getValue()));
			textarea.setStyle("-fx-font-size: " + String.valueOf(curTextBox.getFontSize()) + "pt;"
					+ "-fx-text-inner-color: " + curTextBox.getColor() + ";");
		});

		TextField textField = new TextField(String.valueOf(curTextBox.getFontSize()));
		textField.setPrefWidth(60);
		Button sub = new Button("set");
		sub.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				curTextBox.setFontSize(Double.parseDouble(textField.getText()));
				textarea.setStyle("-fx-font-size: " + String.valueOf(curTextBox.getFontSize()) + "pt;"
						+ "-fx-text-inner-color: " + curTextBox.getColor() + ";");

			}
		});

		hbox.getChildren().addAll(new Label("Size:"), textField, sub, colorPicker, new Button("X"));
		hbox.setAlignment(Pos.CENTER_RIGHT);
		VBox vBox = new VBox(hbox);
		vBox.getChildren().addAll(textarea);

		vBox.setPadding(new Insets(1, 1, 1, 1)); // margins around the whole grid
		vBox.setStyle("-fx-background-color: rgba(53,89,119,0.2);");
		group.getChildren().addAll(vBox);

		vBox.setTranslateX(curTextBox.getFinalX());
		vBox.setTranslateY(curTextBox.getFinalY());

		vBox.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			curTextBox.setOriginalX(e.getSceneX());
			curTextBox.setOriginalY(e.getSceneY());
			curTextBox.setXChanged(vBox.getTranslateX());
			curTextBox.setYChanged(vBox.getTranslateY());

			vBox.toFront();
		});

		vBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			double offsetX = e.getSceneX() - curTextBox.getOriginalX();
			double offsetY = e.getSceneY() - curTextBox.getOriginalY();
			curTextBox.setFinalX(curTextBox.getXChanged() + offsetX);
			curTextBox.setFinalY(curTextBox.getYChanged() + offsetY);

			vBox.setTranslateX(curTextBox.getFinalX());
			vBox.setTranslateY(curTextBox.getFinalY());
		});

		textarea.setOnKeyReleased(event -> {
			curTextBox.setContent(textarea.getText());
		});
		return vBox;
	}

	/**
	 * convert a specific Color object to a formated String
	 * 
	 * @param c: the color to be changed
	 * @return the string indicating the color
	 */
	private static String convertColor(Color c) {
		return String.format("#%02X%02X%02X", (int) (c.getRed() * 255), (int) (c.getGreen() * 255),
				(int) (c.getBlue() * 255));
	}

	/**
	 * convert a specific String to a Color object
	 * 
	 * @param color: the String to be changed
	 * @return the Color indicating the color represented by the String
	 */
	private static Color StringToColor(String color) {
		return Color.web(color, 1.0);
	}

}
