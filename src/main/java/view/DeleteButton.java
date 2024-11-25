package view;

import controller.DeleteCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButton extends JButton implements ActionListener {
    private final UndoManager undoManager;
    private final Model model;

    public DeleteButton(Model model, UndoManager undoManager) {
        super("Delete");
        this.model = model;
        this.undoManager = undoManager;

        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        DeleteCommand deleteCommand = new DeleteCommand(model, undoManager);
        undoManager.beginCommand(deleteCommand);
        undoManager.endCommand();
    }
}
