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

package ikm.util;

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

interface Renderer {
	void drawSubstring(String str, int from, int length, int x, int y);
}

public class RichText {
	private int width;
	private int height = 0;
	private Vector lines = new Vector();
	private Vector fonts = new Vector();
	
	public RichText(int width) {
		this.width = width;
	}
	
	// Freeze if word longer than line
	public void addText(String text, Font font) {
		int p = 0;
		int dotWidth = font.charWidth('.');

		if (text.length() == 0) {
			lines.addElement("");
			fonts.addElement(font);
			height += font.getHeight();
			return;
		}
		
		while (p < text.length()) {
			int remainChars = width / dotWidth;
			if (remainChars + p >= text.length())
				remainChars = text.length() - p;

			int approxWidth = font.substringWidth(text, p, remainChars);

			for (;remainChars > 0; remainChars--) {
				char c = text.charAt(p + remainChars - 1);
				if ((c == ' ' || p + remainChars == text.length()) && approxWidth < width) {
					lines.addElement(text.substring(p, p + remainChars));
					fonts.addElement(font);
					height += font.getHeight();
					
					p += remainChars;
					break;
				}
				approxWidth -= font.charWidth(c);
			}
		}
	}
	
	public void addFormattedText(String str, Font normalFont, Font boldFont) {
		boolean tagOpen = false;
		int tagStart = 0;
		int blockStart = 0;

		String block = null;
		Font font = normalFont;
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (tagOpen) {
				if (c == '>') {
					String tag = str.substring(tagStart, i + 1);
					tagOpen = false;
					
					if (blockStart < tagStart || "<br/>".equals(tag)) {
						block = str.substring(blockStart, tagStart);
					}
					blockStart = i + 1;
					
					if ("<b>".equals(tag)) {
					} else if ("</b>".equals(tag)) {
						font = boldFont;
					}
				}
			} else {
				if (c == '\n') {
					if (blockStart < i) {
						block = str.substring(blockStart, i);
						font = normalFont;
					}
					blockStart = i + 1;
				} else if (c == '<') {
					tagOpen = true;
					tagStart = i;
				}
			}
			
			if (block != null) {
				addText(block, font);
				block = null;
				font = normalFont;
			}
		}
		
		if (blockStart < str.length()) {
			String text = str.substring(blockStart);
			addText(text, font);
		}
	}
	
	public void draw(Graphics g) {
		int y = 0;
		int i = 0;
		for (Enumeration en = lines.elements(); en.hasMoreElements();) {
			Font font = (Font) fonts.elementAt(i++);
			g.setFont(font);
			g.drawString((String) en.nextElement(), 0, y, Graphics.TOP | Graphics.LEFT);
			y += font.getHeight();
		}
	}

	public void draw(Graphics g, int fromY, int toY) {
		int y = 0;
		int i = 0;
		
		for (Enumeration en = lines.elements(); en.hasMoreElements();) {
			Font font = (Font) fonts.elementAt(i++);
			String str = (String) en.nextElement();
			int fontHeight = font.getHeight();
			if (y + fontHeight >= fromY && y <= toY) {
				g.setFont(font);
				g.drawString(str, 0, y, Graphics.TOP | Graphics.LEFT);
			}
			y += fontHeight;
		}
	}
	
	public int getLineCount() {
		return lines.size();
	}
	
	public int getHeight() {
		return height;
	}
}
