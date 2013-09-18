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

import java.util.Vector;

import ikm.Main;
import ikm.ViewManager;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public class MainMenu extends List implements CommandListener {
	private ViewManager viewManager;
	private Command back = new Command("Back", Command.BACK, 0);
	private Vector menuItems = new Vector(5);
	
	private void addMenuItem(String caption, Displayable disp) {
		append(caption, null);
		menuItems.addElement(disp);
	}
	
	public MainMenu(ViewManager viewManager) {
		super("Меню", List.IMPLICIT);
		
		this.viewManager = viewManager;
		Main main = Main.getInstance();
	    
	    addMenuItem("Животные", new ArticleList("Животные", viewManager, main.animalBase, new String[] 
	    		{"Описание", "Категория", "Ареал", "Период охоты", "Способы охоты"}, 3, 2));
	    addMenuItem("Статьи", new ArticleList("Статьи", viewManager, main.documentBase, new String[] 
	    		{""}, 2, -1));
	    addMenuItem("Правила охоты", new ArticleList("Правила охоты", viewManager, main.lawBase, new String[] 
	    		{""}, 2, -1));
	    addMenuItem("Оружие", new ArticleList("Оружие", viewManager, main.weaponBase, new String[] 
	    		{"Тип", "Калибр", "Дальность", "Описание"}, 2, -1));
	    addMenuItem("Магазины", new ShopList(viewManager, main.shopBase));
	    addMenuItem("О программе", new AboutView(viewManager));
	    setCommandListener(this);
	    addCommand(back);
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		} else if (c == List.SELECT_COMMAND) {
			int idx = getSelectedIndex();
			Displayable disp = null;
			if (idx >= 0 && idx < menuItems.size()) {
				disp = (Displayable) menuItems.elementAt(idx);
			}
			if (disp != null)
				viewManager.showView(disp);
		}
	}
}
