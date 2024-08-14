package UI;

import javax.swing.*;
import screen.ScreenBean;
import screen.ScreenMgr;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationForm extends JFrame {
    private static String userId;
    private ScreenBean screenbean;
    private ScreenMgr screenmgr;

    private JToggleButton selectedYouthButton = null; // 청소년 그룹에서 선택된 버튼
    private JToggleButton selectedAdultButton = null; // 어른 그룹에서 선택된 버튼

    public ReservationForm() {
        screenbean = new ScreenBean();
        screenmgr = new ScreenMgr();
        setTitle("영화 예매 창");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        JButton homeButton = new JButton("홈");
        topPanel.add(homeButton, BorderLayout.WEST);

        JButton backButton = new JButton("이전 페이지");
        topPanel.add(backButton, BorderLayout.EAST);

        // 중앙 패널: 영화 선택, 극장 선택, 날짜 선택, 시간 선택 패널을 포함
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 40));
        add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 영화 선택 패널
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        JPanel movieSelectionPanel = new JPanel();
        movieSelectionPanel.setBackground(Color.LIGHT_GRAY);
        movieSelectionPanel.setLayout(new BorderLayout());

        JLabel movieListLabel = new JLabel("상영 영화 리스트", JLabel.CENTER);
        movieListLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        movieListLabel.setOpaque(true);
        movieListLabel.setBackground(Color.WHITE);
        movieListLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        movieSelectionPanel.add(movieListLabel, BorderLayout.CENTER);

        JLabel movieSelectionTitle = new JLabel("영화 선택", JLabel.CENTER);
        movieSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        movieSelectionTitle.setOpaque(true);
        movieSelectionTitle.setBackground(Color.GRAY);
        movieSelectionTitle.setForeground(Color.WHITE);
        movieSelectionPanel.add(movieSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(movieSelectionPanel, gbc);

        // 극장 선택 패널
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        JPanel theaterSelectionPanel = new JPanel();
        theaterSelectionPanel.setBackground(Color.LIGHT_GRAY);
        theaterSelectionPanel.setLayout(new BorderLayout());

        JLabel theaterListLabel = new JLabel("극장 리스트", JLabel.CENTER);
        theaterListLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        theaterListLabel.setOpaque(true);
        theaterListLabel.setBackground(Color.WHITE);
        theaterListLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        theaterSelectionPanel.add(theaterListLabel, BorderLayout.CENTER);

        JLabel theaterSelectionTitle = new JLabel("극장 선택", JLabel.CENTER);
        theaterSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        theaterSelectionTitle.setOpaque(true);
        theaterSelectionTitle.setBackground(Color.GRAY);
        theaterSelectionTitle.setForeground(Color.WHITE);
        theaterSelectionPanel.add(theaterSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(theaterSelectionPanel, gbc);

        // 날짜 선택 패널
        gbc.gridx = 2;
        gbc.weightx = 0;
        JPanel dateSelectionPanel = new JPanel();
        dateSelectionPanel.setBackground(Color.LIGHT_GRAY);
        dateSelectionPanel.setLayout(new BorderLayout());

        JLabel dateListLabel = new JLabel("상영 날짜 리스트", JLabel.CENTER);
        dateListLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        dateListLabel.setOpaque(true);
        dateListLabel.setBackground(Color.WHITE);
        dateListLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dateSelectionPanel.add(dateListLabel, BorderLayout.CENTER);

        JLabel dateSelectionTitle = new JLabel("날짜 선택", JLabel.CENTER);
        dateSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        dateSelectionTitle.setOpaque(true);
        dateSelectionTitle.setBackground(Color.GRAY);
        dateSelectionTitle.setForeground(Color.WHITE);
        dateSelectionPanel.add(dateSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(dateSelectionPanel, gbc);

        // 시간 선택 패널
        gbc.gridx = 3;
        gbc.weightx = 0.4;
        JPanel timeSelectionPanel = new JPanel();
        timeSelectionPanel.setBackground(Color.LIGHT_GRAY);
        timeSelectionPanel.setLayout(new BorderLayout());

        JLabel timeListLabel = new JLabel("상영 시간 리스트", JLabel.CENTER);
        timeListLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        timeListLabel.setOpaque(true);
        timeListLabel.setBackground(Color.WHITE);
        timeListLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        timeSelectionPanel.add(timeListLabel, BorderLayout.CENTER);

        JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
        timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        timeSelectionTitle.setOpaque(true);
        timeSelectionTitle.setBackground(Color.GRAY);
        timeSelectionTitle.setForeground(Color.WHITE);
        timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(timeSelectionPanel, gbc);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel peopleSelectionPanel = new JPanel();
        peopleSelectionPanel.setLayout(new GridLayout(2, 10, 10, 10));

        JLabel youthLabel = new JLabel("청소년", JLabel.CENTER);
        peopleSelectionPanel.add(youthLabel);

        // 청소년 인원 선택 버튼들
        for (int i = 1; i <= 9; i++) {
            JToggleButton youthButton = new JToggleButton(i + "명");
            peopleSelectionPanel.add(youthButton);

            youthButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedYouthButton != null && selectedYouthButton != youthButton) {
                        selectedYouthButton.setSelected(false);
                    }
                    selectedYouthButton = youthButton.isSelected() ? youthButton : null;
                }
            });
        }

        JLabel adultLabel = new JLabel("성인", JLabel.CENTER);
        peopleSelectionPanel.add(adultLabel);

        // 성인 인원 선택 버튼들
        for (int i = 1; i <= 9; i++) {
            JToggleButton adultButton = new JToggleButton(i + "명");
            peopleSelectionPanel.add(adultButton);

            adultButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedAdultButton != null && selectedAdultButton != adultButton) {
                        selectedAdultButton.setSelected(false);
                    }
                    selectedAdultButton = adultButton.isSelected() ? adultButton : null;
                }
            });
        }

        bottomPanel.add(peopleSelectionPanel, BorderLayout.CENTER);

        JButton seatSelectionButton = new JButton("좌석 선택");
        seatSelectionButton.setPreferredSize(new Dimension(100, 80));
        bottomPanel.add(seatSelectionButton, BorderLayout.EAST);

        seatSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SeatSelectionForm();
                dispose();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm(userId);
                dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm(userId);
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReservationForm();
    }
}
