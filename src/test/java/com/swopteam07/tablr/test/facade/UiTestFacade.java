package com.swopteam07.tablr.test.facade;

import com.swopteam07.tablr.controller.Controller;

import java.awt.event.KeyEvent;
import java.util.stream.IntStream;

/**
 * Interface for easier test in the UI
 *
 * @author rapha
 */
public interface UiTestFacade {

    static void pressEscape() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_ESCAPE, '\027');
    }

    static void pressBackspace() {
        Controller.getInstance().handleKeyEvent(400, KeyEvent.VK_BACK_SPACE, '\0');
    }

    static void putString(String s) {
        for (int i = 0; i < s.toCharArray().length; i++) {
            char c = s.toCharArray()[i];
            int code = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c);
            Controller.getInstance().getActiveView().handleKeyEvent(401, code, c);
            Controller.getInstance().getActiveView().handleKeyEvent(400, code, c);
        }
    }

    static void pressEnter() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_ENTER, '\000');
    }

    static void pressDelete() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_DELETE, '\000');
    }

    static void pressN() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_N, 'n');
    }

    static void pressD() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_D, 'd');
    }

    static void pressZ() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_Z, 'z');
    }

    static void pressShift() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_SHIFT, '\000');
    }

    static void pressControl() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_CONTROL, '\000');
    }

    static void pressTController() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_T, 't');
    }


    static void pressControlController() {
        Controller.getInstance().handleKeyEvent(401, KeyEvent.VK_CONTROL, '\000');
    }

    static void clickBeneathView(int y, int clickCount) {
        Controller.getInstance().getActiveView().clickedBeneathView(y, clickCount);
    }

    static void undo() {
        UiTestFacade.pressControl();
        UiTestFacade.pressZ();
    }

    static void redo() {
        UiTestFacade.pressControl();
        UiTestFacade.pressShift();
        UiTestFacade.pressZ();
    }
}
