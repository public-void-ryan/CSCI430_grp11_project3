package controller;

import model.Item;
import model.Model;

import java.util.Enumeration;
import java.util.Vector;

public class DeleteCommand extends Command {
    private final Vector<Item> itemList;

    public DeleteCommand(Model model, UndoManager manager) {
        super(model, manager);
        itemList = new Vector<>();

        Enumeration<Item> selectedItems = model.getSelectedItems();
        while (selectedItems.hasMoreElements()) {
            Item item = selectedItems.nextElement();
            itemList.add(item);
        }
    }

    @Override
    public void execute() {
        model.deleteSelectedItems();
    }

    @Override
    public boolean undo() {
        Enumeration<Item> deletedItems = itemList.elements();
        while (deletedItems.hasMoreElements()) {
            Item item = deletedItems.nextElement();
            model.addItem(item);
            model.markSelected(item);
        }
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }
}
