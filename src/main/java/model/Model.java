package model;

import view.View;

import java.io.*;
import java.util.*;

public class Model {
    private Vector<Item> itemList;
    private Vector<Item> selectedList;

    // list of "currently selected" items
    // private static view.UIContext uiContext;
    private static View view;

    public Model() {
        itemList = new Vector<Item>();
        selectedList = new Vector<Item>();
    }

    public static void setView(View view) {
        Model.view = view;
    }

    public void markSelected(Item item) {
        // marks an item as selected by moving it to the
        // selceted list.
        if (itemList.contains(item)) {
            itemList.remove(item);
            selectedList.add(item);
            view.refresh();
        }
    }

    public void unSelect(Item item) {
        if (selectedList.contains(item)) {
            selectedList.remove(item);
            itemList.add(item);
            view.refresh();
        }
    }

    public void deleteSelectedItems() {
        selectedList.removeAllElements();
        view.refresh();
    }

    public void addItem(Item item) {
        itemList.add(item);
        view.refresh();
    }

    public void removeItem(Item item) {
        itemList.remove(item);
        view.refresh();
    }

    public Enumeration<Item> getItems() {
        return itemList.elements();
    }

    public void setChanged() {
        view.refresh();
    }

    public Enumeration<Item> getSelectedItems() {
        return selectedList.elements();
    }

    // other fields, methods and classes
    public void save(String fileName) {
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(itemList);
            output.writeObject(selectedList);
            output.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void retrieve(String fileName) {
        try (FileInputStream file = new FileInputStream(fileName);
                ObjectInputStream input = new ObjectInputStream(file)) {
            itemList = (Vector<Item>) input.readObject();
            selectedList = (Vector<Item>) input.readObject();
            // model.Item.setUIContext(uiContext);
            view.refresh();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}
