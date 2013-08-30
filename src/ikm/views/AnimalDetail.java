package ikm.views;

import ikm.Main;
import ikm.ViewManager;
import ikm.data.Animal;
import ikm.util.RichText;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;

class TestItem extends CustomItem {
	private int width;
	private String text;
	protected TestItem(String label, int width, String text) {
		super(label);
		this.width = width;
		this.text = text;
	}

	protected int getMinContentHeight() {
		return 10;
	}

	protected int getMinContentWidth() {
		return width;
	}

	protected int getPrefContentHeight(int width) {
		RichText rich = new RichText(width, null);
		
		Font font = Font.getFont(Font.FONT_STATIC_TEXT);
		font = Font.getFont(font.getFace(), font.getStyle(), Font.SIZE_SMALL);
		rich.drawString(text, font);
		
		return (rich.getLineCount() + 1) * font.getHeight();
	}

	protected int getPrefContentWidth(int height) {
		return width;
	}

	protected void paint(Graphics g, int w, int h) {
		g.setColor(Main.display.getColor(Display.COLOR_FOREGROUND));

		Font font = Font.getFont(Font.FONT_STATIC_TEXT);
		font = Font.getFont(font.getFace(), font.getStyle(), Font.SIZE_SMALL);
		Font boldFont = Font.getFont(font.getFace(), Font.STYLE_BOLD, Font.SIZE_SMALL);
		RichText rich = new RichText(w, g);
		g.setFont(font);
		rich.drawString(text, font);
	}
	
	
	protected void sizeChanged(int w, int h) {
	}
}

public class AnimalDetail extends Form implements CommandListener {	
	private ViewManager viewManager;
	private Command exitCommand = new Command("Back", Command.BACK, 1);
	private String[] line;
	private String[] fieldNames;
	
	public AnimalDetail(ViewManager viewManager, String[] line,
			String[] fieldNames) {
		super("Animal");
		
		this.viewManager = viewManager;
		this.line = line;
		this.fieldNames = fieldNames;
		addCommand(exitCommand);
		setCommandListener(this);

		fillDetails();
	}

	private void addText(String caption, String data) {
		//StringItem item = new StringItem(caption, data);
		//append(item);
		TestItem t = new TestItem(caption, getWidth(), data);
		append(t);
	}
	
	private void fillDetails() {
		for (int i = 0; i < fieldNames.length; i++) {
			addText(fieldNames[i], line[i + 1]);
		}
	}

	public void commandAction(Command c, Displayable d) {
		if (c == exitCommand) {
			viewManager.goBack();
		}
	}
}
