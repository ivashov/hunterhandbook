package ikm.util;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

interface Renderer {
	void drawSubstring(String str, int from, int length, int x, int y);
}

public class RichText implements Renderer {
	private int width;
	private int x = 0;
	private int y = 0;
	private Graphics g;
	private Renderer r;
	private int lines = 0;
	
	static Renderer stub = new Renderer() {
		public void drawSubstring(String str, int from, int length, int x, int y) {
		}
	};

	public void drawSubstring(String str, int from, int length, int x, int y) {
		g.drawSubstring(str, from, length, x, y, Graphics.TOP | Graphics.LEFT);
	}
	
	public RichText(int width, Graphics g) {
		this.width = width;
		this.g = g;
		
		if (g == null)
			r = stub;
		else
			r = this;
	}
	
	public void newLine(Font font) {
		y += font.getHeight();
		x = 0;
		lines++;
	}
	
	/* void dryRunMode(boolean dryRun) {
		if (dryRun)
			r = stub;
		else
			r = this;
	}*/

	// Freeze if word longer than line
	public void drawString(String str, Font font) {
		int p = 0;
		int dotWidth = font.charWidth('.');
		//int stop = 10;
		while (p < str.length()) {
			int remainChars = (width - x) / dotWidth;
			if (remainChars + p >= str.length())
				remainChars = str.length() - p;

			int approxWidth = font.substringWidth(str, p, remainChars);

			for (;remainChars > 0; remainChars--) {
				char c = str.charAt(p + remainChars - 1);
				if ((c == ' ' || p + remainChars == str.length()) && approxWidth < width - x) {
					r.drawSubstring(str, p, remainChars, x, y);
					x += approxWidth;
					p += remainChars;
					break;
				}
				approxWidth -= font.charWidth(c);
			}
			
			if (remainChars == 0 || x >= width) {
				y += font.getHeight();
				x = 0;
				lines++;
			}
		}
	}
	
	public int getLineCount() {
		return lines;
	}
}
