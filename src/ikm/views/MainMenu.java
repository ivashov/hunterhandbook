package ikm.views;

import java.util.Vector;

import ikm.Main;
import ikm.ViewManager;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public class MainMenu extends List implements CommandListener {
	private ViewManager viewManager;
	private Command back = new Command("Back", Command.BACK, 0);
	private Vector menuItems = new Vector(5);
	
	private void addMenuItem(String caption, Displayable disp) {
		append(caption, null);
		menuItems.addElement(disp);
	}
	
	public MainMenu(ViewManager viewManager) {
		super("Menu", List.IMPLICIT);
		
		this.viewManager = viewManager;
		Main main = Main.getInstance();
	    
	    addMenuItem("Животные", new ArticleList("Животные", viewManager, main.animalBase, new String[] 
	    		{"Описание", "Категория", "Ареал", "Период охоты", "Способы охоты"}, 3, 2));
	    addMenuItem("Статьи", new ArticleList("Статьи", viewManager, main.documentBase, new String[] 
	    		{""}, 2, -1));
	    addMenuItem("Правила охоты", new ArticleList("Правила охоты", viewManager, main.lawBase, new String[] 
	    		{""}, 2, -1));
	    addMenuItem("Оружие", new ArticleList("Оружие", viewManager, main.weaponBase, new String[] 
	    		{"Тип", "Калибр", "Дальность", "Описание"}, 2, -1));
	    addMenuItem("Магазины", new ShopList(viewManager, main.shopBase));
	    addMenuItem("О программе", new AboutView(viewManager));
	    setCommandListener(this);
	    addCommand(back);
	}
	
	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		} else if (c == List.SELECT_COMMAND) {
			int idx = getSelectedIndex();
			Displayable disp = null;
			if (idx >= 0 && idx < menuItems.size()) {
				disp = (Displayable) menuItems.elementAt(idx);
			}
			if (disp != null)
				viewManager.showView(disp);
		}
	}
}
