package view;

import controller.PolylineCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LineButton extends JButton implements ActionListener {
    protected final JPanel drawingPanel;
    protected final View view;
    private final UndoManager undoManager;
    private final Model model;
    private final MouseHandler mouseHandler;
    private PolylineCommand polylineCommand;

    public LineButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Line");
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
        polylineCommand = null;
        undoManager.endCommand();
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
        drawingPanel.removeMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (polylineCommand == null || polylineCommand.getPointCount() == 0) {
                polylineCommand = new PolylineCommand(model, undoManager);
                polylineCommand.setPolylinePoint(event.getPoint(), true);
                undoManager.beginCommand(polylineCommand);
            } else if (polylineCommand.getPointCount() == 1) {
                polylineCommand.setPolylinePoint(event.getPoint(), true);
                resetState();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            if (polylineCommand != null) {
                polylineCommand.setPolylinePoint(event.getPoint(), false);
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            resetState();
        }
    }
}
