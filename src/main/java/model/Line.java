package model;

import view.UIContext;
import java.awt.*;

public class Line extends Item {
    private Point start;
    private Point end;

    public Line() {
        this.start = null;
        this.end = null;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public void setStart(Point point) {
        start = point;
    }

    public void setEnd(Point point) {
        end = point;
    }

    @Override
    public boolean includes(Point point) {
        if (start == null || end == null) {
            return false;
        }

        return distanceToLineSegment(point, start, end) < 10.0;
    }

    @Override
    public void render(UIContext uiContext) {
        uiContext.drawLine(start, end);
    }

    @Override
    public void translate(int dx, int dy) {
        if (start == null || end == null) {
            return;
        }

        start.translate(dx, dy);
        end.translate(dx, dy);
    }

    private double distanceToLineSegment(Point p, Point p1, Point p2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double lengthSquared = dx * dx + dy * dy;

        if (lengthSquared == 0) {
            return p.distance(p1);
        }

        double t = ((p.x - p1.x) * dx + (p.y - p1.y) * dy) / lengthSquared;
        t = Math.max(0, Math.min(1, t));

        double projectionX = p1.x + t * dx;
        double projectionY = p1.y + t * dy;

        return p.distance(projectionX, projectionY);
    }
}
