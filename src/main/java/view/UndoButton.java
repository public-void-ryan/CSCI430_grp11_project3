package view;

import controller.*;

import javax.swing.*;
import java.awt.event.*;

public class UndoButton extends JButton implements ActionListener {
    private final UndoManager undoManager;

    public UndoButton(UndoManager undoManager) {
        super("Undo");
        this.undoManager = undoManager;

        addActionListener(this);
        setFocusable(false);
    }

    public void actionPerformed(ActionEvent event) {
        undoManager.undo();
    }
}
