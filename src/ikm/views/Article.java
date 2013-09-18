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
