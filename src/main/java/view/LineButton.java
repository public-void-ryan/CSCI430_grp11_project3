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

    public LineButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Line");
        this.model = model;
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;

        mouseHandler = new MouseHandler();

        addFocusListener(new FocusHandler());
        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
    }

    private void resetState() {
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        private int pointCount = 0;
        private LineCommand lineCommand;

        @Override
        public void mouseClicked(MouseEvent event) {
            if (++pointCount == 1) {
                lineCommand = new LineCommand(model, undoManager);
                lineCommand.setLinePoint(event.getPoint());
                undoManager.beginCommand(lineCommand);
            } else if (pointCount == 2) {
                lineCommand.setLinePoint(event.getPoint());
                pointCount = 0;
                undoManager.endCommand();
                resetState();
            }
        }
    }

    private class FocusHandler extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            undoManager.endCommand();
            resetState();
        }
    }
}
