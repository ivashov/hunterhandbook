/* 
 * This file is part of Hunterhandbook.
 * Copyright (C) 2013, Ivashov Kirill
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package ikm;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import ikm.db.Base;
import ikm.views.MainMenu;
import ikm.views.MapView;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

/**
 * This is the main MIDlet of the template. It handles MIDlet life cycle
 * management (what to do when MIDlet starts, pauses or closes). There should be
 * no reason to bloat this class with massive amounts of code; it is better to
 * try to clearly separate responsibilities to own classes.
 */
public class Main
    extends MIDlet {

    // Class members

	public static final String HERE_ID = "aISaf4OOdEYcioK0pEJs";
	public static final String HERE_TOKEN = "sLOZJSIl6hfCid2LZ_BAug";
    private TabManager tabManager;
    public Base animalBase;
    public Base documentBase;
    public Base weaponBase;
    public Base shopBase;
    public Base lawBase;
    public static Display display;
    
    private static Main instance;
    {
    	instance = this;
    }
    public static Main getInstance() {
    	return instance;
    }
    
    private volatile MapView mapView;
    public MapView getMapView() {
    	if (mapView == null) {
    		synchronized (this) {
				if (mapView == null) {
					mapView = new MapView(Display.getDisplay(this), tabManager, shopBase);
				}
			}
    	}
    	
    	return mapView;
    }
    /*
    {
    	new Thread() {
    		public void run() {
    			while (true) {
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    				System.gc();
    				System.out.println("" + (Runtime.getRuntime().freeMemory() / 1024) + "/" + (Runtime.getRuntime().totalMemory() /  1024));
    			}
    		}
    	}.start();
    }
    */
    private void loadBase() {
    	try {
    		InputStreamReader reader;
    		
    		animalBase = new Base(reader = new InputStreamReader(Main.class.getResourceAsStream("/animal.csv"), "utf-8"));
    		animalBase.parseAll();
    		reader.close();
    		documentBase = new Base(reader = new InputStreamReader(Main.class.getResourceAsStream("/document.csv"), "utf-8"));
    		documentBase.parseAll();
    		reader.close();
    		weaponBase = new Base(reader = new InputStreamReader(Main.class.getResourceAsStream("/weapon.csv"), "utf-8"));
    		weaponBase.parseAll();
    		reader.close();
    		shopBase = new Base(reader = new InputStreamReader(Main.class.getResourceAsStream("/shops.csv"), "utf-8"));
    		shopBase.parseAll();
    		reader.close();
    		lawBase = new Base(reader = new InputStreamReader(Main.class.getResourceAsStream("/law.csv"), "utf-8"));
    		lawBase.parseAll();
    		reader.close();
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
    	com.nokia.maps.common.ApplicationContext.getInstance().setAppID(HERE_ID);
    	com.nokia.maps.common.ApplicationContext.getInstance().setToken(HERE_TOKEN);
    	com.nokia.maps.common.ApplicationContext.getInstance().setDefaultLanguage("RUS");
    	display = Display.getDisplay(this);
    	loadBase();
        tabManager = new TabManager(this);

        Displayable view1 = new MainMenu(tabManager);
        Displayable view2 = getMapView();
       // Displayable view3 = new Article2();
        /*
        MapCanvas mapp = new MapCanvas(display) {
			public void onMapUpdateError(String arg0, Throwable arg1, boolean arg2) {
			}

			public void onMapContentComplete() {
			}
		};
        mapp.getMapDisplay().setState(new MapDisplayState(
        		new GeoCoordinate(61.78, 34.35, 0), 10));
		mapp.setTitle("HERE maps");
		MapStandardMarker mar = mapp.getMapFactory().createStandardMarker(
				new GeoCoordinate(61.78, 34.35, 0));
		mapp.getMapDisplay().addMapObject(mar);
		*/        

        tabManager.addTab(view1, "/categorybar_list_m_light.png", "Menu");
        tabManager.addTab(view2, "/1378301421_monotone_earth_world_asia_transparent.png", "Map");
        //tabManager.addTab(view3, "/categorybar_list_m_light.png", "Menu");
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

    
    public void showMap() {
    	tabManager.showTab(1);
    }
}
