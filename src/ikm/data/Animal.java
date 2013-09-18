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

package ikm.data;

import ikm.db.Base;

public class Animal {
	public int id;
	public String name;
	public String desc;
	public String category;
	public String area;
	public String huntPeriod;
	public String huntMethods;

	public Animal(Base base, int line) {
		String[] arr = base.get(line);
		id = Integer.parseInt(arr[0]);
		name = arr[1];
		desc = arr[2];
		category = arr[3];
		area = arr[4];
		huntPeriod = arr[5];
		huntMethods = arr[6];
	}
	
	public static Animal[] generateArray(Base base) {
		Animal[] ret = new Animal[base.getSize()];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Animal(base, i);
		}
		
		return ret;
	}
}
