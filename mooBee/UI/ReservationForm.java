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
    private static int RSVdocid,RSVcinemaNum;
    private static String RSVDate;
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
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        JPanel movieSelectionPanel = new JPanel();
        movieSelectionPanel.setBackground(Color.LIGHT_GRAY);
        movieSelectionPanel.setLayout(new BorderLayout());
        Vector<ScreenBean> movieList = screenmgr.listScreenMovie();
        JList<ScreenBean> movieJList = new JList<>(movieList);
        movieJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieJList.setVisibleRowCount(10);
        JScrollPane listScroller = new JScrollPane(movieJList);
        movieSelectionPanel.add(listScroller, BorderLayout.CENTER);

        JLabel movieSelectionTitle = new JLabel("영화 선택", JLabel.CENTER);
        movieSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        movieSelectionTitle.setOpaque(true);
        movieSelectionTitle.setBackground(Color.GRAY);
        movieSelectionTitle.setForeground(Color.WHITE);
        movieSelectionPanel.add(movieSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(movieSelectionPanel, gbc);

        // 셀 렌더러 설정
        movieJList.setCellRenderer(new ScreenBeanCellRenderer());

        // 극장 선택 패널
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        JPanel theaterSelectionPanel = new JPanel();
        theaterSelectionPanel.setBackground(Color.LIGHT_GRAY);
        theaterSelectionPanel.setLayout(new BorderLayout());

       

        JLabel theaterSelectionTitle = new JLabel("극장 선택", JLabel.CENTER);
        theaterSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        theaterSelectionTitle.setOpaque(true);
        theaterSelectionTitle.setBackground(Color.GRAY);
        theaterSelectionTitle.setForeground(Color.WHITE);
        theaterSelectionPanel.add(theaterSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(theaterSelectionPanel, gbc);
       
        // 날짜 선택 패널
        gbc.gridx = 2;
        gbc.weightx = 0.2;  
        JPanel dateSelectionPanel = new JPanel();
        dateSelectionPanel.setBackground(Color.LIGHT_GRAY);
        dateSelectionPanel.setLayout(new BorderLayout());

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

        JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
        timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        timeSelectionTitle.setOpaque(true);
        timeSelectionTitle.setBackground(Color.GRAY);
        timeSelectionTitle.setForeground(Color.WHITE);
        timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
        centerPanel.add(timeSelectionPanel, gbc);
        
     // 영화 선택 리스너
        movieJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지

                ScreenBean selectedMovie = movieJList.getSelectedValue();
                int docid = selectedMovie.getDocid();

                Vector<ScreenBean> cinemaList = screenmgr.listScreenCinema(docid);
                JList<ScreenBean> cinemaJList = new JList<>(cinemaList);
                cinemaJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                cinemaJList.setVisibleRowCount(10);
                JScrollPane listScroller = new JScrollPane(cinemaJList);
                cinemaJList.setCellRenderer(new ScreenBeanCellRenderer());

                // 극장 선택 패널을 업데이트합니다.
                theaterSelectionPanel.removeAll();
                JLabel theaterSelectionTitle = new JLabel("극장 선택", JLabel.CENTER);
                theaterSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                theaterSelectionTitle.setOpaque(true);
                theaterSelectionTitle.setBackground(Color.GRAY);
                theaterSelectionTitle.setForeground(Color.WHITE);
                theaterSelectionPanel.add(theaterSelectionTitle, BorderLayout.NORTH);
                theaterSelectionPanel.add(listScroller, BorderLayout.CENTER);
                theaterSelectionPanel.revalidate();
                theaterSelectionPanel.repaint();

                // 극장 선택 리스너 추가
                cinemaJList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지

                        ScreenBean selectedCinema = cinemaJList.getSelectedValue();
                        int cinemaDocid = selectedCinema.getDocid();
                        String cinemaName = selectedCinema.getCinemaName();
                        Vector<ScreenBean> dateList = screenmgr.listScreenDate(cinemaDocid, cinemaName);

                        JList<ScreenBean> dateJList = new JList<>(dateList);
                        dateJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        dateJList.setVisibleRowCount(10);
                        JScrollPane dateScroller = new JScrollPane(dateJList);
                        dateJList.setCellRenderer(new ScreenBeanCellRenderer());
                        // 날짜 선택 패널을 업데이트합니다.
                        dateSelectionPanel.removeAll();
                        JLabel dateSelectionTitle = new JLabel("날짜 선택", JLabel.CENTER);
                        dateSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                        dateSelectionTitle.setOpaque(true);
                        dateSelectionTitle.setBackground(Color.GRAY);
                        dateSelectionTitle.setForeground(Color.WHITE);
                        dateSelectionPanel.add(dateSelectionTitle, BorderLayout.NORTH);
                        dateSelectionPanel.add(dateScroller, BorderLayout.CENTER);
                        dateSelectionPanel.revalidate();
                        dateSelectionPanel.repaint();

                        // 날짜 선택 리스너 추가
                        dateJList.addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지

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

                                // 시간 선택 패널을 업데이트합니다.
                                timeSelectionPanel.removeAll();
                                JLabel timeSelectionTitle = new JLabel("시간 선택", JLabel.CENTER);
                                timeSelectionTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                                timeSelectionTitle.setOpaque(true);
                                timeSelectionTitle.setBackground(Color.GRAY);
                                timeSelectionTitle.setForeground(Color.WHITE);
                                timeSelectionPanel.add(timeSelectionTitle, BorderLayout.NORTH);
                                timeSelectionPanel.add(timeScroller, BorderLayout.CENTER);
                                timeSelectionPanel.revalidate();
                                timeSelectionPanel.repaint();
                                
                                timeJList.addListSelectionListener(new ListSelectionListener() {
									@Override
									public void valueChanged(ListSelectionEvent e) {
										if (e.getValueIsAdjusting()) return; // 중복 이벤트 방지
										ScreenBean selectedTime = timeJList.getSelectedValue();
										RSVdocid = selectedTime.getDocid();
										RSVcinemaNum = selectedTime.getCinemaNum();
										RSVDate = selectedTime.getScreenDate()+selectedTime.getScreenTime();
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
            JToggleButton youthButton = new JToggleButton(i + "명");
            youthGroup.add(youthButton); 
            peopleSelectionPanel.add(youthButton);
        }

        JLabel adultLabel = new JLabel("성인", JLabel.CENTER);
        peopleSelectionPanel.add(adultLabel);

        // 성인 인원 선택 버튼들
        ButtonGroup adultGroup = new ButtonGroup(); 
        for (int i = 1; i <= 9; i++) {
            JToggleButton adultButton = new JToggleButton(i + "명");
            adultGroup.add(adultButton); 
            peopleSelectionPanel.add(adultButton);
        }

        bottomPanel.add(peopleSelectionPanel, BorderLayout.CENTER);

        JButton seatSelectionButton = new JButton("좌석 선택");
        seatSelectionButton.setPreferredSize(new Dimension(100, 80));
        bottomPanel.add(seatSelectionButton, BorderLayout.EAST);

        seatSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SeatSelectionForm(userId,RSVdocid,RSVcinemaNum,RSVDate); 
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
        new ReservationForm(userId);
    }
}