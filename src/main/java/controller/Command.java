package controller;

import model.Model;

public abstract class Command {
    protected UndoManager manager;
    protected Model model;

    public Command(Model model, UndoManager manager) {
        this.model = model;
        this.manager = manager;
    }

    public abstract boolean undo();

    public abstract boolean redo();

    public abstract void execute();

    public boolean end() {
        return true;
    }
}
