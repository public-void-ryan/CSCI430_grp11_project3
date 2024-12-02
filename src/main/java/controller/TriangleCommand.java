package controller;

import model.Model;
import model.Triangle;

import java.awt.*;

public class TriangleCommand extends Command {
    private final Triangle triangle;
    private int pointCount;

    public TriangleCommand(Model model, UndoManager manager) {
        super(model, manager);
        this.triangle = new Triangle();
        this.pointCount = 0;
    }

    public void setTrianglePoint(Point point, boolean finalPosition) {
        if (triangle.getPointCount() > pointCount) {
            triangle.updatePoint(pointCount, point);
        } else {
            triangle.addPoint(point);
        }

        if (finalPosition) {
            pointCount++;
        }

        model.notifyObservers();
    }

    public int getPointCount() {
        return pointCount;
    }

    @Override
    public void execute() {
        model.addItem(triangle);
    }

    @Override
    public boolean undo() {
        model.removeItem(triangle);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (pointCount != 3) {
            undo();
            return false;
        }
        return true;
    }
}
