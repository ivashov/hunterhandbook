/* 
 * This file is part of Hunterhandbook.
 * Copyright (C) 2013, Ivashov Kirill
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
