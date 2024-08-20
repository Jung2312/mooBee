package UI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;

import review.ReviewBean;
import review.ReviewMgr;
import temp.TempMgr;
import user.UserBean;
import user.UserMgr;

public class MyPage {

	private JFrame frame;
	private UserBean userbean;
	private UserMgr usermgr;
	private ReviewMgr rMgr;;
	private TempMgr tMgr;
	private static String userId;
	private Vector<ReviewBean> reviewlist;
	// Create the application.
	public MyPage(String userId) {
		this.userId = userId;
		initialize();
	}

	private void initialize() {
		usermgr = new UserMgr();
		userbean = new UserBean();
		userbean = usermgr.getUser(userId);
		rMgr = new ReviewMgr();
		reviewlist = rMgr.findMemberReview(userId);
		tMgr = new TempMgr();
		tMgr.deleteWarning(userId);
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

		JPanel MyPagePanel = new JPanel();
		MyPagePanel.setBounds(0, 0, 984, 661);
		frame.getContentPane().add(MyPagePanel);
		MyPagePanel.setLayout(null);

		JPanel MyInfoPanel = new JPanel();
		MyInfoPanel.setBounds(37, 105, 914, 485);
		MyPagePanel.add(MyInfoPanel);
		MyInfoPanel.setBackground(new Color(192, 192, 192));
		MyInfoPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("\"");
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 30));
		lblNewLabel.setBounds(44, 72, 36, 34);
		MyInfoPanel.add(lblNewLabel);

		JLabel welcome = new JLabel("님 환영합니다!");
		welcome.setFont(new Font("나눔고딕", Font.PLAIN, 30));
		welcome.setBounds(178, 75, 270, 34);
		MyInfoPanel.add(welcome);

		JLabel lblNewLabel_2 = new JLabel("\"");
		lblNewLabel_2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(156, 72, 28, 34);
		MyInfoPanel.add(lblNewLabel_2);

		JLabel name = new JLabel(userbean.getName());
		name.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 30));
		name.setBounds(58, 75, 97, 34);
		MyInfoPanel.add(name);

		JSeparator MyPage_sap = new JSeparator();
		MyPage_sap.setForeground(new Color(0, 0, 0));
		MyPage_sap.setBackground(new Color(0, 0, 0));
		MyPage_sap.setBounds(44, 121, 827, 2);
		MyInfoPanel.add(MyPage_sap);

		JButton ViewMembership_Btn = new ControlButton2("멤버십 혜택 보기");
		ViewMembership_Btn.setBounds(49, 136, 125, 23);
		MyInfoPanel.add(ViewMembership_Btn);

		ViewMembership_Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = ViewMembership_Btn.getLocationOnScreen();
				MembershipInfo MembershipWindow = new MembershipInfo();

				int parentX = frame.getX();
				int parentY = frame.getY();
				int parentWidth = frame.getWidth();
				int parentHeight = frame.getHeight();

				int windowWidth = MembershipWindow.getWidth();
				int windowHeight = MembershipWindow.getHeight();

				int x = parentX + (parentWidth - windowWidth) / 2;
				int y = parentY + (parentHeight - windowHeight) / 2;
				MembershipWindow.getFrame().setLocation(x, y);

				MembershipWindow.showWindow();
			}
		});

		JLabel Year_Cost = new JLabel("연간 누적 금액 :"+userbean.getPaymentAmount()+"원");
		Year_Cost.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Year_Cost.setBounds(127, 31, 270, 34);
		MyInfoPanel.add(Year_Cost);

		JLabel Current_Membership = new JLabel(userbean.getGrade());
		Current_Membership.setBounds(44, 28, 67, 34);
		MyInfoPanel.add(Current_Membership);
		Current_Membership.setHorizontalAlignment(SwingConstants.CENTER);
		Current_Membership.setFont(new Font("나눔고딕", Font.BOLD, 15));

		JPanel Membership_Background = new JPanel();
		Membership_Background.setBounds(44, 28, 67, 34);
		MyInfoPanel.add(Membership_Background);
		Membership_Background.setBackground(new Color(204, 204, 0));

		// 매너 온도 동그라미 패널 추가
		CirclePanel mannerCircle = new CirclePanel(70); // 동그라미의 크기와 색상을 지정
		mannerCircle.setBounds(711, 31, 74, 70); // 위치와 크기 설정
		MyInfoPanel.add(mannerCircle);
		mannerCircle.setLayout(null);
		
		float temp = userbean.getTemp();

		if(temp >= 36.5) {
			mannerCircle.paintColor(new Color(50, 205, 50));
		}else if(temp < 36.5 && temp >= 30.0) {
			mannerCircle.paintColor(new Color(102,102,153));
		}else if(temp < 30.0) {
			mannerCircle.paintColor(new Color(204,204,204));
		}
		
		JLabel MyMannerTemp = new JLabel(temp +"˚");
		MyMannerTemp.setBounds(10, 10, 55, 55);
		MyMannerTemp.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		MyMannerTemp.setHorizontalAlignment(SwingConstants.CENTER);
		mannerCircle.add(MyMannerTemp);

		
		JButton Degree_Btn = new ControlButton2("매너온도란?");
		Degree_Btn.setBounds(797, 10, 105, 23);
		MyInfoPanel.add(Degree_Btn);

		Degree_Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = Degree_Btn.getLocationOnScreen();
				MannerTemp MannerTempWindow = new MannerTemp();

				int parentX = frame.getX();
				int parentY = frame.getY();
				int parentWidth = frame.getWidth();
				int parentHeight = frame.getHeight();

				int windowWidth = MannerTempWindow.getWidth();
				int windowHeight = MannerTempWindow.getHeight();

				int x = parentX + (parentWidth - windowWidth) / 2;
				int y = parentY + (parentHeight - windowHeight) / 2;
				MannerTempWindow.getFrame().setLocation(x, y);

				MannerTempWindow.showWindow();
			}
		});

		JPanel MyReservation_Panel = new JPanel();
		MyReservation_Panel.setBounds(44, 181, 400, 271);
		MyInfoPanel.add(MyReservation_Panel);
		MyReservation_Panel.setLayout(null);

		JLabel MyReservation_Label = new JLabel("My 예매 내역");
		MyReservation_Label.setFont(new Font("나눔고딕", Font.BOLD, 25));
		MyReservation_Label.setBounds(12, 10, 193, 42);
		MyReservation_Panel.add(MyReservation_Label);

		JLabel MyReservMovie_Label1 = new JLabel("명탐정코난 - 100만달러의 펜타그램(더빙)");
		MyReservMovie_Label1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MyReservMovie_Label1.setBounds(12, 62, 357, 23);
		MyReservation_Panel.add(MyReservMovie_Label1);

		JLabel MyReservMovie_Label2 = new JLabel("파일럿");
		MyReservMovie_Label2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MyReservMovie_Label2.setBounds(12, 95, 357, 23);
		MyReservation_Panel.add(MyReservMovie_Label2);

		JLabel MyReservMovie_Label3 = new JLabel("데드풀과 울버린");
		MyReservMovie_Label3.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MyReservMovie_Label3.setBounds(12, 128, 357, 23);
		MyReservation_Panel.add(MyReservMovie_Label3);

		JLabel MyReservMovie_Label4 = new JLabel("인사이드 아웃2");
		MyReservMovie_Label4.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MyReservMovie_Label4.setBounds(12, 161, 357, 23);
		MyReservation_Panel.add(MyReservMovie_Label4);

		JLabel MyReservMovie_Label5 = new JLabel("사랑의 하츄핑");
		MyReservMovie_Label5.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		MyReservMovie_Label5.setBounds(12, 194, 357, 23);
		MyReservation_Panel.add(MyReservMovie_Label5);

		JButton GoMyTicket_Btn = new ControlButton2("예매 내역 보러가기");
		GoMyTicket_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyReservation mrv = new MyReservation(); // MyReservation 창 생성
				mrv.setPreviousPage(frame); // MyPage를 이전 페이지로 설정
				mrv.getFrame().setVisible(true); // MyReservation 창을 표시
				frame.dispose(); // 현재 MyPage 창을 닫음
			}
		});
		GoMyTicket_Btn.setBounds(251, 238, 137, 23);
		MyReservation_Panel.add(GoMyTicket_Btn);

		JPanel MyReview_Panel = new JPanel();
		MyReview_Panel.setBounds(454, 181, 400, 271);
		MyInfoPanel.add(MyReview_Panel);
		MyReview_Panel.setLayout(null);

		JLabel MyReview_Label = new JLabel("내가 작성한 리뷰");
		MyReview_Label.setFont(new Font("나눔고딕", Font.BOLD, 25));
		MyReview_Label.setBounds(12, 10, 310, 42);
		MyReview_Panel.add(MyReview_Label);

		int locationSize = 29;

		for (int i = 0; i < reviewlist.size(); i++) {
			if(i == 5) {
				break;
			}
			locationSize += 33;
			ReviewBean rBean = reviewlist.get(i);
			JLabel MyReviewMovie_Label = new JLabel(rBean.getTitle());
			MyReviewMovie_Label.setFont(new Font("나눔고딕", Font.PLAIN, 15));
			MyReviewMovie_Label.setBounds(12, locationSize, 341, 23);
			MyReview_Panel.add(MyReviewMovie_Label);
		}

		JButton GoMyReview_Btn = new ControlButton2("내 리뷰 보러가기");
		GoMyReview_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyReview mr = new MyReview(userId); // MyReservation 창 생성
				mr.setPreviousPage(frame); // MyPage를 이전 페이지로 설정
				mr.getFrame().setVisible(true); // MyReservation 창을 표시
				frame.dispose(); // 현재 MyPage 창을 닫음
			}
		});
		GoMyReview_Btn.setBounds(263, 238, 125, 23);
		MyReview_Panel.add(GoMyReview_Btn);

		GoMyReview_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyReview mr = new MyReview(userId);
				frame.setVisible(true);
				frame.dispose();
			}
		});

		JPopupMenu popupMenu = new JPopupMenu();

		 JButton MenuTab = new ControlButton("메뉴");
		MenuTab.setBounds(826, 36, 97, 34);
		MyPagePanel.add(MenuTab);

		MenuTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupMenu.show(MenuTab, MenuTab.getWidth(), MenuTab.getHeight());
			}
		});

		JMenuItem GoTicket = new JMenuItem("영화 예매");
		popupMenu.add(GoTicket);

		GoTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReservationForm(userId);
				frame.dispose();
			}
		});

		JMenuItem GoMyPage = new JMenuItem("마이페이지");
		popupMenu.add(GoMyPage);

		GoMyPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyPage mp = new MyPage(userId);
				frame.setVisible(true);
				frame.dispose();
			}
		});

		JMenuItem GoNotice = new JMenuItem("공지사항");
		popupMenu.add(GoNotice);

		JMenuItem Logout = new JMenuItem("로그아웃");
		popupMenu.add(Logout);

		JButton HomeButton = new ControlButton("홈");
		HomeButton.setBounds(61, 36, 97, 34);
		MyPagePanel.add(HomeButton);


		HomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainForm mf = new MainForm(userId);
				mf.setVisible(true);
				frame.dispose();
			}
		});

		JButton ModifyUserInfo_Btn = new ControlButton2("회원정보 수정");
		ModifyUserInfo_Btn.setBounds(826, 613, 125, 23);
		MyPagePanel.add(ModifyUserInfo_Btn);

		ModifyUserInfo_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = ModifyUserInfo_Btn.getLocationOnScreen();
				// ModifyUser 창을 열기
				ModifyUser modifyUserWindow = new ModifyUser();

				int parentX = frame.getX();
				int parentY = frame.getY();
				int parentWidth = frame.getWidth();
				int parentHeight = frame.getHeight();

				int windowWidth = modifyUserWindow.getWidth();
				int windowHeight = modifyUserWindow.getHeight();

				int x = parentX + (parentWidth - windowWidth) / 2;
				int y = parentY + (parentHeight - windowHeight) / 2;
				modifyUserWindow.getFrame().setLocation(x, y);

				modifyUserWindow.showWindow(); // 회원정보 수정 창 표시
			}
		});

		Logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(frame, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					// 로그아웃 처리
					frame.dispose(); // 현재 창을 닫고 로그아웃 처리 (예를 들어 로그인 화면으로 돌아가기)
				}
				// 아니오를 누르면 아무 일도 하지 않음 (팝업 창만 닫힘)
			}
		});

		frame.validate();
		frame.setVisible(true);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userId = "11";
					MyPage window = new MyPage(userId);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
