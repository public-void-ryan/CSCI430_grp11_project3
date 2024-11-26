package controller;

import model.Model;

public class MoveCommand extends Command {
    private int dx = 0;
    private int dy = 0;

    public MoveCommand(Model model, UndoManager manager) {
        super(model, manager);
    }

    public void applyTranslation(int newDx, int newDy) {
        model.moveSelectedItems(-dx, -dy);
        model.moveSelectedItems(newDx, newDy);
        dx = newDx;
        dy = newDy;
    }

    @Override
    public void execute() {
        model.moveSelectedItems(dx, dy);
    }

    @Override
    public boolean undo() {
        model.moveSelectedItems(-dx, -dy);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }
}
