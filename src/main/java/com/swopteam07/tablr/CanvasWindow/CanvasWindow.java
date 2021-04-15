package com.swopteam07.tablr.CanvasWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A window for custom drawing.
 *
 * To use this class, create a subclass, say MyCanvasWindow, that overrides
 * methods {@link #paint(Graphics)}, {@link #handleMouseEvent(int,int,int,int)}, and {@link #handleKeyEvent(int,int,char)}, and then launch
 * it from your main method as follows:
 *
 * <pre>
 * public static void main(String[] args) {
 *     java.awt.EventQueue.invokeLater(() -> {
 *         new MyCanvasWindow("My Canvas Window").show();
 *     });
 * }
 * </pre>
 */

public class CanvasWindow {


    int width;
    int height;
    String title;
    Panel panel;
    private Frame frame;

    private String recordingPath;
    private CanvasWindowRecording recording;

    void updateFrameTitle() {
        System.out.println(frame);
        frame.setTitle(recording == null ? title : title + " - Recording: " + recording.items.size() + " items recorded");
    }

    public void setTitle(String title) {
        this.title = title;
        updateFrameTitle();
    }

    /**
     * Initializes a CanvasWindow object.
     *
     * @param title  Window title
     * @param width  The width of the window
     * @param height The height of the window.
     */
    protected CanvasWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }


    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */
    protected CanvasWindow(String title) {
        this(title, 600, 600);
    }

    public final void recordSession(String path) {
        recordingPath = path;
        recording = new CanvasWindowRecording();
    }

    /**
     * Call this method if the canvas is out of date and needs to be repainted.
     * This will cause method {@link #paint(Graphics)} to be called after the current call of method handleMouseEvent or handleKeyEvent finishes.
     */
    protected final void repaint() {
        if (panel != null)
            panel.repaint();
    }

    /**
     * Called to allow you to paint on the canvas.
     * <p>
     * You should not use the Graphics object after you return from this method.
     *
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    protected void paint(Graphics g) {
    }

    private void handleMouseEvent_(MouseEvent e) {
        System.out.println(e);
        if (recording != null)
            recording.items.add(new MouseEventItem(e.getID(), e.getX(), e.getY(), e.getClickCount()));
        handleMouseEvent(e.getID(), e.getX(), e.getY(), e.getClickCount());
    }

    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param e Details about the event
     */
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
    }

    private void handleKeyEvent_(KeyEvent e) {
        System.out.println(e);
        if (recording != null)
            recording.items.add(new KeyEventItem(e.getID(), e.getKeyCode(), e.getKeyChar()));
        handleKeyEvent(e.getID(), e.getKeyCode(), e.getKeyChar());
    }

    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param e
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar) {
    }

    BufferedImage captureImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics imageGraphics = image.getGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, width, height);
        imageGraphics.setColor(Color.BLACK);
        CanvasWindow.this.paint(imageGraphics);
        return image;
    }

    class Panel extends JPanel {

        {
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.WHITE);
            setFocusable(true);

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    handleMouseEvent_(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    handleMouseEvent_(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    handleMouseEvent_(e);
                }

            });

            addMouseMotionListener(new MouseAdapter() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    handleMouseEvent_(e);
                }

            });

            addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {
                    handleKeyEvent_(e);
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    handleKeyEvent_(e);
                }

            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            System.out.println("Painting...");
            super.paintComponent(g);

            if (recording != null) {
                BufferedImage image = captureImage();
                g.drawImage(image, 0, 0, null);
                recording.items.add(new PaintItem(image));
                updateFrameTitle();
            } else {
                CanvasWindow.this.paint(g);
            }
        }

    }

    private class Frame extends JFrame {
        Frame(String title) {
            super(title);

            addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    if (recording != null)
                        try {
                            System.out.println(new File(".").getCanonicalPath());
                            recording.save(recordingPath);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    System.exit(0);
                }

            });
            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    public final void show() {
        if (!EventQueue.isDispatchThread())
            throw new RuntimeException("You must call this method from the AWT dipatch thread");
        panel = new Panel();
        frame = new Frame(title);
        frame.setVisible(true);
    }

    public static void replayRecording(String path, CanvasWindow window) {
        try {
            new CanvasWindowRecording(path).replay(window);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

