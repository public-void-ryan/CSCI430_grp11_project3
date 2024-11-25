package model;

import java.io.*;
import java.util.*;

public class Model {
    private Vector<Item> itemList;
    private Vector<Item> selectedList;
    private final List<ModelObserver> observers;

    public Model() {
        itemList = new Vector<>();
        selectedList = new Vector<>();
        observers = new ArrayList<>();
    }

    // Observer management
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.modelChanged();
        }
    }

    // Methods to manipulate items
    public void markSelected(Item item) {
        if (itemList.contains(item)) {
            itemList.remove(item);
            selectedList.add(item);
            notifyObservers();
        }
    }

    public void markUnselected(Item item) {
        if (selectedList.contains(item)) {
            selectedList.remove(item);
            itemList.add(item);
            notifyObservers();
        }
    }

    public void addItem(Item item) {
        itemList.add(item);
        notifyObservers();
    }

    public void removeItem(Item item) {
        itemList.remove(item);
        notifyObservers();
    }

    public Enumeration<Item> getItems() {
        return itemList.elements();
    }

    public Enumeration<Item> getSelectedItems() {
        return selectedList.elements();
    }

    public void deleteSelectedItems() {
        selectedList.clear();
        notifyObservers();
    }

    public void save(String fileName) {
        try (FileOutputStream file = new FileOutputStream(fileName);
             ObjectOutputStream output = new ObjectOutputStream(file)) {
            output.writeObject(itemList);
            output.writeObject(selectedList);
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
            notifyObservers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
