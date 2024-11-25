package model;

import view.UIContext;

import java.awt.*;

public class Triangle extends Item {
    private final Line line1;
    private final Line line2;
    private final Line line3;

    public Triangle() {
        this.line1 = new Line();
        this.line2 = new Line();
        this.line3 = new Line();
    }

    public void setPoint1(Point point) {
        line1.setPoint1(point);
    }

    public void setPoint2(Point point) {
        line1.setPoint2(point);
        line2.setPoint1(point);
    }

    public void setPoint3(Point point) {
        line2.setPoint2(point);
        line3.setPoint1(point);
        line3.setPoint2(line1.getPoint1());
    }

    public Point getPoint1() {
        return line1.getPoint1();
    }

    public Point getPoint2() {
        return line1.getPoint2();
    }

    public Point getPoint3() {
        return line3.getPoint2();
    }

    @Override
    public boolean includes(Point point) {
        return line1.includes(point) || line2.includes(point) || line3.includes(point);
    }

    @Override
    public void render(UIContext uiContext) {
        line1.render(uiContext);
        line2.render(uiContext);
        line3.render(uiContext);
    }
}
