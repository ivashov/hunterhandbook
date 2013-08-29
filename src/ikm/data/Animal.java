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
