package model;

import view.UIContext;

import java.awt.*;

public class Label extends Item {
    private final Point startingPoint;
    private String text = "";

    public Label(Point point) {
        startingPoint = point;
    }

    public void addCharacter(char character) {
        text += character;
    }

    public void removeCharacter() {
        if (!text.isEmpty()) {
            text = text.substring(0, text.length() - 1);
        }
    }

    @Override
    public boolean includes(Point point) {
        return distance(point, startingPoint) < 10.0;
    }

    @Override
    public void render(UIContext uiContext) {
        uiContext.drawLabel(text, startingPoint);
    }

    @Override
    public void translate(int dx, int dy) {
        startingPoint.translate(dx, dy);
    }

    public String getText() {
        return text;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }
}
