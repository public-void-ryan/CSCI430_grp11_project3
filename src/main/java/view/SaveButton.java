package view;

import controller.SaveCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButton extends JButton implements ActionListener {
    protected final View view;
    private final UndoManager undoManager;
    private final Model model;

    public SaveButton(Model model, UndoManager undoManager, View view) {
        super("Save");
        this.model = model;
        this.undoManager = undoManager;
        this.view = view;

        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String fileName = view.getFileName();
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = JOptionPane.showInputDialog(view, "Please specify file name:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                view.setFileName(fileName);
            } else {
                return;
            }
        }

        SaveCommand command = new SaveCommand(model, undoManager, fileName);
        undoManager.beginCommand(command);
        undoManager.endCommand();
    }
}
