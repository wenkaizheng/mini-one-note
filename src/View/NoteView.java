package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Controller.NoteController;
import Model.NoteModel;
import Model.NotePage;
import Model.NoteSection;
import Model.NoteShapes.NoteCorrection;
import Model.NoteShapes.NoteImage;
import Model.NoteShapes.NoteLink;
import Model.NoteShapes.NotePath;
import Model.NoteShapes.NoteTextBox;
import Model.NoteShapes.NoteVideo;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * This class represents the overall stage for UI,
 * all functions/events will be monitored by clike button or mouse
 * data serialization is also impelmented and resumed in here
 * for users to recover from last time using.
 * @author JiaCheng Yang, Wenkai Zheng, GuoXing Huan
 */
public class NoteView extends Application implements Observer {

    // protected Color color = Color.;
    private GraphicsContext gc;
    private NoteController controller;
    private NotePen pen;
    private NoteErase erase;
    private Group group = new Group();
    private NoteDrawImage drawImage;
    private Group sectionPageGroup = null;
    private VBox sectionVBox = null;
    private VBox pageVBox = null;
    private boolean haveLoadPage = false;
    private int height;
    private int width;
    private Canvas canvas;
    private boolean max = false;
    private Rectangle2D bounds;
    private NoteAddVideo addVideo;
    private NoteSearch search;

    /**
     * simple constructor
     * set up field
     */
    public NoteView() {
        NoteModel model = new NoteModel();
        model.addObserver(this);
        controller = new NoteController(model);
        pen = new NotePen(controller);
        new NoteTextBoxGroup(controller);
        erase = new NoteErase(controller);
        height = 700;
        width = 700;
        bounds = Screen.getPrimary().getVisualBounds();
        canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
    }

