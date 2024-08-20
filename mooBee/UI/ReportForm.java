package UI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JTextField;

import seat.SeatBean;
import seat.SeatMgr;
import temp.TempMgr;

import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;

public class ReportForm {

	private JFrame frame;
	private JTextField ReaportSeat_textField;
	private static int cinemaNum;
	private static String date;
	private static int docid;
	private SeatMgr seatMgr;
	private TempMgr tempMgr;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportForm window = new ReportForm(cinemaNum, date, docid);
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
	public ReportForm(int cinemaNum, String date, int docid) {
		this.cinemaNum = cinemaNum;
		this.date = date;
		this.docid = docid;
		initialize();
	}

	public void showWindow() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 333, 341);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 화면 중앙에 창을 위치시키기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 317, 302);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JComboBox ReportReason_ComboBox = new JComboBox();
		ReportReason_ComboBox.setModel(
				new DefaultComboBoxModel(new String[] { "소음으로 인한 관람 방해", "핸드폰 불빛으로 인한 관람 방해", "앞 좌석 가격으로 인한 관람 방해" }));
		ReportReason_ComboBox.setMaximumRowCount(3);
		ReportReason_ComboBox.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ReportReason_ComboBox.setBounds(28, 54, 253, 35);
		panel.add(ReportReason_ComboBox);

		JLabel ReportReason_Label = new JLabel("신고 사유");
		ReportReason_Label.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ReportReason_Label.setBounds(28, 28, 164, 26);
		panel.add(ReportReason_Label);

		ReportReason_ComboBox.setMaximumRowCount(3);
		JLabel ReportSeat_Label = new JLabel("신고 좌석");
		ReportSeat_Label.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		ReportSeat_Label.setBounds(28, 122, 164, 26);
		panel.add(ReportSeat_Label);
		
		seatMgr = new SeatMgr();
		JComboBox<String> ReportSeat_ComboBox = new JComboBox<>();

		// 2. 좌석 리스트 가져오기
		Vector<SeatBean> seatList = seatMgr.listSeat(cinemaNum);

		// 3. JComboBox에 데이터 추가
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (SeatBean seat : seatList) {
		    model.addElement(seat.getSeatNum()); // 좌석 번호를 ComboBox 항목으로 추가
		}
		ReportSeat_ComboBox.setModel(model);
		ReportSeat_ComboBox.setBounds(28, 146, 253, 35);
		ReportSeat_ComboBox.setFont(new Font("나눔고딕", Font.PLAIN, 12));
		panel.add(ReportSeat_ComboBox);

		JCheckBox chckbxNewCheckBox = new JCheckBox("허위 신고시 부당한 처벌을 받으실 수 있습니다.");
		chckbxNewCheckBox.setFont(new Font("나눔고딕", Font.PLAIN, 12));
		chckbxNewCheckBox.setBounds(28, 198, 257, 23);
		panel.add(chckbxNewCheckBox);

		JButton Cancel_Btn = new JButton("취소");
		Cancel_Btn.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		Cancel_Btn.setBounds(28, 233, 119, 35);
		panel.add(Cancel_Btn);

		Cancel_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // 현재 창을 닫음
			}
		});

		JButton btnNewButton_1 = new JButton("신고하기");
		btnNewButton_1.setFont(new Font("나눔고딕", Font.BOLD, 15));
		btnNewButton_1.setBounds(162, 233, 119, 35);
		panel.add(btnNewButton_1);

		// 신고하기 버튼의 액션 리스너 추가
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 좌석 필드가 비어있다면 경고 메시지 표시
				String item = (String) ReportReason_ComboBox.getSelectedItem();
				String seat = (String) ReportSeat_ComboBox.getSelectedItem();
				
				if (seat.equals("")) {
					JOptionPane.showMessageDialog(frame, "신고할 좌석을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				// 체크박스가 체크되지 않았으면 경고 메시지 표시
				else if (!chckbxNewCheckBox.isSelected()) {
					JOptionPane.showMessageDialog(frame, "주의사항에 동의해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
				} else {
					// 체크박스가 체크되어 있으면 확인 다이얼로그 표시
					int confirm = JOptionPane.showConfirmDialog(frame, "정말 신고하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					// 확인 다이얼로그에서 '예'를 선택했을 경우
					if (confirm == JOptionPane.YES_OPTION) {
						// 신고 처리 로직 추가 (여기서는 예시로 메시지 표시)
						tempMgr = new TempMgr();
						
						if(item.equals("소음으로 인한 관람 방해")) {
							item = "소음";
						}else if(item.equals("핸드폰 불빛으로 인한 관람 방해")) {
							item = "핸드폰 불빛";
						}else if(item.equals("앞 좌석 가격으로 인한 관람 방해")) {
							item = "좌석 발로차기";
						}
						
						if(tempMgr.insertWarning(item, cinemaNum, date, docid, seat)) {
							JOptionPane.showMessageDialog(frame, "신고가 접수되었습니다.", "신고 완료", JOptionPane.INFORMATION_MESSAGE);
							frame.dispose(); // 현재 창을 닫음
						}else {
							JOptionPane.showMessageDialog(frame, "예매되지 않은 좌석입니다.", "신고 실패", JOptionPane.CANCEL_OPTION);
						}

					}
				}
			}
		});

	}
}
