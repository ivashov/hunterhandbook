package ikm.views;

import ikm.Main;
import ikm.ViewManager;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.List;

public class MainMenu extends List implements CommandListener {
	private ViewManager viewManager;
	private Displayable view1;
	private Displayable view2;
	private Displayable view3;
	private Displayable view4;
	private Command back = new Command("Back", Command.BACK, 0);

	public MainMenu(ViewManager viewManager) {
		super("Menu", List.IMPLICIT);
		
		this.viewManager = viewManager;
		Main main = Main.getInstance();
	    view1 = new ArticleList("��������", viewManager, main.animalBase, new String[] 
	    		{"��������", "��������", "���������", "�����", "������ �����", "������� �����"});
	    view2 = new ArticleList("���������", viewManager, main.documentBase, new String[] 
	    		{"��������", ""});
	    view3 = new ArticleList("������", viewManager, main.weaponBase, new String[] 
	    		{"��������", "���", "������", "���������", "��������"});
	    view4 = new ShopList(viewManager, main.shopBase);
	    
	    append("��������", null);
	    append("���������", null);
	    append("������", null);
	    append("��������", null);
	    setCommandListener(this);
	    addCommand(back);
	    
	}
	public void commandAction(Command c, Displayable d) {
		if (c == back) {
			viewManager.goBack();
		} else if (c == List.SELECT_COMMAND) {
			int idx = getSelectedIndex();
			Displayable disp = null;
			switch (idx) {
			case 0:
				disp = view1;
				break;
			case 1:
				disp = view2;
				break;
			case 2:
				disp = view3;
				break;
			case 3:
				disp = view4;
				break;
			}
			if (disp != null)
				viewManager.showView(disp);
		}
	}


}
