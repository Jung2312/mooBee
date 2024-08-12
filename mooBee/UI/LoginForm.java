package UI;

import javax.swing.*;

import user.UserBean;
import user.UserMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame {
	
	UserMgr mgr;
	
    public LoginForm() {
       
        setTitle("MooBee");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setBackground(Color.WHITE);
      
        JButton loginButton = new JButton("로그인");
        loginButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        loginButton.setBounds(390, 440, 90, 40);
        add(loginButton);
    
        JButton signupButton = new JButton("회원가입");
        signupButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        signupButton.setBounds(490, 440, 110, 40);
        add(signupButton);

        loginButton.addActionListener(e -> openLoginForm());

        signupButton.addActionListener(e -> openSignupForm());

        setVisible(true);
    }

    private void openLoginForm() {
        JFrame loginFrame = new JFrame("로그인 창");
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);

        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setBounds(50, 20, 300, 30);
        loginFrame.add(emailLabel);

        JTextField loginEmailField = new JTextField();
        loginEmailField.setBounds(50, 50, 300, 40);
        loginFrame.add(loginEmailField);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(50, 90, 300, 30);
        loginFrame.add(passwordLabel);

        JPasswordField loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(50, 120, 300, 40);
        loginFrame.add(loginPasswordField);

        JButton loginSubmitButton = new JButton("로그인");
        loginSubmitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        loginSubmitButton.setBounds(50, 170, 300, 40);
        loginSubmitButton.addActionListener(e -> loginFrame.dispose()); 
        loginFrame.add(loginSubmitButton);

        JLabel forgotPasswordLabel = new JLabel("<HTML><U>비밀번호 찾기</U></HTML>"); // 밑줄 추가
        forgotPasswordLabel.setBounds(50, 220, 300, 30);
        forgotPasswordLabel.setForeground(Color.BLACK);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openForgotPasswordWindow();
                loginFrame.dispose(); 
            }
        });
        loginFrame.add(forgotPasswordLabel);

        loginFrame.setVisible(true);
    }

    private void openSignupForm() {
    	
    	mgr = new UserMgr();
    	
        JFrame signupFrame = new JFrame("회원가입 창");
        signupFrame.setSize(400, 500);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(50, 20, 300, 30);
        signupFrame.add(nameLabel);

        JTextField signupNameField = new JTextField();
        signupNameField.setBounds(50, 50, 300, 40);
        signupFrame.add(signupNameField);

        JLabel emailLabel = new JLabel("이메일(아이디)");
        emailLabel.setBounds(50, 90, 300, 30);
        signupFrame.add(emailLabel);

        JTextField signupEmailField = new JTextField();
        signupEmailField.setBounds(50, 120, 300, 40);
        signupFrame.add(signupEmailField);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(50, 160, 300, 30);
        signupFrame.add(passwordLabel);

        JPasswordField signupPasswordField = new JPasswordField();
        signupPasswordField.setBounds(50, 190, 300, 40);
        signupFrame.add(signupPasswordField);

        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setBounds(50, 230, 300, 30);
        signupFrame.add(confirmPasswordLabel);

        JPasswordField signupConfirmPasswordField = new JPasswordField();
        signupConfirmPasswordField.setBounds(50, 260, 300, 40);
        signupFrame.add(signupConfirmPasswordField);

        JLabel phoneLabel = new JLabel("전화번호");
        phoneLabel.setBounds(50, 300, 300, 30);
        signupFrame.add(phoneLabel);

        JTextField signupPhoneField = new JTextField();
        signupPhoneField.setBounds(50, 330, 300, 40);
        signupFrame.add(signupPhoneField);

        JButton signupSubmitButton = new JButton("회원가입");
        signupSubmitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if(obj==signupSubmitButton) {
					UserBean bean = new UserBean();
					bean.setUserId(signupEmailField.getText());
					bean.setName(signupNameField.getText());
					bean.setPassword(new String(signupPasswordField.getPassword()));
					bean.setPhone(signupPhoneField.getText());
					bean.setBirthDate("2000-01-01");
					boolean success = mgr.insertUser(bean);
		            if (success) {
		                JOptionPane.showMessageDialog(signupFrame, "회원가입이 완료되었습니다.");
		                signupFrame.dispose();
		            } else {
		                JOptionPane.showMessageDialog(signupFrame, "회원가입에 실패했습니다.");
		            }
				}
			}
		});
        signupSubmitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        signupSubmitButton.setBounds(50, 380, 300, 40);
        signupSubmitButton.addActionListener(e -> signupFrame.dispose()); 
        signupFrame.add(signupSubmitButton);

        signupFrame.setVisible(true);
    }

    private void openForgotPasswordWindow() {
        JFrame forgotPasswordFrame = new JFrame("비밀번호 찾기 창");
        forgotPasswordFrame.setSize(400, 300);
        forgotPasswordFrame.setLocationRelativeTo(null);
        forgotPasswordFrame.setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(50, 20, 300, 30);
        forgotPasswordFrame.add(nameLabel);

        JTextField forgotPasswordNameField = new JTextField();
        forgotPasswordNameField.setBounds(50, 50, 300, 40);
        forgotPasswordFrame.add(forgotPasswordNameField);

        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setBounds(50, 90, 300, 30);
        forgotPasswordFrame.add(emailLabel);

        JTextField forgotPasswordEmailField = new JTextField();
        forgotPasswordEmailField.setBounds(50, 120, 300, 40);
        forgotPasswordFrame.add(forgotPasswordEmailField);

        JButton forgotPasswordSubmitButton = new JButton("비밀번호 찾기");
        forgotPasswordSubmitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        forgotPasswordSubmitButton.setBounds(50, 170, 300, 40);
        forgotPasswordSubmitButton.addActionListener(e -> forgotPasswordFrame.dispose()); 
        forgotPasswordFrame.add(forgotPasswordSubmitButton);

        forgotPasswordFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
