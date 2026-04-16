package color.palette.generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ColorPaletteGenerator extends JFrame {

    private Color[] palette = new Color[5]; // Array of 5 colors

    private JPanel[] colorPanels = new JPanel[5];
    private JPanel paletteContainer;

    private JButton btnGenerate, btnRotateLeft, btnRotateRight, btnReplace;
    private JComboBox<String> selectIndex;
    private JTextField txtHexColor;

    private Random random = new Random();

    public ColorPaletteGenerator() {
        setTitle("Color Palette Generator");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        

        // Top Panel: Palette
        paletteContainer = new JPanel();
        paletteContainer.setLayout(new GridLayout(1, 5, 10, 10));
        paletteContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create 5 color blocks
        for (int i = 0; i < 5; i++) {
            colorPanels[i] = new JPanel();
            colorPanels[i].setBackground(Color.DARK_GRAY);
            paletteContainer.add(colorPanels[i]);
        }

        add(paletteContainer, BorderLayout.CENTER);

        // Bottom Panel: Controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 1, 5, 5));

        // Buttons
        btnGenerate = new JButton("Generate Random Palette");
        btnRotateLeft = new JButton("Rotate Left");
        btnRotateRight = new JButton("Rotate Right");
        btnReplace = new JButton("Replace Color");

        // Row for rotate
        JPanel rotatePanel = new JPanel();
        rotatePanel.add(btnRotateLeft);
        rotatePanel.add(btnRotateRight);

        // Row for replace function
        JPanel replacePanel = new JPanel();
        replacePanel.add(new JLabel("Index (0-4):"));

        selectIndex = new JComboBox<>(new String[]{"0", "1", "2", "3", "4"});
        replacePanel.add(selectIndex);

        replacePanel.add(new JLabel("HEX (#RRGGBB):"));

        txtHexColor = new JTextField("#FFFFFF", 7);
        replacePanel.add(txtHexColor);

        replacePanel.add(btnReplace);

        controlPanel.add(btnGenerate);
        controlPanel.add(rotatePanel);
        controlPanel.add(replacePanel);

        add(controlPanel, BorderLayout.SOUTH);

        // Button Listeners
        btnGenerate.addActionListener(e -> generatePalette());
        btnRotateLeft.addActionListener(e -> rotateLeft());
        btnRotateRight.addActionListener(e -> rotateRight());
        btnReplace.addActionListener(e -> replaceColor());

        setVisible(true);
    }

    // Generate 5 random colors
    private void generatePalette() {
        for (int i = 0; i < 5; i++) {
            palette[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colorPanels[i].setBackground(palette[i]);
        }
    }

    // Rotate palette left
    private void rotateLeft() {
        Color first = palette[0];
        for (int i = 0; i < 4; i++) {
            palette[i] = palette[i + 1];
        }
        palette[4] = first;
        updatePanels();
    }

    // Rotate palette right
    private void rotateRight() {
        Color last = palette[4];
        for (int i = 4; i > 0; i--) {
            palette[i] = palette[i - 1];
        }
        palette[0] = last;
        updatePanels();
    }

    // Replace color at selected index
    private void replaceColor() {
        try {
            int index = Integer.parseInt(selectIndex.getSelectedItem().toString());
            String hex = txtHexColor.getText().trim();

            if (!hex.startsWith("#") || hex.length() != 7) {
                JOptionPane.showMessageDialog(this, "Please enter a valid HEX format (#RRGGBB).");
                return;
            }

            Color newColor = Color.decode(hex);
            palette[index] = newColor;
            updatePanels();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid color input!");
        }
    }

    // Update all color panels
    private void updatePanels() {
        for (int i = 0; i < 5; i++) {
            colorPanels[i].setBackground(palette[i]);
        }
    }

    public static void main(String[] args) {
        new ColorPaletteGenerator();
    }
}
