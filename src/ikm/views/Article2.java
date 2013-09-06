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
