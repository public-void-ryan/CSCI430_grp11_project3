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

    public void setLinePoint(Point point) {
        if (pointCount == 0) {
            line.setPoint1(point);
            pointCount++;
        } else {
            line.setPoint2(point);
        }
        model.notifyObservers();
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
        if (line.getPoint1() == null) {
            undo();
            return false;
        }

        if (line.getPoint2() == null) {
            line.setPoint2(line.getPoint1());
        }

        return true;
    }
}
