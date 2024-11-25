package view;

import controller.TriangleCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TriangleButton extends JButton implements ActionListener {
    private final JPanel drawingPanel;
    private final View view;
    private final UndoManager undoManager;
    private final Model model;
    private final MouseHandler mouseHandler;
    private TriangleCommand triangleCommand;

    public TriangleButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Triangle");
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
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        drawingPanel.requestFocusInWindow();
    }

    private void resetState() {
        triangleCommand = null;
        undoManager.endCommand();
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
        drawingPanel.removeMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (triangleCommand == null || triangleCommand.getPointCount() == 0) {
                triangleCommand = new TriangleCommand(model, undoManager);
                triangleCommand.setTrianglePoint(event.getPoint(), true);
                undoManager.beginCommand(triangleCommand);
            } else if (triangleCommand.getPointCount() == 1) {
                triangleCommand.setTrianglePoint(event.getPoint(), true);
            } else if (triangleCommand.getPointCount() == 2) {
                triangleCommand.setTrianglePoint(event.getPoint(), true);
                resetState();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            if (triangleCommand != null) {
                triangleCommand.setTrianglePoint(event.getPoint(), false);
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            resetState();
        }
    }
}
