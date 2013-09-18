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

import ikm.ViewManager;
import ikm.db.Base;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

import com.nokia.maps.common.GeoCoordinate;
import com.nokia.maps.map.MapCanvas;
import com.nokia.maps.map.MapDisplayState;
import com.nokia.maps.map.MapStandardMarker;

public class MapView extends MapCanvas implements CommandListener {
	private Command back = new Command("Back", Command.BACK, 0);
	private ViewManager viewManager;
	private Base base;
	private MapDisplayState[] states;
	
	public MapView(Display display, ViewManager viewManager, Base base) {
		super(display);
		this.viewManager = viewManager;
		this.base = base;
		generateMarkers();
		setFullScreenMode(false);
		
		
		setCommandListener(this);
		addCommand(back);
	}

	public void onMapContentComplete() {
	}

	public void onMapUpdateError(String description, Throwable detail,
			boolean critical) {
		Alert alert = new Alert("Error", description, null, AlertType.ERROR);
		alert.setTimeout(1000);
		viewManager.showView(alert);
	}

	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		}
	}
	
	private void generateMarkers() {
		states = new MapDisplayState[base.getSize()];
		for (int i = 0; i < base.getSize(); i++) {
			String lat = base.get(i, 3);
			String lon = base.get(i, 4);
			GeoCoordinate coord = new GeoCoordinate(Double.parseDouble(lat), Double.parseDouble(lon), 0);
			states[i] = new MapDisplayState(coord, 15);
			MapStandardMarker marker = getMapFactory().createStandardMarker(coord, 8, "", MapStandardMarker.BALLOON);
			getMapDisplay().addMapObject(marker);
		}
	}
	
	public void centerOn(int idx) {
		getMapDisplay().setState(states[idx]);
	}
}
