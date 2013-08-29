package ikm;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import com.nokia.mid.ui.CategoryBar;
import com.nokia.mid.ui.ElementListener;
import com.nokia.mid.ui.IconCommand;

/**
 * Tab navigation and drawing handler. Tab navigation is handled through a
 * CategoryBar object. Implements ElementListener interface to be able to detect
 * selection events from the CategoryBar as well as ViewManager interface to be
 * able to handle view switching request from individual views.
 */
public class TabManager
    implements ElementListener, ViewManager {

    // Class members

    protected final MIDlet midlet;
    protected CategoryBar categoryBar;
    protected boolean redrawNeeded;
    protected Vector iconCommands = new Vector();
    protected Vector tabStacks = new Vector();

    /**
     * Class constructor. Just saves the application MIDlet as a member, so that
     * actual view changes can be directed to MIDlet's Display.
     * @param midlet
     */
    public TabManager(MIDlet midlet) {
        this.midlet = midlet;
        this.redrawNeeded = false;
    }

    // Tab handling

    /**
     * Add a tab to the tab bar with a custom icon. View title is added as the
     * command label for the tab.
     * @param view Displayable view which is shown as the first view of the tab
     * @param iconName Filename of the command icon relative to the "res" folder
     */
    public void addTab(Displayable view, String iconName) {
        addTab(view, iconName, view.getTitle());
    }

    /**
     * Add a tab to the tab bar with a custom icon and command label.
     * @param view Displayable view which is shown as the first view of the tab
     * @param iconName Filename of the command icon relative to the "res" folder
     * @param label Command label for the tab
     */
    public void addTab(Displayable view, String iconName, String label) {
        Image icon = loadImageFrom(iconName);
        iconCommands.addElement(new IconCommand(label, icon, null,
            Command.SCREEN, 1));
        Stack tabStack = new Stack();
        tabStack.push(view);
        tabStacks.addElement(tabStack);
        redrawNeeded = true;
    }

    /**
     * Show tab of the given index.
     * @param index Index of the tab to be shown
     */
    public void showTab(int index) {
        drawTabBar();
        
        if (index >= 0 && index < tabStacks.size()) {
            categoryBar.setSelectedIndex(index);
            Stack tabStack = (Stack) (tabStacks.elementAt(index));
            Displayable view = (Displayable) (tabStack.peek());
            Display.getDisplay(midlet).setCurrent(view);
        }
        else {
            // Invalid index -> do nothing
        }
    }

    /**
     * Draw the actual tab bar to the screen after all tabs are added to the
     * view. There is no reason to call this method multiple times, because it
     * basically just initializes a new CategoryBar instance, and most probably
     * it doesn't change during the MIDlet life cycle.
     * @return Instance of the created category bar
     */
    protected CategoryBar drawTabBar() {
        if (redrawNeeded) {
            // Create an array out of Vector iconCommands contents
            IconCommand[] iconCommandsArray =
            		new IconCommand[iconCommands.size()];
            
            for (int i = 0; i < iconCommands.size(); i++) {
                iconCommandsArray[i] = (IconCommand) iconCommands.elementAt(i);
            }
            
            // Initialize a new CategoryBar instance and overwrite the possible
            // old one
            categoryBar = new CategoryBar(iconCommandsArray, true);
            
            // Set it visible and change the selected tab color based on the
            // MIDlet Display
            categoryBar.setVisibility(true);
            categoryBar.setElementListener(this);
            Display display = Display.getDisplay(midlet);
            categoryBar.setHighlightColour(display
                .getColor(Display.COLOR_HIGHLIGHTED_BACKGROUND));
            
            redrawNeeded = false;
        }
        
        return categoryBar;
    }

    /**
     * Handle all CategoryBar selection events.
     * @param categoryBar CategoryBar from which the event was fired (in this
     *        case it will always be the local this.categoryBar instance)
     * @param selectedIndex Index of the selected tab
     */
    public void notifyElementSelected(CategoryBar categoryBar, int selectedIndex) {
        if (selectedIndex == ElementListener.BACK) {
            goBack();
        }
        else {
            showTab(selectedIndex);
        }
    }

    // View stack handling by implementing ViewManager interface

    /**
     * @see ViewManager#showView(javax.microedition.lcdui.Displayable)
     */
    public void showView(Displayable view) {
        int currentTabIndex = categoryBar.getSelectedIndex();
        Stack tabStack = (Stack) (tabStacks.elementAt(currentTabIndex));
        tabStack.push(view);
        Display.getDisplay(midlet).setCurrent(view);
        toggleTabBarVisibilityBasedOn(tabStack);
    }

    /**
     * @see ViewManager#goBack()
     */
    public void goBack() {
        int currentTabIndex = categoryBar.getSelectedIndex();
        Stack tabStack = (Stack) (tabStacks.elementAt(currentTabIndex));
        tabStack.pop();
        
        if (tabStack.empty()) {
            midlet.notifyDestroyed();
        }
        else {
            Displayable view = (Displayable) (tabStack.peek());
            Display.getDisplay(midlet).setCurrent(view);
            toggleTabBarVisibilityBasedOn(tabStack);
        }
    }

    /**
     * Toggle tab bar visibility based on the number of items in a single tab
     * stack. Tab bar is not shown on sub views of a tab.
     * @param currentTabStack
     */
    private void toggleTabBarVisibilityBasedOn(Stack currentTabStack) {
        // Prevent accidental tab bar flickering by checking the current
        // visibility first
        boolean visibilityShouldBe = (currentTabStack.size() <= 1);
        
        if (categoryBar.getVisibility() != visibilityShouldBe) {
            categoryBar.setVisibility(visibilityShouldBe);
        }
    }

    // Utility functions

    /**
     * Load Image from a local resource
     * @param file Filename of the Image to be loaded, relative to the "/res"
     *        folder of the MIDlet.
     * @return The loaded image
     */
    protected static Image loadImageFrom(String file) {
        Image image = null;
        
        try {
            image = Image.createImage(file);
        }
        catch (NullPointerException npe) {
            System.out.println("No image file name specified");
        }
        catch (IOException ioe) {
            System.out.println("Image not found or invalid: " + file);
        }
        
        return image;
    }
}
