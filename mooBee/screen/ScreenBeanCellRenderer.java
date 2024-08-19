package screen;

import javax.swing.*;
import java.awt.*;

public class ScreenBeanCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel(); // GridLayout을 사용하여 라벨을 4열로 배치
        panel.setOpaque(true);

        if (value instanceof ScreenBean) {
            ScreenBean screenBean = (ScreenBean) value;

            // 영화 제목 표시
            JLabel titleLabel = new JLabel(screenBean.getTitle());
            titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
            titleLabel.setOpaque(true);
            panel.add(titleLabel); // 왼쪽에 제목 표시

            // 영화관 이름 표시
            JLabel cinemaLabel = new JLabel(screenBean.getCinemaName());
            cinemaLabel.setHorizontalAlignment(SwingConstants.LEFT);
            cinemaLabel.setOpaque(true);
            panel.add(cinemaLabel); // 중앙에 영화관 이름 표시
            
            // 날짜 표시
            JLabel dateLabel = new JLabel(screenBean.getScreenDate());
            dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
            dateLabel.setOpaque(true);
            panel.add(dateLabel); // 오른쪽에 날짜 표시
            
            // 시간 표시
            JLabel timeLabel = new JLabel(screenBean.getScreenTime());
            timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
            timeLabel.setOpaque(true);
            panel.add(timeLabel); // 가장 오른쪽에 시간 표시

            // 선택된 항목의 색상을 설정
            if (isSelected) {
                panel.setBackground(Color.LIGHT_GRAY);
                titleLabel.setBackground(Color.LIGHT_GRAY);
                cinemaLabel.setBackground(Color.LIGHT_GRAY);
                dateLabel.setBackground(Color.LIGHT_GRAY);
                timeLabel.setBackground(Color.LIGHT_GRAY);
            } else {
                panel.setBackground(Color.WHITE);
                titleLabel.setBackground(Color.WHITE);
                cinemaLabel.setBackground(Color.WHITE);
                dateLabel.setBackground(Color.WHITE);
                timeLabel.setBackground(Color.WHITE);
            }
        }

        return panel;
    }
}
