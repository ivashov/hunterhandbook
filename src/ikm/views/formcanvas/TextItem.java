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