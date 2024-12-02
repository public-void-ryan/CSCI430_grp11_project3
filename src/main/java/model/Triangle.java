package model;

import view.UIContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Triangle extends Item {
    private final List<Line> lines;

    public Triangle() {
        lines = new ArrayList<>();
    }

    public void addPoint(Point point) {
        if (lines.isEmpty()) {
            Line line = new Line();
            line.setStart(new Point(point));
            lines.add(line);
        } else if (lines.size() == 1) {
            Line firstLine = lines.get(0);
            firstLine.setEnd(new Point(point));

            Line secondLine = new Line();
            secondLine.setStart(new Point(point));
            lines.add(secondLine);
        } else if (lines.size() == 2) {
            Line secondLine = lines.get(1);
            secondLine.setEnd(new Point(point));

            Line closingLine = new Line();
            closingLine.setStart(new Point(point));
            closingLine.setEnd(new Point(lines.get(0).getStart()));
            lines.add(closingLine);
        }
    }

    public void updatePoint(int index, Point point) {
        switch (index) {
            case 0:
                lines.get(0).setStart(new Point(point));
                break;
            case 1:
                lines.get(0).setEnd(new Point(point));
                lines.get(1).setStart(new Point(point));
                break;
            case 2:
                lines.get(1).setEnd(new Point(point));
                lines.get(2).setStart(new Point(point));
                break;
        }
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
