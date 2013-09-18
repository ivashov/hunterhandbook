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

import ikm.views.formcanvas.FormCanvas;
import ikm.views.formcanvas.TextItem;

public class Article2 extends FormCanvas {
	private String gen() {
		StringBuffer buf = new StringBuffer();
		
		for (int i = 0; i < 30; i++) {
			buf.append("test<br/><b>tsettesttest</b>" + i + "<br/>");
		}
		return buf.toString();
	}
	
	public Article2() {
		super("Test");
		addItem(new TextItem("Test", gen()));
		addItem(new TextItem("Test", gen()));
		addItem(new TextItem("Test", gen()));
		redraw();
	}
}
