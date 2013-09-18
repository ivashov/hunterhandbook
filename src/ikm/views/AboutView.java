package ikm.views;

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
		add("Версия: 0.0.1");
		add("E-Mail: ivashov@cs.karelia.ru");
		add("В приложении использованы материалы с commons.wikimedia.org");

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
