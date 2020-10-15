package Model.NoteShapes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import Model.NoteModel;
import javafx.scene.image.Image;

/**
 * This is the class for creating an image object.
 * @author Shu Lin
 *
 */
public class NoteImage extends NoteShape {
	private double x, y, w, h;
	private final NoteModel.Functionality sign = NoteModel.Functionality.IMAGE;
	private File file;
	
	/**
     * 
     * @param x  position x
     * @param y  position y
     * @param w  the width
     * @param h  the height
     * @param file  the file of image
     */
	public NoteImage(double x, double y, double w, double h, File file) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.file = file;
		setChanged();
		notifyObservers();
	}
	
	/**
     * notify view to redraw image 
     */
	@Override
	public void reDraw() {
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * get functionality from now
	 */
	@Override
	public NoteModel.Functionality getThisFunctionality() {
		return this.sign;
	}
	
	/**
     * set file if user enter a new file
     * @param f the address from user
     */
	public void setfile(File f) {
		this.file =f;
	}
	
	/**
     * 
     * @return get x
     */
	public double getX() {
		return x;
	}
	
	/**
     * 
     * @return position y
     */
	public double getY() {
		return y;
	}
	
	/**
     * 
     * @return width
     */
	public double getW() {
		return w;
	}
	
	/**
     * 
     * @return Height
     */
	public double getH() {
		return h;
	}
	
	/**
     * 
     * @return An Image object.
     */
	public Image getImage() {
		Image image = null;
		try {
			FileInputStream stream = new FileInputStream(file);
			image = new Image(stream);
		} catch (IOException ex) {
			
		}
		return image;
	}
}