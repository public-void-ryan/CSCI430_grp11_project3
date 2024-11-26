package model;

import view.UIContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polyline extends Item {
    protected final List<Point> points;

    public Polyline() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public int getPointCount() {
        return points.size();
    }

    @Override
    public boolean includes(Point point) {
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            if (distanceToLineSegment(point, p1, p2) < 10.0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(UIContext uiContext) {
        for (int i = 0; i < points.size() - 1; i++) {
            uiContext.drawLine(points.get(i), points.get(i + 1));
        }
        renderSpecific(uiContext);
    }

    protected void renderSpecific(UIContext uiContext) {
        if (points.size() > 2) {
            uiContext.drawLine(points.get(points.size() - 1), points.get(0));
        }
    }

    @Override
    public void translate(int dx, int dy) {
        for (Point point : points) {
            point.translate(dx, dy);
        }
    }

    private double distanceToLineSegment(Point p, Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double lengthSquared = dx * dx + dy * dy;
        if (lengthSquared == 0) {
            return p.distance(p1);
        }
        double t = ((p.getX() - p1.getX()) * dx + (p.getY() - p1.getY()) * dy) / lengthSquared;
        t = Math.max(0, Math.min(1, t));
        double projectionX = p1.getX() + t * dx;
        double projectionY = p1.getY() + t * dy;
        return p.distance(projectionX, projectionY);
    }
}
