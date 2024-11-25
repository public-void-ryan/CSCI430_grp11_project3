package view;

import java.awt.*;

public class NewSwingUI implements UIContext {
    private Graphics graphics;

    public NewSwingUI() {}

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void drawLabel(String text, Point point) {
        if (point != null) {
            if (text == null || text.isEmpty()) {
                graphics.drawString("_", (int) point.getX(), (int) point.getY());
            } else {
                graphics.drawString(text, (int) point.getX(), (int) point.getY());
            }
        }
    }

    @Override
    public void drawLine(Point point1, Point point2) {
        if (point1 != null) {
            int x1 = Math.round((float) point1.getX());
            int y1 = Math.round((float) point1.getY());
            int x2 = (point2 != null) ? Math.round((float) point2.getX()) : x1;
            int y2 = (point2 != null) ? Math.round((float) point2.getY()) : y1;

            graphics.drawLine(x1, y1, x2, y2);
        }
    }
}
