package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import movie.MovieBean;
import reservation.ReservationBean;
import reservation.ReservationMgr;

public class MyReservation {

	private JFrame frame;
	private JFrame previousPage;
	private ReservationMgr reservMgr;
	private static String userId;
	private Vector<ReservationBean> reservationList;

	/**
	 * Create the application.
	 */
	public MyReservation(String userId) {
		this.userId = userId;
		initialize();
	}

	public void setPreviousPage(JFrame previousPage) {
		this.previousPage = previousPage; // 이전 페이지 설정
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    reservMgr = new ReservationMgr();
	    reservationList = reservMgr.distinctRSVNUserId(userId);

	    frame = new JFrame();
	    frame.setBounds(100, 100, 1000, 700);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(null);

	    // 화면 중앙에 창을 위치시키기
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screenSize.width - frame.getWidth()) / 2;
	    int y = (screenSize.height - frame.getHeight()) / 2;
	    frame.setLocation(x, y);

	    JPanel MyReservation_Panel = new JPanel();
	    MyReservation_Panel.setLayout(null);

	    JScrollPane scrollPane = new JScrollPane(MyReservation_Panel);
	    scrollPane.setBounds(0, 0, 984, 661);
	    frame.getContentPane().add(scrollPane);

	    // 이전 페이지 버튼
	    JButton backButton = new ControlButton2("이전 페이지");
	    backButton.setBounds(856, 25, 97, 23);
	    MyReservation_Panel.add(backButton);

