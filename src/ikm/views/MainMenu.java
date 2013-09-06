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
	    
	    addMenuItem("��������", new ArticleList("��������", viewManager, main.animalBase, new String[] 
	    		{"��������", "���������", "�����", "������ �����", "������� �����"}, 3, 2));
	    addMenuItem("������", new ArticleList("������", viewManager, main.documentBase, new String[] 
	    		{""}, 2, -1));
	    addMenuItem("������� �����", new ArticleList("������� �����", viewManager, main.lawBase, new String[] 
	    		{""}, 2, -1));
	    addMenuItem("������", new ArticleList("������", viewManager, main.weaponBase, new String[] 
	    		{"���", "������", "���������", "��������"}, 2, -1));
	    addMenuItem("��������", new ShopList(viewManager, main.shopBase));
	    addMenuItem("� ���������", new AboutView(viewManager));
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
