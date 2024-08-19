package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

public class MembershipInfo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MembershipInfo window = new MembershipInfo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MembershipInfo() {
		initialize();
	}

	public void showWindow() {
		frame.setVisible(true);
	}

	public int getWidth() {
		return frame.getWidth();
	}

	public int getHeight() {
		return frame.getHeight();
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 779, 489);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel Membership_Panel = new JPanel();
		Membership_Panel.setBounds(0, 0, 763, 450);
		frame.getContentPane().add(Membership_Panel);
		Membership_Panel.setLayout(null);

		JLabel MemberShip_Label1 = new JLabel("멤버십 산정 기준 및 혜택");
		MemberShip_Label1.setFont(new Font("나눔고딕", Font.PLAIN, 25));
		MemberShip_Label1.setBounds(56, 50, 324, 54);
		Membership_Panel.add(MemberShip_Label1);

		JSeparator Membership_sap1 = new JSeparator();
		Membership_sap1.setForeground(new Color(0, 0, 0));
		Membership_sap1.setBackground(new Color(0, 0, 0));
		Membership_sap1.setBounds(56, 114, 663, 2);
		Membership_Panel.add(Membership_sap1);

		JSeparator Membership_sap2 = new JSeparator();
		Membership_sap2.setBounds(56, 204, 663, 2);
		Membership_Panel.add(Membership_sap2);

		JButton Close_Btn = new JButton("닫기");
		Close_Btn.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		Close_Btn.setBounds(320, 364, 136, 54);
		Membership_Panel.add(Close_Btn);

		Close_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // 현재 창을 닫음
			}
		});

		JLabel Bronze_Label = new JLabel("BRONZE");
		Bronze_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Bronze_Label.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 50));
		Bronze_Label.setBackground(new Color(102, 51, 51));
		Bronze_Label.setBounds(56, 124, 206, 70);
		Membership_Panel.add(Bronze_Label);

		JLabel SILVER_Label = new JLabel("SILVER");
		SILVER_Label.setBackground(new Color(204, 204, 204));
		SILVER_Label.setHorizontalAlignment(SwingConstants.CENTER);
		SILVER_Label.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 50));
		SILVER_Label.setBounds(296, 126, 189, 70);
		Membership_Panel.add(SILVER_Label);

		JLabel GOLD_Label = new JLabel("GOLD");
		GOLD_Label.setBackground(new Color(204, 204, 0));
		GOLD_Label.setHorizontalAlignment(SwingConstants.CENTER);
		GOLD_Label.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 50));
		GOLD_Label.setBounds(520, 126, 189, 70);
		Membership_Panel.add(GOLD_Label);

		JPanel BRONZE_Background = new JPanel();
		BRONZE_Background.setBackground(new Color(153, 102, 0));
		BRONZE_Background.setBounds(56, 126, 206, 70);
		Membership_Panel.add(BRONZE_Background);

		JPanel SILVER_Background = new JPanel();
		SILVER_Background.setBackground(new Color(204, 204, 204));
		SILVER_Background.setBounds(296, 126, 189, 70);
		Membership_Panel.add(SILVER_Background);

		JPanel GOLD_Background = new JPanel();
		GOLD_Background.setBackground(new Color(204, 204, 0));
		GOLD_Background.setBounds(520, 126, 189, 70);
		Membership_Panel.add(GOLD_Background);

		JLabel Membership_Label2 = new JLabel("연간 누적 금액 10만원 이상");
		Membership_Label2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Membership_Label2.setBounds(66, 216, 180, 39);
		Membership_Panel.add(Membership_Label2);

		JLabel Membership_Label3 = new JLabel("연간 누적 금액 20만원 이상");
		Membership_Label3.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Membership_Label3.setBounds(296, 216, 180, 39);
		Membership_Panel.add(Membership_Label3);

		JLabel Membership_Label4 = new JLabel("연간 누적 금액 30만원 이상");
		Membership_Label4.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Membership_Label4.setBounds(520, 216, 180, 39);
		Membership_Panel.add(Membership_Label4);

		JLabel Discount_Label1 = new JLabel("5% 할인 혜택");
		Discount_Label1.setFont(new Font("나눔고딕", Font.BOLD, 30));
		Discount_Label1.setBounds(57, 265, 189, 54);
		Membership_Panel.add(Discount_Label1);

		JLabel Discount_Label2 = new JLabel("10% 할인 혜택");
		Discount_Label2.setFont(new Font("나눔고딕", Font.BOLD, 30));
		Discount_Label2.setBounds(296, 265, 206, 54);
		Membership_Panel.add(Discount_Label2);

		JLabel Discount_Label3 = new JLabel("20% 할인 혜택");
		Discount_Label3.setFont(new Font("나눔고딕", Font.BOLD, 30));
		Discount_Label3.setBounds(520, 265, 206, 54);
		Membership_Panel.add(Discount_Label3);
	}
}
