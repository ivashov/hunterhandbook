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

import ikm.Main;
import ikm.util.RichText;
import ikm.views.formcanvas.FormCanvas.FormCanvasItem;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class TextItem extends FormCanvasItem {	
	private Font baseFont = Font.getFont(Font.FONT_STATIC_TEXT);
	private Font textFont = Font.getFont(baseFont.getFace(), baseFont.getStyle(), Font.SIZE_SMALL);
	private Font boldFont = Font.getFont(baseFont.getFace(), Font.STYLE_BOLD, Font.SIZE_SMALL);
	private Font captionFont = Font.getDefaultFont();
	
	private String text;
	private RichText rich;
	private int width, generatedWidth, height;
	private String caption;
		
	public TextItem(String caption, String text) {
		this.text = text;
		this.caption = caption;
	}
	
	public int getHeight(int width) {
		this.width = width;
		generateText();
		return height = rich.getHeight();
	}
	
	private void generateText() {
		if (width != generatedWidth) {
			rich = new RichText(width);
			rich.addText(caption, captionFont);
			rich.addFormattedText(text, textFont, boldFont);
			generatedWidth = width;
		}
	}
	
	public void paint(Graphics g, int fromY, int toY) {
		generateText();
		g.setColor(Main.display.getColor(Display.COLOR_FOREGROUND));
		
		rich.draw(g, fromY, toY);
		
		g.drawLine(0, height, width, height);
	}
}