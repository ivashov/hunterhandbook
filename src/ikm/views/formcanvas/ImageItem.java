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
