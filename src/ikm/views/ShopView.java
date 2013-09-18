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

import ikm.Main;
import ikm.ViewManager;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;

public class ShopView extends Form implements CommandListener, ItemCommandListener {
	private ViewManager viewManager;
	private String[] line;
	private String[] fieldNames;
	private Command exitCommand = new Command("Back", Command.BACK, 1);
	private Command mapCommand = new Command("Показать на карте", Command.ITEM, 1);
	private StringItem showMapItem;
	private Main main = Main.getInstance();
	private int idx;
	
	public ShopView(String caption, ViewManager viewManager, String[] line,
			String[] fieldNames, int idx) {
		super(caption);
		this.viewManager = viewManager;
		this.line = line;
		this.fieldNames = fieldNames;
		this.idx = idx;
		
		showMapItem = new StringItem(null, "Показать на карте", StringItem.BUTTON);
		showMapItem.setDefaultCommand(mapCommand);
		showMapItem.setItemCommandListener(this);
		
		append(new StringItem(fieldNames[1], line[2]));
		append(showMapItem);
		
		addCommand(exitCommand);
		//addCommand(mapCommand);
		setCommandListener(this);
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == exitCommand) {
			viewManager.goBack();
		} else if (c == mapCommand) {
			MapView mapView = main.getMapView();
			mapView.centerOn(idx);
			main.showMap();
		}
	}
	
	public void commandAction(Command c, Item item) {
		if (showMapItem == item) {
			MapView mapView = main.getMapView();
			mapView.centerOn(idx);
	    	viewManager.goBack();
	    	viewManager.goBack();
			main.showMap();
		}
	}
}
