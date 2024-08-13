package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatSelectionForm extends JFrame {

    public SeatSelectionForm() {
        setTitle("좌석 선택 화면");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton homeButton = new JButton("홈");
        homeButton.setBounds(30, 30, 97, 34);
        add(homeButton);

        JButton backButton = new JButton("이전 페이지");
       backButton.setBounds(870, 30, 97, 34);
        add(backButton);

        JLabel screenLabel = new JLabel("SCREEN", JLabel.CENTER);
        screenLabel.setBounds(150, 50, 700, 30);
        screenLabel.setOpaque(true);
        screenLabel.setBackground(Color.LIGHT_GRAY);
        add(screenLabel);

        // 좌석 구역을 나누는 경계선 패널 
        JPanel[] borders = new JPanel[4];
        Color[] borderColors = {Color.RED, Color.BLUE, new Color(128, 0, 128), Color.GREEN};

        borders[0] = new JPanel();
        borders[0].setBounds(150, 100, 350, 250);  // 상단 왼쪽
        borders[0].setBorder(BorderFactory.createLineBorder(borderColors[0], 3));
        borders[0].setOpaque(false);
        add(borders[0]);

        borders[1] = new JPanel();
        borders[1].setBounds(500, 100, 350, 250);  // 상단 오른쪽
        borders[1].setBorder(BorderFactory.createLineBorder(borderColors[1], 3));
        borders[1].setOpaque(false);
        add(borders[1]);

        borders[2] = new JPanel();
        borders[2].setBounds(150, 350, 350, 250);  // 하단 왼쪽
        borders[2].setBorder(BorderFactory.createLineBorder(borderColors[2], 3));
        borders[2].setOpaque(false);
        add(borders[2]);

        borders[3] = new JPanel();
        borders[3].setBounds(500, 350, 350, 250);  // 하단 오른쪽
        borders[3].setBorder(BorderFactory.createLineBorder(borderColors[3], 3));
        borders[3].setOpaque(false);
        add(borders[3]);

        
        // 좌석 패널 설정 - 경계선 위에 위치하도록 함
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(8, 10, 8, 8)); 
        seatPanel.setBounds(160, 110, 680, 480);  
        seatPanel.setBackground(Color.WHITE);
        add(seatPanel);

        // 좌석을 위한 토글 버튼 배열 생성
        JToggleButton[][] seats = new JToggleButton[8][10];

        // 좌석 생성 및 패널에 추가
        Dimension buttonSize = new Dimension(55, 40);  // 버튼 크기 설정
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                String seatLabel = String.format("%c%02d", 'A' + i, j + 1);
                seats[i][j] = new JToggleButton(seatLabel);
                seats[i][j].setPreferredSize(buttonSize);  // 크기 설정
                seats[i][j].setBackground(Color.DARK_GRAY);
                seats[i][j].setForeground(Color.WHITE);
                seats[i][j].setFont(new Font("Arial", Font.BOLD, 12));
                seatPanel.add(seats[i][j]);
            }
        }

        // 금색 좌석 설정 (D04, D05, D06, D07, E04, E05, E06, E07)
        int[][] goldSeats = {
            {3, 3}, {3, 4}, {3, 5}, {3, 6},
            {4, 3}, {4, 4}, {4, 5}, {4, 6}
        };
        for (int[] seat : goldSeats) {
            seats[seat[0]][seat[1]].setBackground(new Color(218, 165, 32)); // 금색
        }

        // 좌석 미리보기 버튼들
        JButton previewButton1 = new JButton("좌석 미리보기");
        previewButton1.setBounds(20, 180, 120, 30);
        add(previewButton1);

        JButton previewButton2 = new JButton("좌석 미리보기");
        previewButton2.setBounds(20, 450, 120, 30);
        add(previewButton2);

        JButton previewButton3 = new JButton("좌석 미리보기");
        previewButton3.setBounds(860, 180, 120, 30);
        add(previewButton3);

        JButton previewButton4 = new JButton("좌석 미리보기");
        previewButton4.setBounds(860, 450, 120, 30);
        add(previewButton4);

        JButton payButton = new JButton("결제하기");
        payButton.setBounds(860, 550, 100, 50);
        add(payButton);

        homeButton.addActionListener(e -> {
            new MainForm(); 
            dispose(); 
        });

        backButton.addActionListener(e -> {
            new ReservationForm(); 
            dispose(); 
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new SeatSelectionForm();
    }
}
