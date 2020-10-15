package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import Model.NoteModel;
import Model.NoteShapes.NoteCorrection;
import Model.NoteShapes.NoteImage;
import Model.NoteShapes.NoteLink;
import Model.NoteShapes.NotePath;
import Model.NoteShapes.NoteTextBox;
import Model.NoteShapes.NoteVideo;
import javafx.scene.image.Image;

/**
 * This class tests all shapes in Model.NoteShape
 */
class ShapesTest {

	/**
	 * This class tests the functionality of the model class NoteTextBox
	 */
	@Test
	void testNoteTextBoxClass() {
		NoteTextBox textBox = new NoteTextBox();
		assertEquals(textBox.getThisFunctionality(), NoteModel.Functionality.TYPE);
		textBox.setXChanged(10.2);
		assertEquals(10.2, textBox.getXChanged());
		textBox.setYChanged(10.2);
		assertEquals(10.2, textBox.getYChanged());
		textBox.setFinalY(10.2);
		assertEquals(10.2, textBox.getFinalY());
		textBox.setFinalX(10.2);
		assertEquals(10.2, textBox.getFinalX());
		textBox.setOriginalY(10.2);
		assertEquals(10.2, textBox.getOriginalY());
		textBox.setOriginalX(10.2);
		assertEquals(10.2, textBox.getOriginalX());
		textBox.setFontSize(10.2);
		assertEquals(10.2, textBox.getFontSize());
		assertEquals(400.0, textBox.getWidth());
		assertEquals(300.0, textBox.getHeight());
		textBox.setColor("rgba(0,0,0,1)");
		assertEquals("rgba(0,0,0,1)", textBox.getColor());
		textBox.setContent("rgba(0,0,0,1)");
		assertEquals("rgba(0,0,0,1)", textBox.getContent());
	}

	/**
	 * test not correction
	 */
	@Test
	void testNoteCorrectionClass() {
		NoteCorrection correction =new NoteCorrection(30.0,30.0);
		assertEquals(correction.height,30.0);
		assertEquals(correction.width,30.0);
		assertEquals(correction.getThisFunctionality(), NoteModel.Functionality.ERASE);
		assertEquals(correction.getSize(),0);
		correction.add(100.0, 100.0);
		assertEquals(correction.getX(0),100.0);
		assertEquals(correction.getY(0),100.0);
		correction.add(200.0, 200.0);
		assertEquals(correction.getX(1),200.0);
		assertEquals(correction.getY(1),200.0);
		
		
	}

	/**
	 * test Note Image
	 */
	@Test
	void testNoteImageClass() {
		NoteImage imageObject = new NoteImage(100,100,125,130, new File("abc.jpg"));
		imageObject.setfile(new File("abc.jpg"));
		try {
		assertNotEquals(imageObject.getImage(), new Image(new FileInputStream(new File("def.jpg"))));
		} catch (FileNotFoundException ex) {}
		assertEquals(imageObject.getX(),100);
		assertEquals(imageObject.getY(),100);
		assertEquals(imageObject.getW(),125);
		assertEquals(imageObject.getH(),130);
		assertEquals(imageObject.getThisFunctionality(), NoteModel.Functionality.IMAGE);
		
	}

	/**
	 * test Note Link
	 */
	@Test
	void testNoteLinkClass() {
		NoteLink link =new NoteLink(100.0,105.0,30.0,30.0,"https://www.youtube.com/");
		assertEquals(link.getUrl(),"https://www.youtube.com/");
		assertEquals(30.0,link.getW());
		assertEquals(30.0,link.getH());
		assertEquals(link.getThisFunctionality(), NoteModel.Functionality.SEARCH);
		assertEquals(link.getFinalX(),100.0);
		assertEquals(link.getFinalY(),105.0);
		assertEquals(link.getX(),100.0);
		assertEquals(link.getY(),105.0);
		
		link.setX(101.0);
		assertEquals(101.0,link.getX());
		link.setY(101.0);
		assertEquals(101.0,link.getY());
		link.setFinalX(200.0);
		assertEquals(200.0,link.getFinalX());
		link.setFinalY(200.0);
		assertEquals(200.0,link.getFinalY());
		link.setfile("https://www.google.com/");
		assertEquals(link.getUrl(),"https://www.google.com/");
		link.setOriginalX(101.0);
		assertEquals(link.getOriginalX(),101.0);
		link.setOriginalY(101.0);
		assertEquals(link.getOriginalY(),101.0);
		link.setXChanged(202.0);
		assertEquals(link.getXChanged(),202.0);
		link.setYChanged(202.0);
		assertEquals(link.getYChanged(),202.0);
		
		
	}

	/**
	 * test Note path
	 */
	@Test
	void testNotePathClass() {
		NotePath path = new NotePath("#000000",2.0);
		assertEquals(path.getThisFunctionality(), NoteModel.Functionality.DRAW);
		assertEquals(path.getLength(),0);
		path.add(1.0, 2.0, "lineto");
		assertEquals(path.getColor(),"#000000");
		assertEquals(path.getLength(),1);
		assertEquals(path.getWidth(),2.0);
		assertEquals(path.getYAt(0),2.0);
		assertEquals(path.getXAt(0),1.0);
		assertEquals(path.getOperationAt(0),"lineto");
		path.add(2.0, 3.0, "moveto");
		assertEquals(path.getLength(),2);
		assertEquals(path.getWidth(),2.0);
		assertEquals(path.getYAt(1),3.0);
		assertEquals(path.getXAt(1),2.0);
		assertEquals(path.getOperationAt(1),"moveto");
		
	}

	/**
	 * test Note video
	 */
	@Test
	void testNoteVideoClass() {
		NoteVideo video = new NoteVideo(100,110,150,120, "2.mp4");
		assertEquals(video.getUrl(),"2.mp4");
		assertEquals(video.getW(),150);
		assertEquals(120,video.getH());
		assertEquals(video.getThisFunctionality(), NoteModel.Functionality.VIDEO);
		assertEquals(video.getFinalX(),100);
		assertEquals(video.getFinalY(),110);
		assertEquals(video.getX(),100);
		assertEquals(video.getY(),110);
		
		video.setX(101.0);
		assertEquals(101.0,video.getX());
		video.setY(101.0);
		assertEquals(101.0,video.getY());
		video.setFinalX(200.0);
		assertEquals(200.0,video.getFinalX());
		video.setFinalY(200.0);
		assertEquals(200.0,video.getFinalY());
		video.setfile("3.mp4");
		assertEquals(video.getUrl(),"3.mp4");
		video.setOriginalX(101.0);
		assertEquals(video.getOriginalX(),101.0);
		video.setOriginalY(101.0);
		assertEquals(video.getOriginalY(),101.0);
		video.setXChanged(202.0);
		assertEquals(video.getXChanged(),202.0);
		video.setYChanged(202.0);
		assertEquals(video.getYChanged(),202.0);
	}
}
