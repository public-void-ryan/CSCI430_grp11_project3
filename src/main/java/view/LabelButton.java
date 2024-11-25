package view;

import controller.LabelCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabelButton extends JButton implements ActionListener {
    protected final JPanel drawingPanel;
    protected final View view;
    private final UndoManager undoManager;
    private final Model model;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private LabelCommand labelCommand;

    public LabelButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Label");
        this.model = model;
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;

        mouseHandler = new MouseHandler();
        keyHandler = new KeyHandler();

        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.requestFocusInWindow();
    }

    private void resetState() {
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
        drawingPanel.removeKeyListener(keyHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (labelCommand != null) {
                undoManager.endCommand();
            }

            labelCommand = new LabelCommand(model, undoManager, event.getPoint());
            undoManager.beginCommand(labelCommand);

            drawingPanel.addKeyListener(keyHandler);
            drawingPanel.requestFocusInWindow();
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent event) {
            char character = event.getKeyChar();
            if (character >= 32 && character <= 126 && labelCommand != null) {
                labelCommand.addCharacter(character);
            }
        }

        @Override
        public void keyPressed(KeyEvent event) {
            if (labelCommand != null) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    undoManager.endCommand();
                    resetState();
                } else if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    labelCommand.removeCharacter();
                }
            }
        }
    }
}
