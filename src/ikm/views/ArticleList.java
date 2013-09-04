package ikm.views;

import ikm.ViewManager;
import ikm.data.Animal;
import ikm.db.Base;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public class ArticleList extends List implements CommandListener {
	private Command back = new Command("Back", Command.BACK, 1);
	private ViewManager viewManager;
	private Base base;
	private String[] fieldNames;
	private int firstField;
	private int imageField;
	
	public ArticleList(String name, ViewManager viewManager, Base base, String[] fieldNames, int firstField, int imageField) {
		super(name, List.IMPLICIT);
		this.viewManager = viewManager;
		this.base = base;
		this.fieldNames = fieldNames;
		this.firstField = firstField;
		this.imageField = imageField;
		
		addCommand(back);
		setCommandListener(this);
		
		for (int i = 0; i < base.getSize(); i++) {
			insert(i, base.get(i, 1), null);
		}
	} 

	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		} else if (c == List.SELECT_COMMAND) {
			int idx = getSelectedIndex();
			if (idx >= 0) {
				String[] line = base.get(idx);
				Article detailView = new Article(line[1], viewManager, line, fieldNames, firstField, imageField);
				viewManager.showView(detailView);
			}
		}
	}
}
