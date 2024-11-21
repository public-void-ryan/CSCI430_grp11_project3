package controller;

import model.Line;

import java.awt.*;

public class LineCommand extends Command {
    private Line line;
    private int pointCount;

    public LineCommand() {
        this(null, null);
        pointCount = 0;
    }

    public LineCommand(Point point) {
        this(point, null);
        pointCount = 1;
    }

    public LineCommand(Point point1, Point point2) {
        line = new Line(point1, point2);
        pointCount = 2;
    }

    public void setLinePoint(Point point) {
        if (++pointCount == 1) {
            line.setPoint1(point);
        } else if (pointCount == 2) {
            line.setPoint2(point);
        }
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
