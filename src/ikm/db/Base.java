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

package ikm.db;

import ikm.util.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.util.Vector;

public class Base {
	private Reader reader;
	private char delimiter = '|';
	
	private Vector strings = new Vector();
	
	public Base(Reader reader) {
		this.reader = reader;
	}
	
	public void parseAll() throws IOException {
		String line;
		
		while ((line = StringUtils.readLine2(reader)) != null) {
			strings.addElement(StringUtils.split(line, delimiter));
		}
	}
	
	public String get(int line, int field) {
		return ((String[]) strings.elementAt(line))[field];
	}

	public String[] get(int line) {
		return (String[]) strings.elementAt(line);
	}
	
	public int getSize() {
		return strings.size();
	}
}
