package model;

import view.UIContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Item {
    private final List<Line> lines;
    private boolean isClosed;

    public Polygon() {
        lines = new ArrayList<>();
        isClosed = false;
    }

    public void addPoint(Point point) {
        if (lines.isEmpty()) {
            Line line = new Line();
            line.setStart(new Point(point));
            lines.add(line);
        } else {
            Line lastLine = lines.get(lines.size() - 1);

            if (lastLine.getEnd() == null) {
                lastLine.setEnd(new Point(point));
            }

            Line newLine = new Line();
            newLine.setStart(new Point(point));
            lines.add(newLine);
        }
    }

    public void updatePoint(int index, Point point) {
        if (index == 0) {
            lines.get(0).setStart(new Point(point));
        } else {
            lines.get(index - 1).setEnd(new Point(point));
            lines.get(index).setStart(new Point(point));

            if (isClosed && index == getPointCount() - 1) {
                lines.get(lines.size() - 1).setStart(new Point(point));
                lines.get(lines.size() - 1).setEnd(new Point(lines.get(0).getStart()));
            }
        }
    }

    public void closePolygon() {
        Line lastLine = lines.get(lines.size() - 1);
        if (lastLine.getEnd() == null) {
            lastLine.setEnd(new Point(lines.get(0).getStart()));
        } else {
            Line closingLine = new Line();
            closingLine.setStart(new Point(lastLine.getEnd()));
            closingLine.setEnd(new Point(lines.get(0).getStart()));
            lines.add(closingLine);
        }
        isClosed = true;
    }

    public int getPointCount() {
        return lines.size();
    }

    public List<Line> getLines() {
        return lines;
    }

    @Override
    public boolean includes(Point point) {
        for (Line line : lines) {
            if (line.includes(point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(UIContext uiContext) {
        for (Line line : lines) {
            line.render(uiContext);
        }
    }

    @Override
    public void translate(int dx, int dy) {
        for (Line line : lines) {
            line.translate(dx, dy);
        }
    }
}
