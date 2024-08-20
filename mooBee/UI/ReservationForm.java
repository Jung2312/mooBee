package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import screen.ScreenBean;
import screen.ScreenBeanCellRenderer;
import screen.ScreenMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ReservationForm extends JFrame {
    private static String userId;
    private ScreenBean screenbean;
    private ScreenMgr screenmgr;
    private static int RSVdocid, RSVcinemaNum;
    private static String ViewDate;
    // 청소년 및 성인 인원 수 저장을 위한 변수
    private int YouthCount = 0;
    private int AdultCount = 0;

    private boolean isMovieSelected = false;
    private boolean isCinemaSelected = false;
    private boolean isDateSelected = false;
    private boolean isTimeSelected = false;

    private JPanel theaterSelectionPanel;
    private JPanel dateSelectionPanel;
    private JPanel timeSelectionPanel;

    public ReservationForm(String userId) {
        this.userId = userId;
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

        JButton homeButton = new ControlButton("홈");
        topPanel.add(homeButton, BorderLayout.WEST);

        JButton backButton = new ControlButton("이전 페이지");
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
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        JPanel movieSelectionPanel = new JPanel();
        movieSelectionPanel.setBackground(Color.LIGHT_GRAY);
        movieSelectionPanel.setLayout(new BorderLayout());
        movieSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        Vector<ScreenBean> movieList = screenmgr.listScreenMovie();
        JList<ScreenBean> movieJList = new JList<>(movieList);
        movieJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieJList.setVisibleRowCount(10);
        JScrollPane listScroller = new JScrollPane(movieJList);
        movieSelectionPanel.add(listScroller, BorderLayout.CENTER);

        JLabel movieSelectionTitle = new JLabel("영화 선택", JLabel.CENTER);
        movieSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        movieSelectionTitle.setOpaque(true);
        movieSelectionTitle.setBackground(new Color(255, 200, 0));
        movieSelectionTitle.setForeground(Color.BLACK);
        movieSelectionPanel.add(movieSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(movieSelectionPanel, gbc);

        // 셀 렌더러 설정
        movieJList.setCellRenderer(new ScreenBeanCellRenderer());

        // 극장 선택 패널
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        theaterSelectionPanel = new JPanel();
        theaterSelectionPanel.setBackground(Color.LIGHT_GRAY);
        theaterSelectionPanel.setLayout(new BorderLayout());
        theaterSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

        JLabel theaterSelectionTitle = new JLabel("극장 선택", JLabel.CENTER);
        theaterSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        theaterSelectionTitle.setOpaque(true);
        theaterSelectionTitle.setBackground(new Color(255, 200, 0));
        theaterSelectionTitle.setForeground(Color.BLACK);
        theaterSelectionPanel.add(theaterSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(theaterSelectionPanel, gbc);

        // 날짜 선택 패널
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        dateSelectionPanel = new JPanel();
        dateSelectionPanel.setBackground(Color.LIGHT_GRAY);
        dateSelectionPanel.setLayout(new BorderLayout());
        dateSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel dateSelectionTitle = new JLabel("날짜 선택", JLabel.CENTER);
        dateSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        dateSelectionTitle.setOpaque(true);
        dateSelectionTitle.setBackground(new Color(255, 200, 0));
        dateSelectionTitle.setForeground(Color.BLACK);
        dateSelectionPanel.add(dateSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(dateSelectionPanel, gbc);

        // 시간 선택 패널
        gbc.gridx = 3;
        gbc.weightx = 0.4;
        timeSelectionPanel = new JPanel();
        timeSelectionPanel.setBackground(Color.LIGHT_GRAY);
        timeSelectionPanel.setLayout(new BorderLayout());
        timeSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

        JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
        timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        timeSelectionTitle.setOpaque(true);
        timeSelectionTitle.setBackground(new Color(255, 200, 0));
        timeSelectionTitle.setForeground(Color.BLACK);
        timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(timeSelectionPanel, gbc);

        // 영화 선택 리스너
        movieJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지
                isMovieSelected = true; // 영화 체크 여부
                if (movieJList.getSelectedValue() == null) isMovieSelected = false;

                // 극장 선택 패널 초기화
                theaterSelectionPanel.removeAll();
                JLabel theaterSelectionTitle = new JLabel("극장 선택", JLabel.CENTER);
                theaterSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                theaterSelectionTitle.setOpaque(true);
                theaterSelectionTitle.setBackground(new Color(255, 200, 0));
                theaterSelectionTitle.setForeground(Color.BLACK);
                theaterSelectionPanel.add(theaterSelectionTitle, BorderLayout.NORTH);
                theaterSelectionPanel.revalidate();
                theaterSelectionPanel.repaint();

                // 날짜 선택 패널 초기화
                dateSelectionPanel.removeAll();
                JLabel dateSelectionTitle = new JLabel("날짜 선택", JLabel.CENTER);
                dateSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                dateSelectionTitle.setOpaque(true);
                dateSelectionTitle.setBackground(new Color(255, 200, 0));
                dateSelectionTitle.setForeground(Color.BLACK);
                dateSelectionPanel.add(dateSelectionTitle, BorderLayout.NORTH);
                dateSelectionPanel.revalidate();
                dateSelectionPanel.repaint();

                // 시간 선택 패널 초기화
                timeSelectionPanel.removeAll();
                JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
                timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                timeSelectionTitle.setOpaque(true);
                timeSelectionTitle.setBackground(new Color(255, 200, 0));
                timeSelectionTitle.setForeground(Color.BLACK);
                timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
                timeSelectionPanel.revalidate();
                timeSelectionPanel.repaint();

                // 극장 선택 리스트 갱신
                ScreenBean selectedMovie = movieJList.getSelectedValue();
                int docid = selectedMovie.getDocid();
                Vector<ScreenBean> cinemaList = screenmgr.listScreenCinema(docid);
                JList<ScreenBean> cinemaJList = new JList<>(cinemaList);
                cinemaJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                cinemaJList.setVisibleRowCount(10);
                JScrollPane listScroller = new JScrollPane(cinemaJList);
                cinemaJList.setCellRenderer(new ScreenBeanCellRenderer());

                theaterSelectionPanel.add(listScroller, BorderLayout.CENTER);
                theaterSelectionPanel.revalidate();
                theaterSelectionPanel.repaint();

                // 극장 선택 리스너 추가
                cinemaJList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지
                        isCinemaSelected = true; // 극장 체크 여부
                        if (cinemaJList.getSelectedValue() == null) isCinemaSelected = false;

                        // 날짜 선택 패널 초기화
                        dateSelectionPanel.removeAll();
                        JLabel dateSelectionTitle = new JLabel("날짜 선택", JLabel.CENTER);
                        dateSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                        dateSelectionTitle.setOpaque(true);
                        dateSelectionTitle.setBackground(new Color(255, 200, 0));
                        dateSelectionTitle.setForeground(Color.BLACK);
                        dateSelectionPanel.add(dateSelectionTitle, BorderLayout.NORTH);
                        dateSelectionPanel.revalidate();
                        dateSelectionPanel.repaint();

                        // 시간 선택 패널 초기화
                        timeSelectionPanel.removeAll();
                        JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
                        timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                        timeSelectionTitle.setOpaque(true);
                        timeSelectionTitle.setBackground(new Color(255, 200, 0));
                        timeSelectionTitle.setForeground(Color.BLACK);
                        timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
                        timeSelectionPanel.revalidate();
                        timeSelectionPanel.repaint();

                        // 날짜 선택 리스트 갱신
                        ScreenBean selectedCinema = cinemaJList.getSelectedValue();
                        int cinemaDocid = selectedCinema.getDocid();
                        String cinemaName = selectedCinema.getCinemaName();
                        Vector<ScreenBean> dateList = screenmgr.listScreenDate(cinemaDocid, cinemaName);

                        JList<ScreenBean> dateJList = new JList<>(dateList);
                        dateJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        dateJList.setVisibleRowCount(10);
                        JScrollPane dateScroller = new JScrollPane(dateJList);
                        dateJList.setCellRenderer(new ScreenBeanCellRenderer());

                        dateSelectionPanel.add(dateScroller, BorderLayout.CENTER);
                        dateSelectionPanel.revalidate();
                        dateSelectionPanel.repaint();

                        // 날짜 선택 리스너 추가
                        dateJList.addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지
                                isDateSelected = true; // 날짜 체크 여부
                                if (dateJList.getSelectedValue() == null) isDateSelected = false;

                                // 시간 선택 패널 초기화
                                timeSelectionPanel.removeAll();
                                JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
                                timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                                timeSelectionTitle.setOpaque(true);
                                timeSelectionTitle.setBackground(new Color(255, 200, 0));
                                timeSelectionTitle.setForeground(Color.BLACK);
                                timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
                                timeSelectionPanel.revalidate();
                                timeSelectionPanel.repaint();

                                // 시간 선택 리스트 갱신
                                ScreenBean selectedDate = dateJList.getSelectedValue();
                                int dateDocid = selectedDate.getDocid();
                                String cinemaName = selectedDate.getCinemaName();
                                String screenDate = selectedDate.getScreenDate();
                                Vector<ScreenBean> timeList = screenmgr.listScreenTime(dateDocid, cinemaName, screenDate);

                                JList<ScreenBean> timeJList = new JList<>(timeList);
                                timeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                                timeJList.setVisibleRowCount(10);
                                JScrollPane timeScroller = new JScrollPane(timeJList);
                                timeJList.setCellRenderer(new ScreenBeanCellRenderer());

                                timeSelectionPanel.add(timeScroller, BorderLayout.CENTER);
                                timeSelectionPanel.revalidate();
                                timeSelectionPanel.repaint();

                                // 시간 선택 리스너 추가
                                timeJList.addListSelectionListener(new ListSelectionListener() {
                                    @Override
                                    public void valueChanged(ListSelectionEvent e) {
                                        if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지
                                        isTimeSelected = true; // 시간 체크 여부
                                        if (timeJList.getSelectedValue() == null) isTimeSelected = false;
                                        ScreenBean selectedTime = timeJList.getSelectedValue();
                                        RSVdocid = selectedTime.getDocid();
                                        RSVcinemaNum = selectedTime.getCinemaNum();
                                        ViewDate = selectedTime.getScreenDate() + " " + selectedTime.getScreenTime();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel peopleSelectionPanel = new JPanel();
        peopleSelectionPanel.setLayout(new GridLayout(2, 10, 10, 10));

        JLabel youthLabel = new JLabel("청소년", JLabel.CENTER);
        peopleSelectionPanel.add(youthLabel);

        // 청소년 인원 선택 버튼들
        ButtonGroup youthGroup = new ButtonGroup();
        for (int i = 1; i <= 9; i++) {
            final int index = i;
            JToggleButton youthButton = new HoneyToggleButton(i + "명");
            youthGroup.add(youthButton);
            youthButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (youthButton.isSelected()) {
                        YouthCount = index;
                        System.out.println(YouthCount);
                    } else {
                        YouthCount = 0;
                    }
                }
            });
            peopleSelectionPanel.add(youthButton);
        }

        JLabel adultLabel = new JLabel("성인", JLabel.CENTER);
        peopleSelectionPanel.add(adultLabel);

        // 성인 인원 선택 버튼들
        ButtonGroup adultGroup = new ButtonGroup();
        for (int i = 1; i <= 9; i++) {
            final int index = i;
            JToggleButton adultButton = new HoneyToggleButton(i + "명");
            adultGroup.add(adultButton);
            adultButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (adultButton.isSelected()) {
                        AdultCount = index;
                    } else {
                        AdultCount = 0;
                    }
                }
            });
            peopleSelectionPanel.add(adultButton);
        }

        bottomPanel.add(peopleSelectionPanel, BorderLayout.CENTER);

        JButton seatSelectionButton = new HoneyButton("좌석 선택");
        seatSelectionButton.setPreferredSize(new Dimension(100, 80));
        bottomPanel.add(seatSelectionButton, BorderLayout.EAST);

        seatSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder message = new StringBuilder();

                if (!isMovieSelected || !isCinemaSelected || !isDateSelected || !isTimeSelected)
                    message.append("선택하지 않은 항목이 있습니다.");

                if (message.length() > 0) {
                    JOptionPane.showMessageDialog(null, message.toString(), "경고", JOptionPane.WARNING_MESSAGE);
                } else if (YouthCount == 0 && AdultCount == 0) {
                    JOptionPane.showMessageDialog(null, "청소년 및 성인 수를 선택해 주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                } else {
                    new SeatSelectionForm(userId, RSVdocid, RSVcinemaNum, ViewDate, YouthCount, AdultCount);
                    dispose();
                }
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
        new ReservationForm(userId);
    }
}