    /**
     * set up button for having type box in top
     *
     * @param v      horizontal box for contain this button
     * @param canvas canvas for change cursor
     */
    private void setupTypeButton(HBox v, Canvas canvas) {
        Button btn = new Button("TEXT BOX");
        btn.setPrefWidth(100);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                controller.setCurrentFunctionality(NoteModel.Functionality.TYPE);
                canvas.setCursor(Cursor.HAND);
                NoteTextBoxGroup.addTextBox(group);
            }
        });
        v.getChildren().add(btn);
    }

    /**
     * a method is using for set up buttons
     * a it has image with it
     *
     * @param tileButtons a container for store this button
     */
    private void setupVideoButton(HBox tileButtons) {
        addVideo = new NoteAddVideo(gc, controller, group);
        Button btn = new Button();
        Image image;
        ImageView imageView = null;
        try {
            image = new Image(new FileInputStream("open.png"));
            imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(17);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btn.setGraphic(imageView);
        btn.setPrefWidth(20);
        btn.setPrefHeight(17);

        // btn.setPrefWidth(100);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                controller.setCurrentFunctionality(NoteModel.Functionality.VIDEO);
                addVideo = new NoteAddVideo(gc, controller, group);
                addVideo.showAndWait();
            }
        });
        tileButtons.getChildren().add(btn);
    }

    /**
     * a methods is using for set up buttons
     * a it has image with it
     *
     * @param tileButtons used for store button
     */
    private void setupSearchButton(HBox tileButtons) {
        search = new NoteSearch(gc, controller, group);
        Button btn = new Button();
        Image image;
        ImageView imageView = null;
        try {
            image = new Image(new FileInputStream("google.png"));
            imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(17);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btn.setGraphic(imageView);
        btn.setPrefWidth(20);
        btn.setPrefHeight(17);

        // btn.setPrefWidth(100);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                controller.setCurrentFunctionality(NoteModel.Functionality.SEARCH);
                search = new NoteSearch(gc, controller, group);
                search.showAndWait();
            }
        });
        tileButtons.getChildren().add(btn);
    }

    /**
     * a methods is using for set up image buttons
     * a it has image with it
     *
     * @param tileButtons used for store button
     */
    private void setupImageButton(HBox tileButtons) {

        Button btn = new Button("Add Image");
        btn.setPrefWidth(100);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                controller.setCurrentFunctionality(NoteModel.Functionality.IMAGE);
                drawImage = new NoteDrawImage(gc.getCanvas(), controller);
                drawImage.showAndWait();
            }
        });
        tileButtons.getChildren().add(btn);
    }

    /**
     * set up button for having pen function in top
     *
     * @param v      horizontal box for contain this button
     * @param canvas canvas for change cursor
     */

    private void setupPenButton(HBox v, Canvas canvas) {
        Button btn = new Button();
        // btn.setPrefWidth(100);
        Image image;
        ImageView imageView = null;
        try {
            image = new Image(new FileInputStream("pen.png"));
            imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(17);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btn.setGraphic(imageView);
        btn.setPrefWidth(20);
        btn.setPrefHeight(17);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                controller.setCurrentFunctionality(NoteModel.Functionality.DRAW);
                canvas.setCursor(Cursor.CROSSHAIR);
                canvas.setOnMousePressed(pen::mousePressed);
                canvas.setOnMouseDragged(pen::mouseDragged);
                canvas.setOnMouseReleased(pen::mouseReleased);
            }
        });
        v.getChildren().add(btn);
        // return btn;
    }

    /**
     * set up button for having eraser function in top
     *
     * @param v      horizontal box for contain this button
     * @param canvas canvas for change cursor
     */
    private void setupEraser(HBox v, Canvas canvas) {
        Button btn = new Button();
        // Image image = new Image(getClass().getResourceAsStream("file:eraser.png"));
        Image image = null;
        ImageView imageView = null;
        try {
            image = new Image(new FileInputStream("eraser.png"));
            imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setFitHeight(17);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btn.setGraphic(imageView);
        btn.setPrefWidth(20);
        btn.setPrefHeight(17);

        // btn.setLayoutX(0);
        // btn.setLayoutY(90);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                Image image = new Image("file:def.jpg"); // pass in the image path
                canvas.setCursor(new ImageCursor(image));
                controller.setCurrentFunctionality(NoteModel.Functionality.ERASE);
                canvas.setOnMousePressed(erase::erasePressed);
                canvas.setOnMouseDragged(erase::eraseDragged);
                canvas.setOnMouseReleased(erase::eraseReleased);
            }
        });
        v.getChildren().add(btn);
        // return btn;
    }

    /**
     * set up combo box for have selections for color in top
     * you can choose any size for pen
     *
     * @param v a container to store this combo selection
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setupPenSize(HBox v) {
        Label label1 = new Label("Size for pen:");
        /*
         * TextField textField = new TextField("1.0"); textField.setPrefWidth(60); Button sub = new Button("Submit");
         * sub.setOnMouseClicked(new EventHandler<MouseEvent>() {
         *
         * @Override public void handle(MouseEvent me) { controller.setPenSize(new Double(textField.getText())); } });
         * HBox hb = new HBox();
         *
         * hb.getChildren().addAll(label1, textField, sub); hb.setSpacing(10); v.getChildren().add(hb);
         **/

        // create a choiceBox
        final ComboBox cb = new ComboBox();
        cb.getItems().addAll(1.0, 2.0, 3.0, 4.0, 5.0);
        // cb.SelectedIndex = 2;
        cb.getSelectionModel().selectFirst();
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                controller.setPenSize((double) cb.getValue());
            }

        });
        HBox hb = new HBox();

        hb.getChildren().addAll(label1, cb);
        hb.setAlignment(Pos.CENTER_RIGHT);
        v.getChildren().add(hb);
    }

    /**
     * when model shapes notify this, and this will find need to up date
     * different shapes have different sign
     */
    @Override
    public void update(Observable o, Object arg) {
        switch (controller.getCurrentFunctionality()) {
            case DRAW:
                NotePen.process(gc, (NotePath) o);
                break;
            case TYPE:
                NoteTextBoxGroup.process(group, (NoteTextBox) o);
                break;
            case ERASE:
                NoteErase.process(gc, (NoteCorrection) o);
                break;
            case IMAGE:
                NoteDrawImage.process(gc, (NoteImage) o);
                break;
            case VIDEO:
                NoteAddVideo.process((NoteVideo) o);
                break;
            case SEARCH:
                NoteSearch.process((NoteLink) o);
                break;
            case NULL:
                break;
        }
    }

    /**
     * start method for start application
     * the canvas has max size already
     * and it also set up the image as background
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("hyzl one note");
        primaryStage.setHeight(height);
        primaryStage.setWidth(width);
        primaryStage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (!max) {

                    primaryStage.setWidth(bounds.getWidth());
                    primaryStage.setHeight(bounds.getHeight());

                } else {

                    height = 700;
                    width = 700;
                    primaryStage.setWidth(width);
                    primaryStage.setHeight(height);

                }

                max = !max;
            }
        });

        // tileButtons.getChildren().add(setupPenButton());

        // tileButtons.getChildren().add(colorPicker);
        // color =colorPicker.getValue();
        BorderPane border = setUpCanvasGroup(primaryStage);

        Scene scene = new Scene(border);


        readSavedNote();

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event) -> setUpCloseAction());
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setResizable(true);
        primaryStage.show();
        setupSections();


    }

    /**
     * Set up the group of the BorderPane and the group, canvas, buttons
     *
     * @param primaryStage the stage which will show all content
     * @return the already set-up BorderPane
     */
    private BorderPane setUpCanvasGroup(Stage primaryStage) {
        HBox tileButtons = new HBox();
        // Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        // canvas.setCursor(Cursor.CROSSHAIR);
        setupPenButton(tileButtons, canvas);
        setupEraser(tileButtons, canvas);
        setupColorPicker(tileButtons);
        setupPenSize(tileButtons);
        setupTypeButton(tileButtons, canvas);
        setupImageButton(tileButtons);
        setupVideoButton(tileButtons);
        setupSearchButton(tileButtons);
        group.getChildren().add(canvas);

        BorderPane border = new BorderPane();
        border.setTop(tileButtons);
        border.setCenter(group);
        HBox sectionPage = new HBox();
        sectionPageGroup = new Group(sectionPage);
        sectionVBox = new VBox(new Label("section"));
        sectionVBox.setPrefWidth(150);
        pageVBox = new VBox(new Label("page"));
        pageVBox.setPrefWidth(150);
        sectionPage.getChildren().addAll(sectionVBox, pageVBox);
        border.setLeft(sectionPageGroup);
        sectionPage.setStyle("-fx-background-color: rgba(205,205,205,0.5);");

        try {
            FileInputStream input = new FileInputStream("abc.jpg");

            Image image = new Image(input);

            BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

            Background background = new Background(backgroundimage);

            border.setBackground(background);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return border;
    }

    /**
     * This method create the section VBox. It iterate the current page list and show all section to the users
     */
    private void setupSections() {
        if (controller.getAllSection().size() == 0) {
            controller.newSection();
            controller.setCurrentSection(controller.getAllSection().get(0).getID());
            setupSections();
            setUpPages(controller.getCurrentSection());
            return;
        }

        sectionVBox.getChildren().clear();
        for (NoteSection itSection : controller.getAllSection()) {

            if (!haveLoadPage) {
                haveLoadPage = true;
                setUpPages(itSection.getID());
            }
            HBox itSectionHBox = new HBox(new Label(itSection.getName()));
            setHBoxShape(itSectionHBox);

            itSectionHBox.setOnMouseClicked((e) -> {

                if (e.getButton() == MouseButton.SECONDARY) { // right clicked
                    VBox vbox = new VBox();
                    vbox.setStyle("-fx-background-color: rgba(134,161,168,1);");
                    HBox renameHB = new HBox(new Label("rename"));
                    addHBoxStyle(renameHB);
                    HBox deleteHB = new HBox(new Label("delete"));
                    addHBoxStyle(deleteHB);
                    HBox cancelHB = new HBox(new Label("cancel"));
                    addHBoxStyle(cancelHB);
                    vbox.getChildren().addAll(renameHB, deleteHB, cancelHB);
                    sectionPageGroup.getChildren().add(vbox);
                    renameHB.setOnMouseClicked((e1) -> {
                        itSectionHBox.getChildren().clear();
                        TextField renameTF = new TextField(itSection.getName());
                        Button setButton = new Button("ok");
                        itSectionHBox.getChildren().addAll(renameTF, setButton);
                        setButton.setOnAction((e2) -> {
                            controller.setSectionName(itSection.getID(), renameTF.getText());
                            setupSections();
                        });
                    });
                    deleteHB.setOnMouseClicked((e1) -> {
                        boolean nullPage = false;
                        if (itSection.getID() == controller.getCurrentSection()) {
                            nullPage = true;
                        }
                        controller.removeSection(itSection.getID());
                        if (nullPage) {
                            controller.setCurrentSection(-1);
                            ArrayList<NoteSection> nsArray = controller.getAllSection();
                            if (nsArray != null && nsArray.size() > 0) {
                                controller.setCurrentSection(nsArray.get(0).getID());
                            }
                        }
                        setupSections();
                        setUpPages(controller.getCurrentSection());
                    });

                    vbox.setOnMouseClicked((e1) -> {
                        sectionPageGroup.getChildren().remove(vbox);
                    });

                    vbox.setTranslateX(e.getSceneX() + itSectionHBox.getTranslateX());
                    vbox.setTranslateY(e.getSceneY() - 28);

                } else { // single click
                    if (itSection.getID() != controller.getCurrentSection()) {
                        canvas.setCursor(Cursor.DEFAULT);
                        controller.setCurrentSection(itSection.getID());
                        setupSections();
                        setUpPages(itSection.getID());
                    }
                }
            });
            sectionVBox.getChildren().add(itSectionHBox);

            if (itSection.getID() == controller.getCurrentSection()) {
                itSectionHBox.setStyle("-fx-background-color: rgba(85,132,145,0.5);");
            } else {
                addMouseEnterExitEvent(itSectionHBox);
            }

        }
        HBox newSection = new HBox(new Label("+ Section"));
        addMouseEnterExitEvent(newSection);
        newSection.setPadding(new Insets(10, 3, 10, 3));
        sectionVBox.getChildren().add(newSection);
        newSection.setOnMouseClicked((e) -> {
            controller.newSection();
            setupSections();
            //			setUpPages();
        });
    }

    /**
     * This method create the page VBox. It iterate the current page list and show all pages to the users
     *
     * @param thisNoteSectionID: The section that contains the pages
     */
    private void setUpPages(int thisNoteSectionID) {
        if (controller.getAllPageInSection(thisNoteSectionID).size() == 0) {
            controller.newPage(controller.getCurrentSection());
            controller.setCurrentPage(controller.getAllPageInSection(thisNoteSectionID).get(0).getId());
            setUpPages(controller.getCurrentSection());
            return;
        }
        pageVBox.getChildren().clear();

        for (NotePage itPage : controller.getAllPageInSection(thisNoteSectionID)) {
            HBox itPageHBox = new HBox(new Label(itPage.getName()));
            setHBoxShape(itPageHBox);

            itPageHBox.setOnMouseClicked((e) -> {
                if (e.getButton() == MouseButton.SECONDARY) { // right clicked
                    VBox vbox = new VBox();
                    vbox.setStyle("-fx-background-color: rgba(134,161,168,1);");
                    HBox renameHB = new HBox(new Label("rename"));
                    addHBoxStyle(renameHB);
                    HBox deleteHB = new HBox(new Label("delete"));
                    addHBoxStyle(deleteHB);
                    HBox cancelHB = new HBox(new Label("cancel"));
                    addHBoxStyle(cancelHB);

                    vbox.getChildren().addAll(renameHB, deleteHB, cancelHB);
                    sectionPageGroup.getChildren().add(vbox);
                    renameHB.setOnMouseClicked((e1) -> {
                        itPageHBox.getChildren().clear();
                        TextField renameTF = new TextField(itPage.getName());
                        Button setButton = new Button("ok");
                        itPageHBox.getChildren().addAll(renameTF, setButton);
                        setButton.setOnAction((e2) -> {
                            controller.setPageName(thisNoteSectionID, itPage.getId(), renameTF.getText());
                            setUpPages(thisNoteSectionID);
                        });
                    });
                    deleteHB.setOnMouseClicked((e1) -> {
                        boolean nullPage = false;
                        if (itPage.getId() == controller.getCurrentPage()) {
                            nullPage = true;
                        }
                        controller.removePageFromSection(thisNoteSectionID,
                                itPage.getId());
                        if (nullPage) {
                            controller.setCurrentPage(-1);
                            ArrayList<NotePage> npArray = controller.getAllPageInSection(thisNoteSectionID);
                            if (npArray != null && npArray.size() > 0) {
                                controller.setCurrentPage(npArray.get(0).getId());
                            }
                        }
                        setUpPages(thisNoteSectionID);
                    });

                    vbox.setOnMouseClicked((e1) -> sectionPageGroup.getChildren().remove(vbox));

                    vbox.setTranslateX(e.getSceneX() + itPageHBox.getTranslateX());
                    vbox.setTranslateY(e.getSceneY() - 28);

                } else { // single click
                    canvas.setCursor(Cursor.DEFAULT);
                    resetUI(thisNoteSectionID, itPage.getId());
                    setUpPages(thisNoteSectionID);
                }
            });
            pageVBox.getChildren().add(itPageHBox);

            if (itPage.getId() == controller.getCurrentPage()) {
                itPageHBox.setStyle("-fx-background-color: rgba(85,132,145,0.5);");
                resetUI(thisNoteSectionID, itPage.getId());
            } else {
                addMouseEnterExitEvent(itPageHBox);
            }
        }
        HBox newGroup = new HBox(new Label("+ Page"));
        addMouseEnterExitEvent(newGroup);
        newGroup.setPadding(new Insets(10, 3, 10, 3));
        pageVBox.getChildren().add(newGroup);
        newGroup.setOnMouseClicked((e) -> {
            if (thisNoteSectionID >= 0) {
                controller.newPage(thisNoteSectionID);
                if (controller.getCurrentPage() == -1) {
                    controller.setCurrentPage(0);
                }
                setUpPages(thisNoteSectionID);
            }
        });
    }

    /**
     * used to reload the user interface
     *
     * @param thisNoteSectionID: section for the UI
     * @param pageID:            pageID for the UI
     */
    private void resetUI(int thisNoteSectionID, int pageID) {
        controller.setCurrentPage(pageID);
        NoteAddVideo.stop();
        NoteSearch.stop();

        Node canv = group.getChildren().get(0);
        group.getChildren().clear();
        gc.clearRect(0, 0, 2000, 2000);
        group.getChildren().add(canv);
        controller.reDraw(thisNoteSectionID, pageID);

    }

    /**
     * Add the HBox style to the input HBox. The style includes color, mouse enter and exit effect
     *
     * @param inHBox: the input HBox whose style to be set
     */
    private void addHBoxStyle(HBox inHBox) {
        addMouseEnterExitEvent(inHBox, "rgba(85,132,145,1)", "rgba(134,161,168,1)");
        inHBox.setPadding(new Insets(1, 8, 1, 8));
    }

    /**
     * Add mouse enter and exit effect to the input node with default color sets
     *
     * @param node: the node to be added mouse enter and exit effect
     */
    private void addMouseEnterExitEvent(Node node) {
        addMouseEnterExitEvent(node, "rgba(85,132,145,0.5)", "rgba(0,0,0,0)");
    }

    /**
     * Add mouse enter and exit effect to the input node with default color sets
     *
     * @param node:   the node to be added mouse enter and exit effect
     * @param color1: the color for mouse enter
     * @param color2: the color for mouse exit
     */
    private void addMouseEnterExitEvent(Node node, String color1, String color2) {
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                node.setStyle("-fx-background-color: " + color1 + ";");
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                node.setStyle("-fx-background-color: " + color2 + ";");
            }
        });
    }

    /**
     * Set up the shape of the input HBox
     *
     * @param inputHBox a instance of HBox
     */
    private void setHBoxShape(HBox inputHBox) {
        inputHBox.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * set up close button
     * when you close the window it will save everything in
     * current all sections's pages
     */
    private void setUpCloseAction() {
        File file = new File("save_note.dat");
        if (file.exists()) {
            file.delete();
        }
        try {
            controller.saveModelTo(file);
        } catch (IOException e) {
            // todo make an alert
        }
    }

    /**
     * when you need to read file from previous saved one
     */
    private void readSavedNote() {
        File file = new File("save_note.dat");
        if (!file.exists()) {
            return;
        }
        try {
            controller.readModelFrom(file);
        } catch (IOException e) {
            // todo make an alert
        }
    }

    /**
     * set up color picker for pen
     *
     * @param tileButtons the container to have this button
     */
    private void setupColorPicker(HBox tileButtons) {
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setMaxWidth(30);
        colorPicker.setOnAction(t -> controller.setColor(convertColor(colorPicker.getValue())));
        tileButtons.getChildren().add(colorPicker);
    }

    /**
     * this return the Color object to String object
     *
     * @param c the Color Object
     * @return the String object
     */
    private String convertColor(Color c) {
        return String.format("#%02X%02X%02X", (int) (c.getRed() * 255), (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

}