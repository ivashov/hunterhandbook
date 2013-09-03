package ikm;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import ikm.data.Animal;
import ikm.db.Base;
import ikm.views.ArticleList;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import com.nokia.mid.ui.orientation.Orientation;
import com.nokia.mid.ui.orientation.OrientationListener;


/**
 * This is the main MIDlet of the template. It handles MIDlet life cycle
 * management (what to do when MIDlet starts, pauses or closes). There should be
 * no reason to bloat this class with massive amounts of code; it is better to
 * try to clearly separate responsibilities to own classes.
 */
public class Main
    extends MIDlet 
    implements OrientationListener {

    // Class members

    private TabManager tabManager;
    private Base animalBase;
    private Base documentBase;
    private Base weaponBase;
    public static Display display;
    private void loadBase() {
    	try {
    		animalBase = new Base(new InputStreamReader(Main.class.getResourceAsStream("/animal.csv"), "utf-8"));
    		animalBase.parseAll();
    		documentBase = new Base(new InputStreamReader(Main.class.getResourceAsStream("/document.csv"), "utf-8"));
    		documentBase.parseAll();
    		weaponBase = new Base(new InputStreamReader(Main.class.getResourceAsStream("/weapon.csv"), "utf-8"));
    		weaponBase.parseAll();
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // Life cycle management

    /**
     * What to do when MIDlet is started
     * @see javax.microedition.midlet.MIDlet#startApp()
     */
    public void startApp() {
    	display = Display.getDisplay(this);
    	loadBase();
        tabManager = new TabManager(this);
        
        Displayable view1 = new ArticleList("Животные", tabManager, animalBase, new String[] 
        		{"Название", "Описание", "Категория", "Ареал", "Период охоты", "Способы охоты"});
        Displayable view2 = new ArticleList("Документы", tabManager, documentBase, new String[] 
        		{"Название", ""});
        Displayable view3 = new ArticleList("Оружие", tabManager, weaponBase, new String[] 
        		{"Название", "Тип", "Калибр", "Дальность", "Описание"});

        Orientation.addOrientationListener(this);

        tabManager.addTab(view1, "/categorybar_comments.png",
            "Custom command label");
        tabManager.addTab(view2, "/categorybar_contacts.png");
        tabManager.addTab(view3, "/categorybar_image.png");
        tabManager.showTab(0); // 0 == first tab
    }

    /**
     * What to do when MIDlet is paused. This can happen e.g. when MIDlet is on
     * the foreground and an incoming phone call is received.
     * @see javax.microedition.midlet.MIDlet#pauseApp()
     */
    public void pauseApp() {
    }

    /**
     * What to do when MIDlet is destroyed
     * @param unconditional If false, MIDlet can ask to not to be destroyed by
     *        throwing MIDletStateChangeException. If true, MIDlet will be
     *        destroyed regardless of how this function terminates.
     * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
     */
    public void destroyApp(boolean unconditional) {
    }

    /**
     * Changes display orientation between landscape and portrait.
     * Note: ORIENTATION_LANDSCAPE_180 and ORIENTATION_PORTRAIT_180 are not
     * supported.
     * @param newDisplayOrientation Tells the new device orientation.
     * @see com.nokia.mid.ui.orientation.OrientationListener#displayOrientationChanged(int)
     */
    public void displayOrientationChanged(int newDisplayOrientation) {
        if (newDisplayOrientation == Orientation.ORIENTATION_LANDSCAPE
            || newDisplayOrientation == Orientation.ORIENTATION_PORTRAIT)
        {
            Orientation.setAppOrientation(newDisplayOrientation);
        }
    }
}
