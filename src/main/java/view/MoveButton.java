package view;

import controller.MoveCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MoveButton extends JButton implements ActionListener {
    private final JPanel drawingPanel;
    private final View view;
    private final UndoManager undoManager;
    private final Model model;
    private final MouseHandler mouseHandler;
    private MoveCommand moveCommand;
    private Point startPoint;

    public MoveButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Move");
        this.model = model;
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;

        mouseHandler = new MouseHandler();

        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.HAND_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        drawingPanel.requestFocusInWindow();
    }

    private void resetState() {
        moveCommand = null;
        startPoint = null;
        undoManager.endCommand();
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
        drawingPanel.removeMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            startPoint = event.getPoint();
            moveCommand = new MoveCommand(model, undoManager);
            undoManager.beginCommand(moveCommand);
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            if (startPoint != null) {
                Point currentPoint = event.getPoint();
                int dx = currentPoint.x - startPoint.x;
                int dy = currentPoint.y - startPoint.y;

                if (moveCommand != null) {
                    moveCommand.applyTranslation(dx, dy);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            resetState();
        }

        @Override
        public void mouseExited(MouseEvent event) {
            resetState();
        }
    }
}
