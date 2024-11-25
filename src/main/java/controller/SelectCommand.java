package controller;

import model.Item;
import model.Model;

import java.awt.*;
import java.util.Enumeration;

public class SelectCommand extends Command {
    private Item item;

    public SelectCommand(Model model, UndoManager manager) {
        super(model, manager);
    }

    public boolean setPoint(Point point) {
        boolean found = false;
        Enumeration<Item> items = model.getItems();

        while (items.hasMoreElements()) {
            item = items.nextElement();
            if (item.includes(point)) {
                model.markSelected(item);
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public boolean undo() {
        model.markUnselected(item);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public void execute() {
        if (item != null) {
            model.markSelected(item);
        }
    }
}
