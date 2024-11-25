package view;

import controller.OpenCommand;
import controller.UndoManager;
import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenButton extends JButton implements ActionListener {
    protected final View view;
    private final UndoManager undoManager;
    private final Model model;

    public OpenButton(Model model, UndoManager undoManager, View view) {
        super("Open");
        this.model = model;
        this.undoManager = undoManager;
        this.view = view;

        addActionListener(this);
        setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(view, "Please type the file name to open:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            OpenCommand command = new OpenCommand(model, undoManager, fileName);
            undoManager.beginCommand(command);
            undoManager.endCommand();
            view.setFileName(fileName);
        }
    }
}
