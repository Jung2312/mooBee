package UI;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class CirclePanel extends JPanel {

    private int diameter;
    private Color circleColor;

    public CirclePanel(int diameter) {
        this.diameter = diameter;
        this.setOpaque(false); // 배경을 투명하게 설정
    }
    
    public void paintColor(Color circleColor) {
        this.circleColor = circleColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(circleColor);
        g.fillOval(0, 0, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter, diameter);
    }
}
