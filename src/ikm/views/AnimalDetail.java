package ikm.views;

import ikm.ViewManager;
import ikm.data.Animal;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class AnimalDetail extends Form implements CommandListener {
	private ViewManager viewManager;
	private Command exitCommand = new Command("Back", Command.BACK, 1);
	private String[] line;
	private String[] fieldNames;
	
	public AnimalDetail(ViewManager viewManager, String[] line,
			String[] fieldNames) {
		super("Animal");
		
		this.viewManager = viewManager;
		this.line = line;
		this.fieldNames = fieldNames;
		addCommand(exitCommand);
		setCommandListener(this);
	
		fillDetails();
	}

	private void addText(String caption, String data) {
		StringItem item = new StringItem(caption, data);
		append(item);
	}
	
	private void fillDetails() {
		for (int i = 0; i < fieldNames.length; i++) {
			addText(fieldNames[i], line[i + 1]);
		}
	}

	public void commandAction(Command c, Displayable d) {
		if (c == exitCommand) {
			viewManager.goBack();
		}
	}
}
