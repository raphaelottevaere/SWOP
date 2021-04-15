package com.swopteam07.tablr.UI;


import com.swopteam07.tablr.UI.event.TextFieldChangeEvent;
import com.swopteam07.tablr.UI.handlers.TextFieldChangeHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a label that can be edited by the user.
 **/
public class TextField extends Label {

    /**
     * Keeps track of the latest value before editing began.
     * Text will be replaced with this variable when escape is pressed.
     */
    private String oldValue;

    /**
     * true if you want a border around your textfield else false
     */
    private Boolean border = false;

    /**
     * List of the observers that are alerted when the contents of this Textfield changes.
     */
    private final List<TextFieldChangeHandler> textFieldChangeHandlers = new ArrayList<>();


    /**
     * Create a new textfield with the given dimensions and visibility state.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param text    The text to be displayed in the label.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The textfield is initialized as a label with the given dimensions and visibility state.
     * | super(x, y, height, width, visible)
     */
    public TextField(int x, int y, int height, int width, boolean visible, String text, Component parent) {
        super(x, y, height, width, visible, text, parent);
        oldValue = text;
    }

    /**
     * Create a new visible label with the given dimensions.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param text   The text to be displayed in the textfield.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect Initialize the textfield with the given parameters.
     * | this(x, y, height, width, true, text, parent)
     */
    public TextField(int x, int y, int height, int width, String text, Component parent) {
        this(x, y, height, width, true, text, parent);
    }

    /**
     * Create a new visible textField with the given text and a 0 surface area.
     *
     * @param text   The text to be displayed in the textfield.
     * @param parent The component that contains this component or null if this component has no parent.
     */
    public TextField(String text, Component parent) {
        this(0, 0, 0, 0, text, parent);
    }


    /**
     * Handle a user pressing a key while the textField is selected.
     *
     * @param id      The type of key event.
     * @param keyCode The number of key that was pressed.
     * @param keyChar The character that was typed.
     * @post If the user presses backspace, remove the last character in the value of this textfield.
     * @post If the user presses an alphanumeric character append that to the value of this textfield.
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar) {

        if (!isSelected())
            return;
        if (keyCode == 10) // Enter was pressed.
            unselect();

        if (keyCode == 8 && !getText().isEmpty())
            //fixed for tests
            if (getText().contains("|"))
                removeCharacter(2);
            else
                removeCharacter(1);
        else if (keyCode == 27)
            setText(oldValue);
        else if (id == 400 && !Character.isISOControl(keyChar)) addCharacter(keyChar);
    }

    /**
     * Deal with a user clicking somewhere in the component.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     */
    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {

        if (id != 500) return; // Only react to click event.

        super.handleClickEvent(id, x, y, clickCount);

        if (isSelected())
            addCharacter('|'); // Add the cursor.

        if (clickCount == 2)
            setText(getText()); // Remove the '|' when the textfield is double clicked.
    }

    /**
     * Add an observer that is alerted when the content of this cell changes.
     *
     * @param handler The handler to add.
     */
    public void addTextFieldChangeHandler(TextFieldChangeHandler handler) {
        textFieldChangeHandlers.add(handler);
    }

    /**
     * Remove an observer that is no longer alerted when the content of this cell changes.
     *
     * @param handler The handler to remove.
     */
    public void removeTextFieldChangeHandler(TextFieldChangeHandler handler) {
        textFieldChangeHandlers.remove(handler);
    }

    /**
     * Draw the label on a canvas.
     *
     * @param g The graphics object on which to paint.
     */
    @Override
    public void paint(Graphics g) {
        if (!isVisible()) return;
        if (border) {

            g.setColor(getColor("Standard"));
            g.drawRect(getDrawX(), getDrawY(), getWidth(), getHeight());
            g.setColor(getColor("Background"));
            g.fillRect(getDrawX() + 1, getDrawY() + 1, getWidth() - 1, getHeight() - 1);
        }

        g.setColor(getColor("Standard"));

        // Need to check if length is equal to 0 because if it is you'd get an index -1 which throws an exception.
        if (isSelected() && (getText().length() == 0 || getText().charAt(getText().length() - 1) != '|'))
            addCharacter('|');
        super.paint(g);
    }

    /**
     * Unselect this component and remove the cursor from the text.
     */
    @Override
    public void unselect() {
        super.unselect();

        if (allowsUnselect() && isValid()) {
            super.setText(getText()); // Use super so the component doesn't trigger change event.

            // User finished editing, replace the oldValue with the new value.
            oldValue = getText();
        }
    }

    /**
     * Get the text of this textfield.
     *
     * @return The text of this textfield.
     */
    public String getText() {
        String text = super.getText();
        return text == null ? null : text.replaceAll("\\|$", "");
    }

    /**
     * Remove a given number of characters from the text of this textfield.
     *
     * @param n The number of characters to remove.
     * @post The new text does not contain the last n characters.
     */
    private void removeCharacter(int n) {
        setText(getText().substring(0, getText().length() - n));
    }

    /**
     * Append a character to the text of this textfield.
     *
     * @param c The character to append.
     * @post The new text appended with the given character.
     * | new.getText() == getText() + c
     */
    private void addCharacter(char c) {
        setText(getText() + c);
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    /**
     * Set a new value for the text in this textfield. Then alert any observers of the change.
     *
     * @param text The new text for this label.
     */
    @Override
    public void setText(String text) {
        String old = getText();
        super.setText(text);

        if (support != null)
            support.firePropertyChange("text", null, text);
        if (textFieldChangeHandlers != null) {
            TextFieldChangeEvent evt = new TextFieldChangeEvent(old, getText());
            textFieldChangeHandlers.forEach(h -> h.handleEvent(evt));
        }
    }
}
