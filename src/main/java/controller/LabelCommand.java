package controller;

import model.Label;

import java.awt.*;

public class LabelCommand extends Command {
    private model.Label label;

    public LabelCommand(Point point) {
        label = new Label(point);
    }

    public void addCharacter(char character) {
        label.addCharacter(character);
        model.setChanged();
    }

    public void removeCharacter() {
        label.removeCharacter();
        model.setChanged();
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
}
