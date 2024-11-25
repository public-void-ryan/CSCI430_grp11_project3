package controller;

import model.Model;

public class SaveCommand extends Command {
    private final String fileName;

    public SaveCommand(Model model, UndoManager manager, String fileName) {
        super(model, manager);
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        model.save(fileName);
    }

    @Override
    public boolean undo() {
        return false;
    }

    @Override
    public boolean redo() {
        return false;
    }
}
