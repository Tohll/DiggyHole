package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utils.Configuration;

public class GraphicEngine extends JPanel implements Runnable {
	private static final long serialVersionUID = -7273880090886312807L;
    private ImageIcon bush;
    private final Font defaultFont;
    private final int delay;
    private final boolean hasToDisplay;
    private final Random rand;
    private ImageIcon shadow;
    private final Font smallDefaultFont;
    private final Color uiBackgroundColor;
    private Point square;

    public GraphicEngine() {
    	this.square = new Point (Configuration.WIDTH/2, Configuration.HEIGHT/2);
        this.defaultFont = new Font("Arial", Font.BOLD, 18);
        this.smallDefaultFont = new Font("Arial", Font.BOLD, 11);
        this.uiBackgroundColor = new Color(0, 0, 0, 0.3f);
        this.delay = 25;
        this.rand = new Random();
        this.hasToDisplay = true;
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(Configuration.WIDTH, Configuration.HEIGHT));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        Thread animator;
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
    }

    private double round(final double value, final int precision) {
        final int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public void run() {
        long beforeTime;
        long timeDiff;
        long sleep;
        beforeTime = System.currentTimeMillis();
        while (this.hasToDisplay) {
            this.repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = this.delay - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (final InterruptedException e) {
                final String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
                Thread.currentThread().interrupt();
            }
            beforeTime = System.currentTimeMillis();
        }
    }
}
