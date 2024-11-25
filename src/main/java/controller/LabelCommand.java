package controller;

import model.Label;
import model.Model;

import java.awt.*;

public class LabelCommand extends Command {
    private final Label label;

    public LabelCommand(Model model, UndoManager manager, Point point) {
        super(model, manager);
        this.label = new Label(point);
    }

    public void addCharacter(char character) {
        label.addCharacter(character);
        model.notifyObservers();
    }

    public void removeCharacter() {
        label.removeCharacter();
        model.notifyObservers();
    }

    @Override
    public void execute() {
        model.addItem(label);
    }

    @Override
    public boolean undo() {
        model.removeItem(label);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (label.getText().isEmpty()) {
            undo();
            return false;
        }

        return true;
    }
}
