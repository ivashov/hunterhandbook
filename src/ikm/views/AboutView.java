package ikm.views;

import ikm.ViewManager;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

public class AboutView extends Form implements CommandListener {
	private Command back = new Command("Back", Command.BACK, 0);
	private ViewManager viewManager;

	public AboutView(ViewManager viewManager) {
		super("О программе");
		this.viewManager = viewManager;
		append("Приложение реализовано в лаборатории ПетрГУ");
		append("Разработчик: Kirill Ivashov");
		append("Версия: 0.0.1");
		append("E-Mail: ivashov@cs.karelia.ru");
		append("В приложении использованы материалы с commons.wikimedia.org");

		setCommandListener(this);
		addCommand(back);
	}

	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		}
	}
}
