package view;

import model.*;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class View extends JFrame implements ModelObserver {
    private final UIContext uiContext;
    private final Model model;
    private final UndoManager undoManager;

    private final JPanel drawingPanel;
    private final JPanel buttonPanel;

    private String fileName;

    public View(Model model, UndoManager undoManager) {
        super("Drawing Program 1.1 - Untitled");
        this.model = model;
        this.undoManager = undoManager;
        this.uiContext = new NewSwingUI();

        this.model.addObserver(this);

        drawingPanel = new DrawingPanel();
        buttonPanel = new JPanel();

        initializeComponents();

        Container contentPane = getContentPane();
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(drawingPanel, BorderLayout.CENTER);

        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeComponents() {
        JButton lineButton = new LineButton(model, undoManager, this, drawingPanel);
        JButton triangleButton = new TriangleButton(model, undoManager, this, drawingPanel);
        JButton polygonButton = new PolygonButton(model, undoManager, this, drawingPanel);
        JButton labelButton = new LabelButton(model, undoManager, this, drawingPanel);
        JButton selectButton = new SelectButton(model, undoManager, this, drawingPanel);
        JButton moveButton = new MoveButton(model, undoManager, this, drawingPanel);
        JButton saveButton = new SaveButton(model, undoManager, this);
        JButton openButton = new OpenButton(model, undoManager, this);
        JButton deleteButton = new DeleteButton(model, undoManager);
        JButton undoButton = new UndoButton(undoManager);
        JButton redoButton = new RedoButton(undoManager);

        buttonPanel.add(lineButton);
        buttonPanel.add(triangleButton);
        buttonPanel.add(polygonButton);
        buttonPanel.add(labelButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(openButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
    }

    @Override
    public void modelChanged() {
        refresh();
    }

    public void refresh() {
        drawingPanel.repaint();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        setTitle("Drawing Program 1.1 - " + fileName);
    }

    public String getFileName() {
        return fileName;
    }

    private class DrawingPanel extends JPanel {
        private MouseListener currentMouseListener;
        private KeyListener currentKeyListener;
        private FocusListener currentFocusListener;

        public DrawingPanel() {
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((NewSwingUI) uiContext).setGraphics(g);

            g.setColor(Color.BLUE);
            Enumeration<Item> items = model.getItems();
            while (items.hasMoreElements()) {
                items.nextElement().render(uiContext);
            }

            g.setColor(Color.RED);
            Enumeration<Item> selectedItems = model.getSelectedItems();
            while (selectedItems.hasMoreElements()) {
                selectedItems.nextElement().render(uiContext);
            }
        }

        public void addMouseListener(MouseListener newListener) {
            if (currentMouseListener != null) {
                removeMouseListener(currentMouseListener);
            }
            currentMouseListener = newListener;
            super.addMouseListener(newListener);
        }

        public void addKeyListener(KeyListener newListener) {
            if (currentKeyListener != null) {
                removeKeyListener(currentKeyListener);
            }
            currentKeyListener = newListener;
            super.addKeyListener(newListener);
        }

        public void addFocusListener(FocusListener newListener) {
            if (currentFocusListener != null) {
                removeFocusListener(currentFocusListener);
            }
            currentFocusListener = newListener;
            super.addFocusListener(newListener);
        }
    }
}
