package controller;

import model.Model;

public class OpenCommand extends Command {
    private final String fileName;

    public OpenCommand(Model model, UndoManager manager, String fileName) {
        super(model, manager);
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        model.retrieve(fileName);
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
