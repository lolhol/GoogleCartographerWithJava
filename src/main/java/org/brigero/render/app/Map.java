package org.brigero.render.app;

import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
    private int[][] mapData; // 2D array representing the height of each grid cell
    private double zoomFactor = 1.0; // Factor to control zoom level

    public Map() {
        this.setPreferredSize(new Dimension(400, 400));

        // Initialize map data (for example, a 50x50 grid with varying heights)
        mapData = new int[100][100];
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                mapData[i][j] = (int) (Math.random() * 10); // Random height between 0 and 9
            }
        }
    }

    public void setMapData(int[][] newData) {
        mapData = newData;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int mapWidth = mapData.length;
        int mapHeight = mapData[0].length;

        // Calculate cell size based on panel size and zoom factor
        int cellSize = (int) (Math.min(panelWidth / (double) mapWidth, panelHeight / (double) mapHeight) * zoomFactor);

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                int height = mapData[i][j];
                int x = i * cellSize;
                int y = j * cellSize;

                // Calculate color based on height
                int colorValue = 255 - height * 25;
                g2d.setColor(new Color(colorValue, colorValue, colorValue)); // Greyscale based on height
                g2d.fillRect(x, y, cellSize, cellSize);

                // Draw the border of the cell
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    public void zoomIn() {
        zoomFactor *= 1.2;
        repaint();
    }

    public void zoomOut() {
        zoomFactor /= 1.2;
        repaint();
    }
}
