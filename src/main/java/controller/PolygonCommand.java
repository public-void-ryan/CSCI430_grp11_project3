package controller;

import model.Model;
import model.Polygon;

import java.awt.*;

public class PolygonCommand extends Command {
    private final Polygon polygon;
    private int pointCount;

    public PolygonCommand(Model model, UndoManager manager) {
        super(model, manager);
        this.polygon = new Polygon();
        this.pointCount = 0;
    }

    public void setPolygonPoint(Point point, boolean finalPosition) {
        if (polygon.getPointCount() > pointCount) {
            polygon.updatePoint(pointCount, point);
        } else {
            polygon.addPoint(point);
        }

        if (finalPosition) {
            pointCount++;
        }

        model.notifyObservers();
    }

    public void closePolygon() {
        if (pointCount >= 3) {
            polygon.closePolygon();
            pointCount++;
        }

        model.notifyObservers();
    }

    public int getPointCount() {
        return pointCount;
    }

    @Override
    public void execute() {
        model.addItem(polygon);
    }

    @Override
    public boolean undo() {
        model.removeItem(polygon);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (pointCount != polygon.getPointCount()) {
            undo();
            return false;
        }
        return true;
    }
}
