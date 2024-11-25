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
        if (pointCount == 0) {
            triangle.setPoint1(point);
        } else if (pointCount == 1) {
            triangle.setPoint2(point);
        } else if (pointCount == 2) {
            triangle.setPoint3(point);
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
        if (triangle.getPoint1() == null || triangle.getPoint2() == null || triangle.getPoint3() == null) {
            undo();
            return false;
        }
        return true;
    }
}
