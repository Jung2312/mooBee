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
import java.util.Vector;

import javax.swing.JSeparator;

import review.ReviewBean;
import review.ReviewMgr;

import java.awt.Color;
import java.awt.Dimension;

public class MyReview {

	private JFrame frame;
	private JFrame previousPage;
	private static String userId;
	private ReviewMgr rMgr;
	private Vector<ReviewBean> reviewList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyReview window = new MyReview(userId);
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
	public MyReview(String userId) {
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

		rMgr = new ReviewMgr();
		reviewList = rMgr.findMemberReview(userId);
		for (int i = 0; i < reviewList.size(); i++) {
			ReviewBean rBean = reviewList.get(i);
		}

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		JButton backButton = new ControlButton2("이전 페이지");
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

		reviewList = rMgr.findMemberReview("11");

		int size1 = 123;
		int size2 = 171;
		int size3 = 209;
		int size4 = 214;
		int size5 = 253;

		for (int i = 0; i < reviewList.size(); i++) {
			
			if(i == 3) {
				break;
			}
			
			ReviewBean rBean = reviewList.get(i);
			JLabel MovieTitle_Label1 = new JLabel(rBean.getTitle());
			MovieTitle_Label1.setFont(new Font("나눔고딕", Font.BOLD, 20));
			MovieTitle_Label1.setBounds(56, size1, 866, 41);
			panel.add(MovieTitle_Label1);

			JLabel MyReview_Label1 = new JLabel(rBean.getContent());
			MyReview_Label1.setFont(new Font("나눔고딕", Font.BOLD, 15));
			MyReview_Label1.setBounds(56, size2, 866, 28);
			panel.add(MyReview_Label1);

			JLabel ReviewDate_Label1 = new JLabel(rBean.getReviewDate());
			ReviewDate_Label1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			ReviewDate_Label1.setBounds(699, size3, 117, 34);
			panel.add(ReviewDate_Label1);

			JLabel Recommend_Label1 = new JLabel("추천 "+rBean.getRecommend()+"개");
			Recommend_Label1.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			Recommend_Label1.setBounds(832, size4, 90, 24);
			panel.add(Recommend_Label1);

			JSeparator separator1 = new JSeparator();
			separator1.setForeground(new Color(192, 192, 192));
			separator1.setBackground(new Color(192, 192, 192));
			separator1.setBounds(56, size5, 866, 2);
			panel.add(separator1);
			size1 += 142;
			size2 += 142;
			size3 += 142;
			size4 += 142;
			size5 += 142;
		}


	}
}
