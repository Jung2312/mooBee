package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;

public class MyReview {

	private JFrame frame;
	private JFrame previousPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyReview window = new MyReview();
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
	public MyReview() {
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// 화면 중앙에 창을 위치시키기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 984, 661);
		frame.getContentPane().add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		JButton backButton = new JButton("이전 페이지");
		backButton.setBounds(856, 25, 97, 23);
		panel.add(backButton);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (previousPage != null) {
					previousPage.setVisible(true); // 이전 페이지 창을 다시 표시
					frame.dispose(); // 현재 MyReservation 창을 닫음
				}
			}
		});

		JLabel MyReviewTitle_Label = new JLabel("내가 작성한 리뷰");
		MyReviewTitle_Label.setFont(new Font("나눔고딕", Font.BOLD, 30));
		MyReviewTitle_Label.setBounds(40, 60, 230, 41);
		panel.add(MyReviewTitle_Label);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(40, 111, 895, 2);
		panel.add(separator);

		JSeparator separator1 = new JSeparator();
		separator1.setForeground(new Color(192, 192, 192));
		separator1.setBackground(new Color(192, 192, 192));
		separator1.setBounds(56, 253, 866, 2);
		panel.add(separator1);

		JLabel MovieTitle_Label1 = new JLabel("명탐정코난 - 100만달러의 펜타그램(더빙)");
		MovieTitle_Label1.setFont(new Font("나눔고딕", Font.BOLD, 20));
		MovieTitle_Label1.setBounds(56, 123, 866, 41);
		panel.add(MovieTitle_Label1);

		JLabel ReviewDate_Label1 = new JLabel("2024.07.25");
		ReviewDate_Label1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		ReviewDate_Label1.setBounds(699, 209, 117, 34);
		panel.add(ReviewDate_Label1);

		JLabel Recommend_Label1 = new JLabel("추천 15개");
		Recommend_Label1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		Recommend_Label1.setBounds(832, 214, 90, 24);
		panel.add(Recommend_Label1);

		JLabel MyReview_Label1 = new JLabel("역시 이번에도 기대를 저버리지 않는 판타지 영화. 개인적으로 작년에 했던 흑철의 어영이 더 재밌음 ");
		MyReview_Label1.setFont(new Font("나눔고딕", Font.BOLD, 15));
		MyReview_Label1.setBounds(56, 171, 866, 28);
		panel.add(MyReview_Label1);

		JSeparator separator2 = new JSeparator();
		separator2.setForeground(Color.LIGHT_GRAY);
		separator2.setBackground(Color.LIGHT_GRAY);
		separator2.setBounds(56, 395, 866, 2);
		panel.add(separator2);

		JLabel MovieTitle_Label1_1 = new JLabel("데드풀과 울버린");
		MovieTitle_Label1_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
		MovieTitle_Label1_1.setBounds(56, 265, 866, 41);
		panel.add(MovieTitle_Label1_1);

		JLabel MyReview_Label1_1 = new JLabel("마블이 돌아왔다!");
		MyReview_Label1_1.setFont(new Font("나눔고딕", Font.BOLD, 15));
		MyReview_Label1_1.setBounds(56, 313, 866, 28);
		panel.add(MyReview_Label1_1);

		JLabel ReviewDate_Label1_1 = new JLabel("2024.08.13");
		ReviewDate_Label1_1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		ReviewDate_Label1_1.setBounds(699, 351, 117, 34);
		panel.add(ReviewDate_Label1_1);

		JLabel Recommend_Label1_1 = new JLabel("추천 8개");
		Recommend_Label1_1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		Recommend_Label1_1.setBounds(832, 356, 90, 24);
		panel.add(Recommend_Label1_1);
	}
}
