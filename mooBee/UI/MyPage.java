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

		JButton MenuTab = new JButton("메뉴");
		MenuTab.setBounds(839, 36, 97, 34);
		MyPagePanel.add(MenuTab);
		MenuTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorderPainted(false);
		addPopup(MenuTab, popupMenu);

		JButton GoTicket = new JButton("  영화 예매 ");
		GoTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		popupMenu.add(GoTicket);

		JButton GoMyPage = new JButton("마이페이지");
		popupMenu.add(GoMyPage);

		JButton GoNotice = new JButton("  공지사항  ");
		popupMenu.add(GoNotice);

		JButton Logout = new JButton("  로그아웃  ");
		popupMenu.add(Logout);

		JButton HomeButton = new JButton("홈");
		HomeButton.setBounds(61, 36, 97, 34);
		MyPagePanel.add(HomeButton);

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
