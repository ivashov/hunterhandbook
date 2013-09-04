package ikm;

import java.io.IOException;
import java.util.Hashtable;

import javax.microedition.lcdui.Image;

public class ImageManager {
	public static Hashtable loadedImages = new Hashtable();
	
	public static Image getImage(String filename) {
		Image img = (Image) loadedImages.get(filename);
		if (img == null) {
			String path = "/images/" + filename;
			try {
				img = Image.createImage(path);
				loadedImages.put(filename, img);
			} catch (IOException e) {
				img = null;
				//e.printStackTrace();
			}
		}
		return img;
	}
}
