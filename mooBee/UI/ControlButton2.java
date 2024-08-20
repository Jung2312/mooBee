package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlButton2 extends JButton {

    private Color defaultBackground;
    private Color hoverBackground;
    private Color pressedBackground;

    public ControlButton2(String text) {
        super(text);
        initializeButton();
    }

    private void initializeButton() {
        // 기본 배경색
        defaultBackground = new Color(30, 30, 30, 150);
        // 마우스를 올렸을 때 배경색
        hoverBackground = new Color(100, 100, 100, 180);
        // 눌렀을 때 배경색
        pressedBackground = new Color(50, 50, 50, 200);

        setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                g.setColor(pressedBackground); // 눌렸을 때 배경색
                g.fillRoundRect(1, 1, b.getWidth() - 2, b.getHeight() - 2, 20, 20);
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 현재 배경색 설정
                if (b.getModel().isPressed()) {
                    g2.setColor(pressedBackground);
                } else if (b.getModel().isRollover()) {
                    g2.setColor(hoverBackground);
                } else {
                    g2.setColor(defaultBackground);
                }

                g2.fillRoundRect(1, 1, b.getWidth() - 2, b.getHeight() - 2, 20, 20);

                // 글로시 효과
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255, 100), 0, b.getHeight() / 2, new Color(0, 0, 0, 0));
                g2.setPaint(gp);
                g2.fillRoundRect(1, 1, b.getWidth() - 2, b.getHeight() / 2, 20, 20);

                // 버튼 테두리
                g2.setColor(new Color(80, 80, 80, 200));
                g2.setStroke(new BasicStroke(2));
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

        setForeground(Color.WHITE);  // 텍스트 색상
        setFont(new Font("맑은 고딕", Font.BOLD, 12));  // "맑은 고딕" 폰트 사용
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        // 마우스 이벤트 추가
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBackground);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(defaultBackground);
            }
        });
    }
}
