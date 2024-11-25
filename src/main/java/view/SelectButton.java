package view;

import controller.SelectCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectButton extends JButton implements ActionListener {
    protected final JPanel drawingPanel;
    protected final View view;
    private final UndoManager undoManager;
    private final Model model;
    private final MouseHandler mouseHandler;
    private SelectCommand selectCommand;

    public SelectButton(Model model, UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Select");
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
        drawingPanel.requestFocusInWindow();
    }

    private void resetState() {
        selectCommand = null;
        undoManager.endCommand();
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        drawingPanel.removeMouseListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (selectCommand != null) {
                undoManager.endCommand();
            }

            selectCommand = new SelectCommand(model, undoManager);
            undoManager.beginCommand(selectCommand);
            selectCommand.setPoint(event.getPoint());
        }

        @Override
        public void mouseExited(MouseEvent event) {
            resetState();
        }
    }
}
