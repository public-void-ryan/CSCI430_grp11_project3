package controller;

import model.Item;

import java.util.*;

public class DeleteCommand extends Command {
    private Vector<Item> itemList;

    public DeleteCommand() {
        itemList = new Vector<Item>();
        Enumeration<Item> enumeration = model.getSelectedItems();

        while (enumeration.hasMoreElements()) {
            Item item = (Item) (enumeration.nextElement());
            itemList.add(item);
        }

        model.deleteSelectedItems();
    }

    public boolean undo() {
        Enumeration<Item> enumeration = itemList.elements();

        while (enumeration.hasMoreElements()) {
            Item item = (Item) (enumeration.nextElement());
            model.addItem(item);
            model.markSelected(item);
        }

        return true;
    }

    public boolean redo() {
        execute();
        return true;
    }

    public void execute() {
        model.deleteSelectedItems();
    }
}
