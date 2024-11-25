package view;

import controller.*;

import javax.swing.*;
import java.awt.event.*;

public class RedoButton extends JButton implements ActionListener {
    private final UndoManager undoManager;

    public RedoButton(UndoManager undoManager) {
        super("redo");
        this.undoManager = undoManager;

        addActionListener(this);
        setFocusable(false);
    }

    public void actionPerformed(ActionEvent event) {
        undoManager.redo();
    }
}
