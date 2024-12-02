package controller;

import model.Line;
import model.Model;

import java.awt.*;

public class LineCommand extends Command {
    private final Line line;
    private int pointCount;

    public LineCommand(Model model, UndoManager manager) {
        super(model, manager);
        this.line = new Line();
        this.pointCount = 0;
    }

    public void setLinePoint(Point point, boolean finalPosition) {
        if (pointCount == 0) {
            line.setStart(point);
        } else if (pointCount == 1) {
            line.setEnd(point);
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
        model.addItem(line);
    }

    @Override
    public boolean undo() {
        model.removeItem(line);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (pointCount != 2) {
            undo();
            return false;
        }
        return true;
    }
}
