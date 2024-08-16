package org.brigero.render.app;

import javax.swing.*;
import java.awt.*;

public class DropdownPanel extends JPanel {
    public DropdownPanel(String[][] dropdownOptions) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Arrange dropdowns vertically

        // Create and add dropdowns based on the provided options
        for (int i = 0; i < dropdownOptions.length; i++) {
            JComboBox<String> dropdown = new JComboBox<>(dropdownOptions[i]);
            this.add(new JLabel("Dropdown " + (i + 1) + ":"));
            this.add(dropdown);
            this.add(Box.createVerticalStrut(10)); // Add some space between dropdowns
        }
    }
}