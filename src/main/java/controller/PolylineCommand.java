package controller;

import model.Model;
import model.Polyline;

import java.awt.*;

public class PolylineCommand extends Command {
    private final Polyline polyline;
    private int pointCount;

    public PolylineCommand(Model model, UndoManager manager) {
        super(model, manager);
        this.polyline = new Polyline();
        this.pointCount = 0;
    }

    public void setPolylinePoint(Point point, boolean finalPosition) {
        if (pointCount == 0) {
            polyline.addPoint(point);
        } else if (pointCount > 0) {
            if (polyline.getPointCount() > pointCount) {
                polyline.getPoint(pointCount).setLocation(point);
            } else {
                polyline.addPoint(point);
            }
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
        model.addItem(polyline);
    }

    @Override
    public boolean undo() {
        model.removeItem(polyline);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (pointCount != polyline.getPointCount()) {
            undo();
            return false;
        }

        return true;
    }
}
