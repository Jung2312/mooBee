package UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MannerTemp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MannerTemp window = new MannerTemp();
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
	public MannerTemp() {
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
		frame.setBounds(100, 100, 410, 236);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel MannerInfo_Panel = new JPanel();
		MannerInfo_Panel.setBounds(0, 0, 394, 197);
		frame.getContentPane().add(MannerInfo_Panel);
		MannerInfo_Panel.setLayout(null);

		JButton Close_Btn = new JButton("닫기");
		Close_Btn.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		Close_Btn.setBounds(134, 148, 90, 33);
		MannerInfo_Panel.add(Close_Btn);

		JLabel Manner_Label1 = new JLabel("매너온도");
		Manner_Label1.setFont(new Font("나눔고딕", Font.BOLD, 30));
		Manner_Label1.setBounds(77, 21, 124, 53);
		MannerInfo_Panel.add(Manner_Label1);

		// 아이콘을 위한 JLabel 추가
		JLabel iconLabel = new JLabel();
		iconLabel.setBounds(25, 35, 40, 53); // 이미지의 위치와 크기 조정
		try {
			Image img = ImageIO.read(new File("./UI/images/thermometer.png"));
			img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // 아이콘 크기 조정
			iconLabel.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		MannerInfo_Panel.add(iconLabel);

		JLabel Manner_Label2 = new JLabel("누적 신고수 및 신고 항목에 따라 해당 고객의 ");
		Manner_Label2.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Manner_Label2.setBounds(77, 84, 362, 18);
		MannerInfo_Panel.add(Manner_Label2);

		JLabel Manner_Label2_1_1 = new JLabel("매너온도를 산정하는 시스템입니다.");
		Manner_Label2_1_1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Manner_Label2_1_1.setBounds(77, 112, 284, 26);
		MannerInfo_Panel.add(Manner_Label2_1_1);

		Close_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // 현재 창을 닫음
			}
		});
	}
}
