package main;

import controller.UndoManager;
import model.Model;
import view.View;

public class DrawingProgram {
    public static void main(String[] args) {
        Model model = new Model();
        UndoManager undoManager = new UndoManager();
        View view = new View(model, undoManager);

        view.setVisible(true);
    }
}
