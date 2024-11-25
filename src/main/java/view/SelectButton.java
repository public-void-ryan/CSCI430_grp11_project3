package view;

import controller.SelectCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.event.*;

public class SelectButton extends JButton implements ActionListener {
    protected final JPanel drawingPanel;
    protected final View view;
    private final UndoManager undoManager;
    private final Model model;
    private SelectCommand selectCommand;
    private final MouseHandler mouseHandler;

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
        selectCommand = new SelectCommand(model, undoManager);
        drawingPanel.addMouseListener(mouseHandler);
        undoManager.beginCommand(selectCommand);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (selectCommand.setPoint(event.getPoint())) {
                drawingPanel.removeMouseListener(this);
                undoManager.endCommand();
            } else {
                undoManager.cancelCommand();
            }
        }
    }
}
