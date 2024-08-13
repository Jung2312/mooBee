package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MyReservation {

	private JFrame frame;
	private JFrame previousPage;

	/**
	 * Create the application.
	 */
	public MyReservation() {
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
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 화면 중앙에 창을 위치시키기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

		// Create the main panel that will hold all content
		JPanel MyReservation_Panel = new JPanel();
		MyReservation_Panel.setLayout(null);

		// Create a JScrollPane that wraps the main panel
		JScrollPane scrollPane = new JScrollPane(MyReservation_Panel);
		scrollPane.setBounds(0, 0, 984, 661);
		frame.getContentPane().add(scrollPane);

		// 이전 페이지 버튼
		JButton backButton = new JButton("이전 페이지");
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

		JLabel MyReservationTitle_Label = new JLabel("My 예매 내역");
		MyReservationTitle_Label.setFont(new Font("나눔고딕", Font.BOLD, 30));
		MyReservationTitle_Label.setBounds(40, 25, 192, 41);
		MyReservation_Panel.add(MyReservationTitle_Label);

		// First reservation panel
		JPanel MyReservationMovie_Panel1 = new JPanel();
		MyReservationMovie_Panel1.setBackground(new Color(192, 192, 192));
		MyReservationMovie_Panel1.setBounds(108, 118, 763, 226);
		MyReservation_Panel.add(MyReservationMovie_Panel1);
		MyReservationMovie_Panel1.setLayout(null);

		JSeparator separator1 = new JSeparator();
		separator1.setForeground(new Color(0, 0, 0));
		separator1.setBackground(new Color(0, 0, 0));
		separator1.setBounds(0, 164, 763, 2);
		MyReservationMovie_Panel1.add(separator1);

		JLabel ReservationNumber1 = new JLabel("KGE789SF");
		ReservationNumber1.setHorizontalAlignment(SwingConstants.CENTER);
		ReservationNumber1.setBounds(12, 10, 101, 144);
		MyReservationMovie_Panel1.add(ReservationNumber1);

		JLabel MoviePoster1 = new JLabel("영화 포스터");
		MoviePoster1.setHorizontalAlignment(SwingConstants.CENTER);
		MoviePoster1.setBounds(130, 10, 115, 144);
		MyReservationMovie_Panel1.add(MoviePoster1);

		JLabel MovieTitle1 = new JLabel("명탐정코난 - 100만달러의 펜타그램(더빙)");
		MovieTitle1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MovieTitle1.setBounds(351, 10, 400, 27);
		MyReservationMovie_Panel1.add(MovieTitle1);

		JLabel TheaterLocation1 = new JLabel("CGV 서면 상상마당 2관");
		TheaterLocation1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		TheaterLocation1.setBounds(351, 40, 400, 27);
		MyReservationMovie_Panel1.add(TheaterLocation1);

		JLabel Date1 = new JLabel("2024.08.13");
		Date1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Date1.setBounds(351, 70, 400, 27);
		MyReservationMovie_Panel1.add(Date1);

		JLabel Personnel1 = new JLabel("2");
		Personnel1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Personnel1.setBounds(351, 100, 400, 27);
		MyReservationMovie_Panel1.add(Personnel1);

		JLabel ViewingMovie1 = new JLabel("관람 영화:  ");
		ViewingMovie1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingMovie1.setBounds(257, 10, 82, 27);
		MyReservationMovie_Panel1.add(ViewingMovie1);

		JLabel ViewingLocation1 = new JLabel("관람 극장: ");
		ViewingLocation1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingLocation1.setBounds(257, 40, 82, 27);
		MyReservationMovie_Panel1.add(ViewingLocation1);

		JLabel ViewingDate1 = new JLabel("관람 일시: ");
		ViewingDate1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingDate1.setBounds(257, 70, 82, 27);
		MyReservationMovie_Panel1.add(ViewingDate1);

		JLabel ViewingPeople1 = new JLabel("관람 인원: ");
		ViewingPeople1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingPeople1.setBounds(257, 100, 82, 27);
		MyReservationMovie_Panel1.add(ViewingPeople1);

		JLabel Seat1 = new JLabel("관람 좌석: ");
		Seat1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Seat1.setBounds(257, 130, 82, 27);
		MyReservationMovie_Panel1.add(Seat1);

		JLabel SeatNumber1 = new JLabel("H13, H14");
		SeatNumber1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		SeatNumber1.setBounds(351, 130, 400, 27);
		MyReservationMovie_Panel1.add(SeatNumber1);

		JLabel PayAmount1 = new JLabel("결제 금액");
		PayAmount1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		PayAmount1.setBounds(23, 176, 89, 40);
		MyReservationMovie_Panel1.add(PayAmount1);

		JLabel PayNumber1 = new JLabel("30,000원");
		PayNumber1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		PayNumber1.setBounds(158, 176, 262, 40);
		MyReservationMovie_Panel1.add(PayNumber1);

		JButton Report_Btn1 = new JButton("비매너 신고");
		Report_Btn1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Report_Btn1.setBounds(640, 171, 111, 45);
		MyReservationMovie_Panel1.add(Report_Btn1);

		Report_Btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = Report_Btn1.getLocationOnScreen();
				ReportForm ReportFormWindow = new ReportForm();
				ReportFormWindow.showWindow();
			}
		});

		JButton CancelTicket_Btn1 = new JButton("예매 취소");
		CancelTicket_Btn1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		CancelTicket_Btn1.setBounds(530, 171, 97, 45);
		
		CancelTicket_Btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(frame, "영화를 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				// 확인 다이얼로그에서 '예'를 선택했을 경우
				if (confirm == JOptionPane.YES_OPTION) {
					// 신고 처리 로직 추가 (여기서는 예시로 메시지 표시)
					JOptionPane.showMessageDialog(frame, "영화가 취소되었습니다.", "취소 완료", JOptionPane.INFORMATION_MESSAGE);
				}	
			}
		});
		MyReservationMovie_Panel1.add(CancelTicket_Btn1);

		// Second reservation panel
		JPanel MyReservationMovie_Panel2 = new JPanel();
		MyReservationMovie_Panel2.setLayout(null);
		MyReservationMovie_Panel2.setBackground(Color.LIGHT_GRAY);
		MyReservationMovie_Panel2.setBounds(108, 373, 763, 226);
		MyReservation_Panel.add(MyReservationMovie_Panel2);

		JSeparator separator2 = new JSeparator();
		separator2.setForeground(Color.BLACK);
		separator2.setBackground(Color.BLACK);
		separator2.setBounds(0, 164, 763, 2);
		MyReservationMovie_Panel2.add(separator2);

		JLabel ReservationNumber2 = new JLabel("EGA153BK");
		ReservationNumber2.setHorizontalAlignment(SwingConstants.CENTER);
		ReservationNumber2.setBounds(12, 10, 101, 144);
		MyReservationMovie_Panel2.add(ReservationNumber2);

		JLabel MoviePoster2 = new JLabel("영화 포스터");
		MoviePoster2.setHorizontalAlignment(SwingConstants.CENTER);
		MoviePoster2.setBounds(130, 10, 115, 144);
		MyReservationMovie_Panel2.add(MoviePoster2);

		JLabel MovieTitle2 = new JLabel("범죄도시 4");
		MovieTitle2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MovieTitle2.setBounds(351, 10, 400, 27);
		MyReservationMovie_Panel2.add(MovieTitle2);

		JLabel TheaterLocation2 = new JLabel("CGV 서면 상상마당 1관 ");
		TheaterLocation2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		TheaterLocation2.setBounds(351, 40, 400, 27);
		MyReservationMovie_Panel2.add(TheaterLocation2);

		JLabel Date2 = new JLabel("2024.08.15");
		Date2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Date2.setBounds(351, 70, 400, 27);
		MyReservationMovie_Panel2.add(Date2);

		JLabel Personnel2 = new JLabel("1");
		Personnel2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Personnel2.setBounds(351, 100, 400, 27);
		MyReservationMovie_Panel2.add(Personnel2);

		JLabel ViewingMovie2 = new JLabel("관람 영화:  ");
		ViewingMovie2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingMovie2.setBounds(257, 10, 82, 27);
		MyReservationMovie_Panel2.add(ViewingMovie2);

		JLabel ViewingLocation2 = new JLabel("관람 극장: ");
		ViewingLocation2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingLocation2.setBounds(257, 40, 82, 27);
		MyReservationMovie_Panel2.add(ViewingLocation2);

		JLabel ViewingDate2 = new JLabel("관람 일시: ");
		ViewingDate2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingDate2.setBounds(257, 70, 82, 27);
		MyReservationMovie_Panel2.add(ViewingDate2);

		JLabel ViewingPeople2 = new JLabel("관람 인원: ");
		ViewingPeople2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ViewingPeople2.setBounds(257, 100, 82, 27);
		MyReservationMovie_Panel2.add(ViewingPeople2);

		JLabel Seat2 = new JLabel("관람 좌석: ");
		Seat2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Seat2.setBounds(257, 130, 82, 27);
		MyReservationMovie_Panel2.add(Seat2);

		JLabel SeatNumber2 = new JLabel("D16");
		SeatNumber2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		SeatNumber2.setBounds(351, 130, 400, 27);
		MyReservationMovie_Panel2.add(SeatNumber2);

		JLabel PayAmount2 = new JLabel("결제 금액");
		PayAmount2.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		PayAmount2.setBounds(23, 176, 89, 40);
		MyReservationMovie_Panel2.add(PayAmount2);

		JLabel PayNumber2 = new JLabel("14,000원");
		PayNumber2.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		PayNumber2.setBounds(158, 176, 262, 40);
		MyReservationMovie_Panel2.add(PayNumber2);

		JButton Report_Btn2 = new JButton("비매너 신고");
		Report_Btn2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Report_Btn2.setBounds(640, 171, 111, 45);
		MyReservationMovie_Panel2.add(Report_Btn2);

		Report_Btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = Report_Btn2.getLocationOnScreen();
				ReportForm ReportFormWindow = new ReportForm();
				ReportFormWindow.showWindow();
			}
		});

		JButton CancelTicket_Btn2 = new JButton("예매 취소");
		CancelTicket_Btn2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		CancelTicket_Btn2.setBounds(530, 171, 97, 45);
		
		CancelTicket_Btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(frame, "영화를 취소하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				// 확인 다이얼로그에서 '예'를 선택했을 경우
				if (confirm == JOptionPane.YES_OPTION) {
					// 신고 처리 로직 추가 (여기서는 예시로 메시지 표시)
					JOptionPane.showMessageDialog(frame, "영화가 취소되었습니다.", "취소 완료", JOptionPane.INFORMATION_MESSAGE);
				}	
			}
		});
		MyReservationMovie_Panel2.add(CancelTicket_Btn2);

		// Ensure the MyReservation_Panel is large enough to contain all components
		MyReservation_Panel.setPreferredSize(new java.awt.Dimension(984, 700));

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
					MyReservation window = new MyReservation();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
