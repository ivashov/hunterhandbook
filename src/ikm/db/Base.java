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
