package ikm.views;

import ikm.ImageManager;
import ikm.Main;
import ikm.ViewManager;
import ikm.util.RichText;
import ikm.views.formcanvas.FormCanvas;
import ikm.views.formcanvas.ImageItem;
import ikm.views.formcanvas.TextItem;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;

public class Article extends FormCanvas implements CommandListener {	
	private ViewManager viewManager;
	private Command exitCommand = new Command("Back", Command.BACK, 1);
	private String[] line;
	private String[] fieldNames;
	private int firstField;
	private int imageField;
	
	public Article(String caption, ViewManager viewManager, String[] line,
			String[] fieldNames, int firstField, int imageField) {
		super(caption);
		
		this.imageField = imageField;
		this.firstField = firstField;
		this.viewManager = viewManager;
		this.line = line;
		this.fieldNames = fieldNames;
		addCommand(exitCommand);
		setCommandListener(this);
		
		fillDetails();
		redraw();
	}

	private void addText(String caption, String data) {
		TextItem textItem = new TextItem(/*caption, getWidth(), */caption, data);
		addItem(textItem);
	}
	
	private void fillDetails() {		
		if (imageField >= 0) {
			String imgFile = line[imageField];
			if (imgFile != null) {
				Image img = ImageManager.getImage(imgFile);
				if (img != null) {
					ImageItem imgItem = new ImageItem(img);
					addItem(imgItem);
				}
			}
		}
		
		for (int i = 0; i < fieldNames.length; i++) {
			addText(fieldNames[i], line[i + firstField]);
		}
	}

	public void commandAction(Command c, Displayable d) {
		if (c == exitCommand) {
			viewManager.goBack();
		}
	}
}
