package ikm.views;

import ikm.Main;
import ikm.util.Maths;
import ikm.views.TextCanvas.TextItem;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

import com.nokia.mid.ui.frameanimator.FrameAnimator;
import com.nokia.mid.ui.frameanimator.FrameAnimatorListener;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.nokia.mid.ui.gestures.GestureListener;
import com.nokia.mid.ui.gestures.GestureRegistrationManager;

class TestItem extends TextItem {
	static int c = 0;
	int n = c++;
	//`Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	Font font = Font.getFont(Font.FONT_STATIC_TEXT);
	public int getHeight(int width) {
		return 6 * font.getHeight();
	}
	
	public void paint(Graphics g, int fromY, int toY) {
		g.setFont(font);
		int h = font.getHeight();
		for (int i = 0; i < 6; i++) {
			if ((i + 1) * h >= fromY && (i + 0) * h <= toY)
				g.drawString("qwe asd zxc asd " + i + " " + n, 0, i * h, Graphics.TOP | Graphics.LEFT);
		}
		g.drawLine(0, 6 * h, 320, 6 * h);
	}
}

public class TextCanvas extends GameCanvas implements GestureListener, FrameAnimatorListener {
	static abstract class TextItem {
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
	
	public TextCanvas() {
		super(true);
		setTitle("Hello world");
		this.width = getWidth();
		this.height = getHeight();
		g = getGraphics();
		this.cwidth = g.getClipWidth();
		this.cheight = g.getClipHeight();
		
		giz = new GestureInteractiveZone(GESTURES);
		frame = new FrameAnimator();
		GestureRegistrationManager.register(this, giz);
		GestureRegistrationManager.setListener(this, this);
		
		frame.register(0, 0, (short) 0, (short) 0, this);

		for (int i = 0; i < 10; i++) {
			items.addElement(new TestItem());
			items.addElement(new TestItem());
			items.addElement(new TestItem());
		}
		initialize();
		redraw();
	}
	
	public void initialize() {
		for (Enumeration en = items.elements(); en.hasMoreElements();) {
			TextItem item = (TextItem) en.nextElement();
			item.canvasPos = totalHeight;
			totalHeight += (item.height = item.getHeight(width - 2 * MARGIN));
		}
	}
	
	private void redraw() {
		g.translate(-g.getTranslateX(), -g.getTranslateY());
		g.setColor(backColor);
		g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
		
		g.translate(MARGIN, -canvasTranslation);
		g.setColor(textColor);
		for (Enumeration en = items.elements(); en.hasMoreElements();) {
			TextItem item = (TextItem) en.nextElement();
			if (item.canvasPos < canvasTranslation + height && item.canvasPos + item.height > canvasTranslation) {
				item.paint(g, canvasTranslation - item.canvasPos, canvasTranslation - item.canvasPos + height);
			}
			g.translate(0, item.height);
		}
		flushGraphics();
	}

	public void animate(FrameAnimator animator, int x, int y, short delta,
			short deltaX, short deltaY, boolean lastFrame) {
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
		if (totalHeight <= cheight) {
			return;
		}
		switch (gestureEvent.getType()) {
		case GestureInteractiveZone.GESTURE_DRAG:
			dragY += gestureEvent.getDragDistanceY();
			// TODO: what if item size less that screen?
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
	}
}
