package Model.NoteShapes;

import Model.NoteModel;

/**
 * This class is used to create a new textBox with desired properties
 * 
 * @author Guoxin Huang
 *
 */
public class NoteTextBox extends NoteShape {
	private static final long serialVersionUID = 2L;

	private double originalX, originalY;
	private double XChanged, YChanged;
	private double finalX = 0, finalY = 0;
	private String content = "";
	private double fontSize = 12;
	private double width = 400, height = 300;
	private String color = "black";
	private final NoteModel.Functionality sign = NoteModel.Functionality.TYPE;

	/**
	 * constructor
	 */
	public NoteTextBox() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.NoteShapes.NoteShape#reDraw()
	 */
	@Override
	public void reDraw() {
		setChanged();
		notifyObservers(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Model.NoteShapes.NoteShape#getThisFunctionality()
	 */
	@Override
	public NoteModel.Functionality getThisFunctionality() {
		return this.sign;
	}

	/**
	 * get the YChanged
	 * 
	 * @return YChanged
	 */
	public double getYChanged() {
		return YChanged;
	}

	/**
	 * get the XChanged
	 * 
	 * @return XChanged
	 */
	public double getXChanged() {
		return XChanged;
	}

	/**
	 * set the finalY
	 * 
	 * @param d: value to be set for finalY
	 */
	public void setFinalY(double d) {
		finalY = d;
	}

	/**
	 * set the finalX
	 * 
	 * @param d: value to be set for finalX
	 */
	public void setFinalX(double d) {
		finalX = d;
	}

	/**
	 * get the originalY
	 * 
	 * @return originalY
	 */
	public double getOriginalY() {
		return originalY;
	}

	/**
	 * get the originalX
	 * 
	 * @return originalX
	 */
	public double getOriginalX() {
		return originalX;
	}

	/**
	 * set the YChanged
	 * 
	 * @param translateY: value to be set for YChanged
	 */
	public void setYChanged(double translateY) {
		YChanged = translateY;
	}

	/**
	 * set the XChanged
	 * 
	 * @param translateX: value to be set for XChanged
	 */
	public void setXChanged(double translateX) {
		XChanged = translateX;
	}

	/**
	 * set the originalY
	 * 
	 * @param sceneY: value to be set for originalY
	 */
	public void setOriginalY(double sceneY) {
		originalY = sceneY;
	}

	/**
	 * set the originalX
	 * 
	 * @param sceneX: value to be set for originalX
	 */
	public void setOriginalX(double sceneX) {
		originalX = sceneX;
	}

	/**
	 * get the finalY
	 * 
	 * @return finalY
	 */
	public double getFinalY() {
		return finalY;
	}

	/**
	 * get the finalX
	 * 
	 * @return finalX
	 */
	public double getFinalX() {
		return finalX;
	}

	/**
	 * set the color
	 * 
	 * @param convertColor: value to be set for color
	 */
	public void setColor(String convertColor) {
		color = convertColor;
	}

	/**
	 * get the color
	 * 
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * set the fontSize
	 * 
	 * @param parseDouble: value to be set for fontSize
	 */
	public void setFontSize(double parseDouble) {
		fontSize = parseDouble;
	}

	/**
	 * get the fontSize
	 * 
	 * @return fontSize
	 */
	public double getFontSize() {
		return fontSize;
	}

	/**
	 * get the content
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * get the width
	 * 
	 * @return width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * get the height
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * set the content
	 * 
	 * @param text: value to be set for content
	 */
	public void setContent(String text) {
		content = text;
	}

}
