package org.brigero.render.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class AppRenderer extends Thread {
    private final MapPanel _K_MAP_PANEL = new MapPanel();

    @Override
    public void run() {
        JFrame frame = new JFrame("Cartographer Viewer");
        JButton button = new JButton("Submit");
        JTextField textField = new JTextField(20); // 20 is the width of the text field

        // Set up the frame's layout manager
        frame.setLayout(new BorderLayout());

        // Create a panel for the text field and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Y_AXIS means vertical stacking

        // Add components to the input panel
        inputPanel.add(textField);
        inputPanel.add(Box.createVerticalStrut(10)); // Adds space between components
        inputPanel.add(button);
        inputPanel.add(Box.createVerticalStrut(10)); // Adds space between components
        inputPanel.add(_K_MAP_PANEL);

        // Add padding to the inputPanel
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10 pixels of padding on all sides

        // Add the input panel to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        //frame.add(new DropdownPanel(dropdownOptions), BorderLayout.WEST);

        // Set up the button action listener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                JOptionPane.showMessageDialog(frame, "You entered: " + inputText);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500); // Adjusted size to fit both the input panel and map field
        frame.setVisible(true);
    }
}
