package UI;

import javax.swing.*;
import java.awt.*;

public class HoneyButton2 extends JButton {

    public HoneyButton2(String text) {
        super(text);

        setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                g.setColor(new Color(255, 180, 0)); // 버튼 눌렸을 때 색상
                g.fillRoundRect(1, 1, b.getWidth() - 2, b.getHeight() - 2, 20, 20);
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 그라데이션 색상 설정
                GradientPaint gp;
                if (b.getModel().isPressed()) {
                    gp = new GradientPaint(0, 0, new Color(255, 180, 0), 0, b.getHeight(), new Color(255, 140, 0));
                } else if (b.getModel().isRollover()) {
                    gp = new GradientPaint(0, 0, new Color(255, 220, 0), 0, b.getHeight(), new Color(255, 165, 0));
                } else {
                    gp = new GradientPaint(0, 0, new Color(255, 200, 0), 0, b.getHeight(), new Color(255, 150, 0));
                }

                // 그라데이션 적용 및 버튼 배경 그리기
                g2.setPaint(gp);
                g2.fillRoundRect(1, 1, b.getWidth() - 2, b.getHeight() - 2, 20, 20);

                // 버튼 테두리
                g2.setColor(Color.BLACK);  
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(1, 1, b.getWidth() - 2, b.getHeight() - 2, 20, 20);

                // 텍스트 중앙 정렬
                g2.setColor(b.getForeground());
                g2.setFont(b.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (b.getWidth() - fm.stringWidth(b.getText())) / 2;
                int y = (b.getHeight() + fm.getAscent()) / 2 - fm.getDescent();
                g2.drawString(b.getText(), x, y);
            }
        });

        setForeground(Color.BLACK);  // 텍스트 색상
        setFont(new Font("맑은 고딕", Font.BOLD, 12)); 
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
}
