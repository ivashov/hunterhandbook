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
import ikm.db.Base;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;

public class ShopList extends List implements CommandListener {
	private Command back = new Command("Back", Command.BACK, 1);
	private ViewManager viewManager;
	private Base base;
	private String[] fieldNames = new String[] {
		"Название",
		"Адрес"
	};
	
	public ShopList(ViewManager viewManager, Base base) {
		super("Магазины", List.IMPLICIT);
		this.base = base;
		this.viewManager = viewManager;
		
		addCommand(back);
		setCommandListener(this);
		
		for (int i = 0; i < base.getSize(); i++) {
			append(base.get(i, 1), null);
		}
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		} else if (c == List.SELECT_COMMAND) {
			int idx = getSelectedIndex();
			if (idx >= 0) {
				String[] line = base.get(idx);
				ShopView detailView = new ShopView(line[1], viewManager, line, fieldNames, idx);
				viewManager.showView(detailView);
			}
		}
	}
}
