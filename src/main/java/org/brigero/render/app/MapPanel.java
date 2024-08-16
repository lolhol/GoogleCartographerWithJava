package org.brigero.render.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapPanel extends JPanel {
    public Map map;

    public MapPanel() {
        this.setPreferredSize(new Dimension(400, 400));
        this.setLayout(new BorderLayout());

        // Create the Map object
        map = new Map();

        JLabel label = new JLabel("Map Area");
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");

        // Add action listeners for zoom buttons
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.zoomIn(); // Call the zoomIn method in the Map class
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.zoomOut(); // Call the zoomOut method in the Map class
            }
        });

        // Create a panel to hold the zoom buttons and the map
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        buttonPanel.add(zoomInButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(map);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(zoomOutButton);

        this.add(label, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
    }
}