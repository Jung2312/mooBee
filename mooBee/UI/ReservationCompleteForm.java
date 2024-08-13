package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationCompleteForm extends JFrame {

    public ReservationCompleteForm() {
      
        setTitle("예매 상세 정보");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        JLabel completionLabel = new JLabel("예매가 완료되었습니다.", JLabel.CENTER);
        completionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        completionLabel.setBounds(350, 50, 300, 30);
        add(completionLabel);

        JPanel posterPanel = new JPanel();
        posterPanel.setBackground(Color.LIGHT_GRAY);
        posterPanel.setBounds(150, 150, 300, 400);
        JLabel posterLabel = new JLabel("영화 포스터", JLabel.CENTER);
        posterLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        posterPanel.add(posterLabel);
        add(posterPanel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 1, 10, 10));
        infoPanel.setBounds(500, 150, 400, 400);
        add(infoPanel);

        String[] infoLabels = {"예매번호", "영화 제목", "극장", "일시", "인원", "좌석", "결제 금액"};
        for (String label : infoLabels) {
            JLabel infoLabel = new JLabel(label + " : ");
            infoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            infoPanel.add(infoLabel);
        }

        JButton confirmButton = new JButton("확인");
        confirmButton.setBounds(800, 600, 100, 30);
        add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm();  
                dispose();      
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReservationCompleteForm();
    }
}
