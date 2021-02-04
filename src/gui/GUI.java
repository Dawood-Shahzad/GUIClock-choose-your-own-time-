package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI {

    public static void main(String[] args) {
        ClockDrawingFrame frame = new ClockDrawingFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ClockDrawingFrame extends JFrame {

    private JTextField hourField;
    private JTextField minuteField;
    private ClockPanel clock;

    public ClockDrawingFrame() {
        setTitle("Clock GUI");

        DocumentListener listener = new ClockFieldListener();

        JPanel panel = new JPanel();

        panel.add(new JLabel("Hours:"));
        hourField = new JTextField("12", 3);
        panel.add(hourField);
        hourField.getDocument().addDocumentListener(listener);

        panel.add(new JLabel("Minutes:"));
        minuteField = new JTextField("00", 3);
        panel.add(minuteField);
        minuteField.getDocument().addDocumentListener(listener);

        add(panel, BorderLayout.SOUTH);

        clock = new ClockPanel();
        add(clock, BorderLayout.CENTER);
        pack();
    }

    public void setClock() {
        try {
            int hours = Integer.parseInt(hourField.getText().trim());
            int minutes = Integer.parseInt(minuteField.getText().trim());
            clock.setTime(hours, minutes);
        } catch (NumberFormatException e) {
        }

    }

    private class ClockFieldListener implements DocumentListener {

        public void insertUpdate(DocumentEvent event) {
            setClock();
        }

        public void removeUpdate(DocumentEvent event) {
            setClock();
        }

        public void changedUpdate(DocumentEvent event) {
        }
    }
}

class ClockPanel extends JPanel {

    private double minutes = 0;
    private int RADIUS = 100;
    private double MINUTE_HAND_LENGTH = 0.8 * RADIUS;
    private double HOUR_HAND_LENGTH = 0.6 * RADIUS;

    public ClockPanel() {
        setPreferredSize(new Dimension(2 * RADIUS + 1, 2 * RADIUS + 1));
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D circle = new Ellipse2D.Double(0, 0, 2 * RADIUS, 2 * RADIUS);
        g2.draw(circle);

        double hourAngle = Math.toRadians(90 - 360 * minutes / (12 * 60));
        drawHand(g2, hourAngle, HOUR_HAND_LENGTH);

        double minuteAngle = Math.toRadians(90 - 360 * minutes / 60);
        drawHand(g2, minuteAngle, MINUTE_HAND_LENGTH);
    }

    public void drawHand(Graphics2D g2, double angle, double handLength) {
        Point2D end = new Point2D.Double(RADIUS + handLength * Math.cos(angle),
                RADIUS - handLength * Math.sin(angle));
        Point2D center = new Point2D.Double(RADIUS, RADIUS);
        g2.draw(new Line2D.Double(center, end));
    }

    public void setTime(int h, int m) {
        minutes = h * 60 + m;
        repaint();
    }

}
