package view;

import controller.LineCommand;
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
    private LineCommand lineCommand;

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
        lineCommand = null;
        undoManager.endCommand();
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
        drawingPanel.removeMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (lineCommand == null || lineCommand.getPointCount() == 0) {
                lineCommand = new LineCommand(model, undoManager);
                lineCommand.setLinePoint(event.getPoint(), true);
                undoManager.beginCommand(lineCommand);
            } else if (lineCommand.getPointCount() == 1) {
                lineCommand.setLinePoint(event.getPoint(), true);
                resetState();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            if (lineCommand != null) {
                lineCommand.setLinePoint(event.getPoint(), false);
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            resetState();
        }
    }
}
