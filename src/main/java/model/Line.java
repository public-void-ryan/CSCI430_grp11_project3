package model;

import view.UIContext;

import java.awt.*;

public class Line extends Item {
    private Point point1;
    private Point point2;

    public Line(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Line(Point point1) {
        this.point1 = point1;
        point2 = null;
    }

    public Line() {
        point1 = null;
        point2 = null;
    }

    @Override
    public boolean includes(Point point) {
        if (point1 == null || point2 == null) {
            return false;
        }

        double distance = distanceToLineSegment(point, point1, point2);
        return distance < 10.0;
    }

    private double distanceToLineSegment(Point clickedPoint, Point linePoint1, Point linePoint2) {
        double linePoint1X = linePoint1.getX();
        double linePoint1Y = linePoint1.getY();
        double linePoint2X = linePoint2.getX();
        double linePoint2Y = linePoint2.getY();
        double clickedPointX = clickedPoint.getX();
        double clickedPointY = clickedPoint.getY();

        double lineLengthSquared = Math.pow(linePoint2X - linePoint1X, 2) + Math.pow(linePoint2Y - linePoint1Y, 2);

        if (lineLengthSquared == 0) {
            return clickedPoint.distance(linePoint1);
        }

        double t = ((clickedPointX - linePoint1X) * (linePoint2X - linePoint1X) + (clickedPointY - linePoint1Y) * (linePoint2Y - linePoint1Y)) / lineLengthSquared;
        t = Math.max(0, Math.min(1, t));

        double closestX = linePoint1X + t * (linePoint2X - linePoint1X);
        double closestY = linePoint1Y + t * (linePoint2Y - linePoint1Y);

        return clickedPoint.distance(closestX, closestY);
    }

    @Override
    public void render(UIContext uiContext) {
        uiContext.drawLine(point1, point2);
    }

    public void setPoint1(Point point) {
        point1 = point;
    }

    public void setPoint2(Point point) {
        point2 = point;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public String toString() {
        return "model.Line  from " + point1 + " to " + point2;
    }
}
