package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieListForm extends JFrame {

    public MovieListForm() {
 
        setTitle("현재 상영작");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(1000, 1300)); // 스크롤을 위해 패널 크기 설정

        JButton homeButton = new JButton("홈");
        homeButton.setBounds(30, 20, 100, 40);
        mainPanel.add(homeButton);

        JButton menuButton = new JButton("메뉴");
        menuButton.setBounds(870, 20, 100, 40);
        mainPanel.add(menuButton);

        JLabel titleLabel = new JLabel("현재 상영작", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBounds(400, 20, 200, 40);
        mainPanel.add(titleLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(250, 80, 450, 40);
        mainPanel.add(searchField);

        JButton searchButton = new JButton("검색");
        searchButton.setBounds(700, 80, 60, 40);
        mainPanel.add(searchButton);

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(4, 4, 20, 20)); // 4행 4열로 설정
        moviePanel.setBounds(100, 150, 800, 1050);
        mainPanel.add(moviePanel);

        for (int i = 0; i < 16; i++) {
            JPanel posterPanel = new JPanel();
            posterPanel.setBackground(Color.LIGHT_GRAY);
            posterPanel.setPreferredSize(new Dimension(180, 300)); 
            moviePanel.add(posterPanel);
        }

        // 스크롤 패널 설정
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(scrollPane);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm(); 
                dispose(); 
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MovieListForm();
    }
}
