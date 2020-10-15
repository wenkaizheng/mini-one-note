package Model.NoteShapes;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import Model.NoteModel;

/**
 * This is the class for creating a video.
 * @author Shu Lin
 *
 */
public class NoteVideo extends NoteShape {
    private double x, y, w, h, XChanged, YChanged;
	private double originalX, originalY;
	private double finalX = 0, finalY = 0;
    private final NoteModel.Functionality sign = NoteModel.Functionality.VIDEO;
    private String url;

    /**
     * 
     * @param x  position x
     * @param y  position y
     * @param w  the width
     * @param h  the height
     * @param url  the address from user
     */
    public NoteVideo(double x, double y, double w, double h, String url) {
    	this.x = x;
		this.y = y;
		this.finalX = x;
		this.finalY = y;
		this.w = w;
		this.h = h;
		this.url = url;
    }

    /**
     * notify view to redraw Video 
     */
    @Override
    public void reDraw() {
        // TODO Auto-generated method stub
        setChanged();
        notifyObservers(this);
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
	 * get functionality from now
	 */
    public NoteModel.Functionality getThisFunctionality() {
        return this.sign;
    }
    
    /**
     * set url if user enter if again
     * @param url the address from user
     */
    public void setfile(String url) {
        this.url = url;
    }

    /**
     * 
     * @return get x
     */
    public double getX() {
        return x;
    }
    
    /**
     * set x 
     * @param x position x
     */
    public void setX(double x) {
    	this.x = x;
    }
    
    /**
     *  set y
     * @param y position y
     */
    public void setY(double y) {
    	this.y = y;
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
     * @return address from user
     */
    public String getUrl() { return url; }
}