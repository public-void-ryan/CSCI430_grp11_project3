package view;

import controller.PolygonCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolygonButton extends JButton implements ActionListener {
    private final JPanel drawingPanel;
    private final View view;
    private final UndoManager undoManager;
    private final Model model;
    private final MouseHandler mouseHandler;
    private PolygonCommand polygonCommand;

    public PolygonButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Polygon");
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
        polygonCommand = null;
        undoManager.endCommand();
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
        drawingPanel.removeMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            // Left click behavior
            if (SwingUtilities.isLeftMouseButton(event)) {
                if (polygonCommand == null) {
                    polygonCommand = new PolygonCommand(model, undoManager);
                    polygonCommand.setPolygonPoint(event.getPoint(), true);
                    undoManager.beginCommand(polygonCommand);
                } else {
                    polygonCommand.setPolygonPoint(event.getPoint(), true);
                }
            }
            else if (SwingUtilities.isRightMouseButton(event)) {
                polygonCommand.closePolygon();
                resetState();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            if (polygonCommand != null) {
                polygonCommand.setPolygonPoint(event.getPoint(), false);
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            resetState();
        }
    }
}
