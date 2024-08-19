package UI;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        super();
    }

    public BackgroundPanel(Image backgroundImage) {
        super();
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 이미지의 원본 크기 가져오기
            int imageWidth = backgroundImage.getWidth(this);
            int imageHeight = backgroundImage.getHeight(this);

            // 패널의 크기 가져오기
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // 이미지의 위치 계산 (가운데에 위치시키기)
            int x = (panelWidth - imageWidth) / 2;
            int y = (panelHeight - imageHeight) / 2;

            // 이미지 그리기
            g.drawImage(backgroundImage, x, y, this);
        }
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        repaint();
    }
}


