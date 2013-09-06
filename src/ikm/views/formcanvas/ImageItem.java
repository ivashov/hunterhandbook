package ikm.views.formcanvas;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import ikm.views.formcanvas.FormCanvas.FormCanvasItem;

public class ImageItem extends FormCanvasItem {
	private Image image;
	private int width;

	public ImageItem(Image image) {
		this.image = image;
	}
	
	public void paint(Graphics g, int fromY, int toY) {
		g.drawImage(image, width / 2, 0, Graphics.HCENTER | Graphics.TOP);
	}

	public int getHeight(int width) {
		this.width = width;
		return image.getHeight();
	}
}
