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

package ikm.views.formcanvas;

import ikm.Main;
import ikm.util.Maths;
import ikm.views.formcanvas.FormCanvas.FormCanvasItem;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

import com.nokia.mid.ui.frameanimator.FrameAnimator;
import com.nokia.mid.ui.frameanimator.FrameAnimatorListener;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.nokia.mid.ui.gestures.GestureListener;
import com.nokia.mid.ui.gestures.GestureRegistrationManager;

public class FormCanvas extends GameCanvas implements GestureListener, FrameAnimatorListener {
	static abstract class FormCanvasItem {
		public int canvasPos;
		public int height;
		public abstract void paint(Graphics g, int fromY, int toY);
		public abstract int getHeight(int width);
	}
	
	public static int MARGIN = 3;
	private static int GESTURES = GestureInteractiveZone.GESTURE_FLICK 
			| GestureInteractiveZone.GESTURE_DRAG 
			| GestureInteractiveZone.GESTURE_TAP; 
	private int width, height, cwidth, cheight;
	private int totalHeight = 0;
	private int canvasTranslation = 0;
	private int dragY = 0;
	private Graphics g;
	
	private int textColor = Main.display.getColor(Display.COLOR_FOREGROUND);
	private int backColor = Main.display.getColor(Display.COLOR_BACKGROUND);
	
	private GestureInteractiveZone giz;
	private FrameAnimator frame;
	
	private Vector items = new Vector();
	
	private boolean registered = false;
	
	public FormCanvas(String caption) {
		super(true);
		setTitle(caption);
		this.width = getWidth();
		this.height = getHeight();
		g = getGraphics();
		this.cwidth = g.getClipWidth();
		this.cheight = g.getClipHeight();
		
		giz = new GestureInteractiveZone(GESTURES);
		frame = new FrameAnimator();
		GestureRegistrationManager.register(this, giz);

		redraw();
	}
	
	public void addItem(FormCanvasItem item) {
		items.addElement(item);
		item.canvasPos = totalHeight;
		totalHeight += (item.height = item.getHeight(width - 2 * MARGIN) + 6);
	}
	
	public void redraw() {
		g.translate(-g.getTranslateX(), -g.getTranslateY());
		g.setColor(backColor);
		g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
		
		g.translate(MARGIN, -canvasTranslation);
		g.setColor(textColor);
		for (Enumeration en = items.elements(); en.hasMoreElements();) {
			FormCanvasItem item = (FormCanvasItem) en.nextElement();

			if (item.canvasPos < canvasTranslation + height && item.canvasPos + item.height > canvasTranslation) {
				item.paint(g, canvasTranslation - item.canvasPos, canvasTranslation - item.canvasPos + height);
			}
			
			g.translate(0, item.height);
		}
		flushGraphics();
	}

	public void animate(FrameAnimator animator, int x, int y, short delta,
			short deltaX, short deltaY, boolean lastFrame) {
		if (!registered)
			return;

		canvasTranslation = y;
		if (y < 0) {
			frame.stop();
			canvasTranslation = 0;
		} else if (y > totalHeight - cheight) {
			y = totalHeight - cheight;
			frame.stop();
		}
		dragY = 0;
		redraw();
	}
	
	public void gestureAction(Object container,
			GestureInteractiveZone gestureInteractiveZone,
			GestureEvent gestureEvent) {
		if (totalHeight <= cheight || !registered)
			return;

		switch (gestureEvent.getType()) {
		case GestureInteractiveZone.GESTURE_DRAG:
			dragY += gestureEvent.getDragDistanceY();
			int newTranslation = Maths.clamp(canvasTranslation - dragY, 0, totalHeight - cheight);
			frame.drag(0, newTranslation);
			break;
			
		case GestureInteractiveZone.GESTURE_FLICK:
			frame.kineticScroll(-gestureEvent.getFlickSpeedY(), FrameAnimator.FRAME_ANIMATOR_VERTICAL, FrameAnimator.FRAME_ANIMATOR_FRICTION_LOW, 0);
			break;
			
		case GestureInteractiveZone.GESTURE_TAP:
			frame.stop();
			break;
		}
	}
	
	protected void sizeChanged(int w, int h) {
		width = cwidth = w;
		height = cheight = h;
		redraw();
	}

	protected void showNotify() {
		if (!registered) {
			frame.register(0, 0, (short) 0, (short) 0, this);
			GestureRegistrationManager.setListener(this, this);
			registered = true;
		}
	}

	protected void hideNotify() {
		if (registered) {
			registered = false;
			frame.unregister();
		}
	}
}
