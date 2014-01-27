package ikm.views;

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

import ikm.ViewManager;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class AboutView extends Form implements CommandListener {
	private Command back = new Command("Back", Command.BACK, 0);
	private ViewManager viewManager;

	public AboutView(ViewManager viewManager) {
		super("О программе");
		this.viewManager = viewManager;
		add("Приложение реализовано в лаборатории ПетрГУ Fruct");
		add("Разработчик: Kirill Ivashov");
		add("Версия: 0.0.2");
		add("E-Mail: ivashov@cs.karelia.ru");
		add("В приложении использованы материалы с commons.wikimedia.org");

		//add("This application is a part of grant KA322 of Karelia ENPI CBC programme, which is co-funded by the European Union, the Russian Federation and the Republic of Finland");
		add("Приложение является частью гранта KA322 Karelia ENPI CBC programme, который финансируется Европейским Союзом, Российской Федерацией и Финляндией");
		setCommandListener(this);
		addCommand(back);
	}

	private void add(String str) {
		append(new StringItem(null, str));
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		}
	}
}