	    backButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (previousPage != null) {
	                previousPage.setVisible(true); // 이전 페이지 창을 다시 표시
	                frame.dispose(); // 현재 MyReservation 창을 닫음
	            }
	        }
	    });

	    // 예매 불러오기
	    JLabel MyReservationTitle_Label = new JLabel("My 예매 내역");
	    MyReservationTitle_Label.setFont(new Font("나눔고딕", Font.BOLD, 30));
	    MyReservationTitle_Label.setBounds(40, 25, 192, 41);
	    MyReservation_Panel.add(MyReservationTitle_Label);

	    int panelY = 100; // 패널의 초기 Y 위치
	    
	    for (ReservationBean rBean : reservationList) {
	        JPanel MyReservationMovie_Panel = new JPanel();
	        MyReservationMovie_Panel.setBackground(new Color(192, 192, 192));
	        MyReservationMovie_Panel.setBounds(108, panelY, 763, 226);
	        MyReservation_Panel.add(MyReservationMovie_Panel);
	        MyReservationMovie_Panel.setLayout(null);

	        JSeparator separator = new JSeparator();
	        separator.setForeground(Color.BLACK);
	        separator.setBackground(Color.BLACK);
	        separator.setBounds(0, 164, 763, 2);
	        MyReservationMovie_Panel.add(separator);

	        JLabel ReservationNumber = new JLabel(Integer.toString(rBean.getRSVNNum()));
	        ReservationNumber.setHorizontalAlignment(SwingConstants.CENTER);
	        ReservationNumber.setBounds(12, 10, 101, 144);
	        MyReservationMovie_Panel.add(ReservationNumber);
	        MovieBean movieBean = reservMgr.getMovieData(rBean.getDocid());
	        
	        // 영화
	        JLabel MoviePoster = new JLabel();
	        MoviePoster.setHorizontalAlignment(SwingConstants.CENTER);
	        MoviePoster.setBounds(130, 10, 115, 144);
	        MyReservationMovie_Panel.add(MoviePoster);
	        ImageIcon imgIcon;
	        try {
	            imgIcon = new ImageIcon(new URL(movieBean.getPosterUrl()));
	            Image img = imgIcon.getImage();  
	            Image scaledImg = img.getScaledInstance(250, 350, Image.SCALE_SMOOTH); 
	            MoviePoster.setIcon(new ImageIcon(scaledImg, BorderLayout.NORTH)); 
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
	        
	        JLabel ViewingMovie = new JLabel("관람 영화:  ");
	        ViewingMovie.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        ViewingMovie.setBounds(257, 10, 82, 27);
	        MyReservationMovie_Panel.add(ViewingMovie);
	        
	        JLabel MovieTitle = new JLabel(movieBean.getTitle());
	        MovieTitle.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        MovieTitle.setBounds(351, 10, 400, 27);
	        MyReservationMovie_Panel.add(MovieTitle);

	        // 극장(시네마 테이블 빈 가져오기) 
	        JLabel ViewingLocation = new JLabel("관람 극장: ");
	        ViewingLocation.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        ViewingLocation.setBounds(257, 40, 82, 27);
	        MyReservationMovie_Panel.add(ViewingLocation);
	        
	        JLabel TheaterLocation = new JLabel(reservMgr.getCinema(rBean.getCinemaNum()));
	        TheaterLocation.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        TheaterLocation.setBounds(351, 40, 400, 27);
	        MyReservationMovie_Panel.add(TheaterLocation);

	        // 일시
	        JLabel ViewingDate = new JLabel("관람 일시: ");
	        ViewingDate.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        ViewingDate.setBounds(257, 70, 82, 27);
	        MyReservationMovie_Panel.add(ViewingDate);
	        
	        JLabel Date = new JLabel(rBean.getViewDate());
	        Date.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        Date.setBounds(351, 70, 400, 27);
	        MyReservationMovie_Panel.add(Date);

	        // 좌석(좌석 테이블에서 값 가져오기)
	        JLabel Seat = new JLabel("관람 좌석: ");
	        Seat.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        Seat.setBounds(257, 130, 82, 27);
	        MyReservationMovie_Panel.add(Seat);

	        JLabel SeatNumber = new JLabel(reservMgr.getSeatNum(userId, rBean.getRSVNNum()).toString());
	        SeatNumber.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        SeatNumber.setBounds(351, 130, 400, 27);
	        MyReservationMovie_Panel.add(SeatNumber);
	
	        // 인원
	        JLabel ViewingPeople = new JLabel("관람 인원: ");
	        ViewingPeople.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        ViewingPeople.setBounds(257, 100, 82, 27);
	        MyReservationMovie_Panel.add(ViewingPeople);
	        
	        int sum = reservMgr.getSumUser(rBean.getCinemaNum(), rBean.getViewDate(), rBean.getDocid(), userId);
	        JLabel Personnel = new JLabel(Integer.toString(sum));
	        Personnel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        Personnel.setBounds(351, 100, 400, 27);
	        MyReservationMovie_Panel.add(Personnel);

	        // 결제 금액
	        JLabel PayAmount = new JLabel("결제 금액");
	        PayAmount.setFont(new Font("나눔고딕", Font.PLAIN, 20));
	        PayAmount.setBounds(23, 176, 89, 40);
	        MyReservationMovie_Panel.add(PayAmount);

	        JLabel PayNumber = new JLabel(Integer.toString(rBean.getPrice())+"원");
	        PayNumber.setFont(new Font("나눔고딕", Font.PLAIN, 20));
	        PayNumber.setBounds(158, 176, 262, 40);
	        MyReservationMovie_Panel.add(PayNumber);

	        
	        JButton Report_Btn = new ReportButton("비매너 신고");
	        Report_Btn.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        Report_Btn.setBounds(640, 171, 111, 45);
	        MyReservationMovie_Panel.add(Report_Btn);

	        Report_Btn.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                ReportForm ReportFormWindow = new ReportForm(rBean.getCinemaNum(), rBean.getViewDate(), rBean.getDocid());
	                ReportFormWindow.showWindow();
	            }
	        });

	        JButton CancelTicket_Btn = new ControlButton("예매 취소");
	        CancelTicket_Btn.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	        CancelTicket_Btn.setBounds(530, 171, 97, 45);

	        CancelTicket_Btn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int confirm = JOptionPane.showConfirmDialog(frame, "영화를 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	                if (confirm == JOptionPane.YES_OPTION) {
	                	reservMgr.deleteRsvn(rBean.getRSVNNum());
				reservMgr.cancleSeat(rBean.getSeatId());
	                    // 데이터와 화면 새로 고침
	                    reservationList = reservMgr.distinctRSVNUserId(userId);
	                    frame.getContentPane().removeAll(); // 기존 컴포넌트 제거
	                    initialize(); // 화면 재초기화
	                    frame.revalidate(); // 변경 사항 적용
	                    frame.repaint(); // 화면 갱신
	                    JOptionPane.showMessageDialog(frame, "영화가 취소되었습니다.", "취소 완료", JOptionPane.INFORMATION_MESSAGE);
	                }
	            }
	        });
	        MyReservationMovie_Panel.add(CancelTicket_Btn);

	        // 패널의 다음 위치를 계산
	        panelY += 250; // 패널 높이 + 여백
	    }

	    MyReservation_Panel.setPreferredSize(new java.awt.Dimension(984, panelY + 60)); // 여백 추가

	    // Refresh scrollPane view
	    scrollPane.revalidate();
	    scrollPane.repaint();

	}
  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyReservation window = new MyReservation(userId);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
