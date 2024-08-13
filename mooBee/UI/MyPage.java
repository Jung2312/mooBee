package UI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class MyPage {

	private JFrame frame;

	// Create the application.
	public MyPage() {
		initialize();
	}

	// Initialize the contents of the frame.
	private void initialize() {
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

		JLabel name = new JLabel("홍길동");
		name.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 30));
		name.setBounds(58, 75, 97, 34);
		MyInfoPanel.add(name);

		JSeparator MyPage_sap = new JSeparator();
		MyPage_sap.setForeground(new Color(0, 0, 0));
		MyPage_sap.setBackground(new Color(0, 0, 0));
		MyPage_sap.setBounds(44, 121, 827, 2);
		MyInfoPanel.add(MyPage_sap);

		JButton ViewMembership = new JButton("멤버십 혜택 보기");
		ViewMembership.setBounds(49, 136, 125, 23);
		MyInfoPanel.add(ViewMembership);

		JLabel lblNewLabel_1 = new JLabel("연간 누적 금액 100,000원");
		lblNewLabel_1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(127, 31, 270, 34);
		MyInfoPanel.add(lblNewLabel_1);

		JEditorPane Membership_Background = new JEditorPane();
		Membership_Background.setBounds(44, 28, 67, 34);
		MyInfoPanel.add(Membership_Background);
		Membership_Background.setBackground(new Color(204, 204, 0));

		JLabel Current_Membership = new JLabel("GOLD");
		Current_Membership.setBounds(44, 28, 67, 34);
		MyInfoPanel.add(Current_Membership);
		Current_Membership.setHorizontalAlignment(SwingConstants.CENTER);
		Current_Membership.setFont(new Font("나눔고딕", Font.BOLD, 15));

		ViewMembership.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = ViewMembership.getLocationOnScreen();
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

		JPopupMenu popupMenu = new JPopupMenu();

		JButton MenuTab = new JButton("메뉴");
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

		JMenuItem GoMyPage = new JMenuItem("마이페이지");
		popupMenu.add(GoMyPage);

		GoMyPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyPage mp = new MyPage();
				frame.setVisible(true);
				frame.dispose();
			}
		});

		JMenuItem GoNotice = new JMenuItem("공지사항");
		popupMenu.add(GoNotice);

		JMenuItem Logout = new JMenuItem("로그아웃");
		popupMenu.add(Logout);

		JButton HomeButton = new JButton("홈");
		HomeButton.setBounds(61, 36, 97, 34);
		MyPagePanel.add(HomeButton);

		HomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainForm mf = new MainForm();
				mf.setVisible(true);
				frame.dispose();
			}
		});

		JButton ModifyUserInfo = new JButton("회원정보 수정");
		ModifyUserInfo.setBounds(826, 613, 125, 23);
		MyPagePanel.add(ModifyUserInfo);

		ModifyUserInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Point buttonLocation = ModifyUserInfo.getLocationOnScreen();
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

		HomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					MyPage window = new MyPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
