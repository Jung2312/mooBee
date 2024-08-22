package UI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import user.UserBean;
import user.UserMgr;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class ModifyUser {

	private JFrame frame;
	private JTextField Name_Field;
	private JTextField PhoneNumber_Field;
	private JLabel Name;
	private JLabel ChangePwd;
	private JLabel ChkPwd;
	private JLabel PhoneNumber;
	private JPasswordField ChangePwd_Field;
	private JPasswordField ChkPwd_Field;
	private UserMgr userMgr;
	private static String userId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userId = userId;
					ModifyUser window = new ModifyUser(userId);
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
	public ModifyUser(String userId) {
		this.userId = userId;
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
	
	
	
	public void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 384, 461);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		Name_Field = new JTextField();
		Name_Field.setBounds(27, 49, 330, 36);
		panel.add(Name_Field);
		Name_Field.setColumns(10);

		PhoneNumber_Field = new JTextField();
		PhoneNumber_Field.setColumns(10);
		PhoneNumber_Field.setBounds(27, 337, 330, 36);
		panel.add(PhoneNumber_Field);

		Name = new JLabel("이름");
		Name.setBounds(27, 28, 57, 15);
		panel.add(Name);

		ChangePwd = new JLabel("비밀번호 변경");
		ChangePwd.setBounds(27, 123, 93, 15);
		panel.add(ChangePwd);

		ChkPwd = new JLabel("비밀번호 확인");
		ChkPwd.setBounds(27, 221, 93, 15);
		panel.add(ChkPwd);

		PhoneNumber = new JLabel("전화번호");
		PhoneNumber.setBounds(27, 318, 93, 15);
		panel.add(PhoneNumber);

		JButton Cancel_Btn = new ControlButton("취소");
		Cancel_Btn.setBounds(27, 401, 135, 39);
		panel.add(Cancel_Btn);
		
		Cancel_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // 현재 창을 닫음
			}
		});

		JButton Modify_Btn = new ControlButton("수정");
		Modify_Btn.setBounds(222, 401, 135, 39);
		panel.add(Modify_Btn);
		
		Modify_Btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userMgr = new UserMgr();
				String password = new String(ChangePwd_Field.getPassword());
				String rePassword = new String(ChkPwd_Field.getPassword());
				String name = Name_Field.getText();
				String phone = PhoneNumber_Field.getText();
				if(password.equals("") || name.equals("") || phone.equals("")) {
					JOptionPane.showMessageDialog(frame, "빈칸 없이 모두 입력하세요.");
				}else {
					if(password.equals(rePassword)) {
						UserBean bean = new UserBean();
						bean.setName(name);
						bean.setPhone(phone);
						bean.setPassword(password);
						bean.setUserId(userId);
						if(userMgr.updateUser(bean)) {
							JOptionPane.showMessageDialog(frame, bean.getName()+"님의 정보가 수정 되었습니다.", "수정 완료", JOptionPane.INFORMATION_MESSAGE);
							frame.dispose();
						}else {
							JOptionPane.showMessageDialog(frame, "올바른 정보를 입력하세요.");
						}
					}else {
						JOptionPane.showMessageDialog(frame, "비밀번호가 다릅니다. 다시 입력하세요.");
					}
				}
				
			}
		});

		ChangePwd_Field = new JPasswordField();
		ChangePwd_Field.setBounds(27, 145, 330, 39);
		panel.add(ChangePwd_Field);

		ChkPwd_Field = new JPasswordField();
		ChkPwd_Field.setBounds(27, 246, 330, 39);
		panel.add(ChkPwd_Field);
	}
}
