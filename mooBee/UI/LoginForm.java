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
        loginSubmitButton.addActionListener(e -> {
            loginFrame.dispose();  // 로그인 창 닫기
            openMainForm();  // 메인 폼 열기
            dispose(); // LoginForm 창 닫기
        });
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

    private void openMainForm() {
        new MainForm();  // MainForm 호출
    }
    
    private void openSignupForm() {
    	
    	mgr = new UserMgr();
    	
    	JFrame signupFrame = new JFrame("회원가입 창");
        signupFrame.setSize(400, 550);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(50, 20, 300, 30);
        signupFrame.add(nameLabel);

        JTextField signupNameField = new JTextField();
        signupNameField.setBounds(50, 50, 300, 40);
        signupFrame.add(signupNameField);

        JLabel emailLabel = new JLabel("이메일");
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

        JLabel dobLabel = new JLabel("생년월일");
        dobLabel.setBounds(50, 370, 300, 30);
        signupFrame.add(dobLabel);

        JTextField signupDobField = new JTextField("ex) 2000-01-01");
        signupDobField.setBounds(50, 400, 300, 40);
        signupDobField.setForeground(Color.GRAY); // Placeholder 색상 설정
        signupDobField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (signupDobField.getText().equals("ex) 2000-01-01")) {
                    signupDobField.setText("");
                    signupDobField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (signupDobField.getText().isEmpty()) {
                    signupDobField.setForeground(Color.GRAY);
                    signupDobField.setText("ex) 2000-01-01");
                }
            }
        });
        signupFrame.add(signupDobField);
        
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
        signupSubmitButton.setBounds(50, 450, 300, 40);
        signupSubmitButton.addActionListener(e -> signupFrame.dispose()); // 버튼 클릭 시 창 닫기
        signupFrame.add(signupSubmitButton);

        signupFrame.setVisible(true);
    }

    private void openForgotPasswordWindow() {
        JFrame forgotPasswordFrame = new JFrame("비밀번호 찾기 창");
        forgotPasswordFrame.setSize(400, 400); // 창 사이즈 확대
        forgotPasswordFrame.setLocationRelativeTo(null);
        forgotPasswordFrame.setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(50, 20, 300, 30);
        forgotPasswordFrame.add(nameLabel);

        JTextField forgotPasswordNameField = new JTextField();
        forgotPasswordNameField.setBounds(50, 50, 300, 40);
        forgotPasswordFrame.add(forgotPasswordNameField);

        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setBounds(50, 100, 300, 30);
        forgotPasswordFrame.add(emailLabel);

        JTextField forgotPasswordEmailField = new JTextField();
        forgotPasswordEmailField.setBounds(50, 130, 300, 40);
        forgotPasswordFrame.add(forgotPasswordEmailField);

        JLabel phoneLabel = new JLabel("전화번호");
        phoneLabel.setBounds(50, 180, 300, 30);
        forgotPasswordFrame.add(phoneLabel);

        JTextField forgotPasswordPhoneField = new JTextField();
        forgotPasswordPhoneField.setBounds(50, 210, 300, 40);
        forgotPasswordFrame.add(forgotPasswordPhoneField);

        JButton forgotPasswordSubmitButton = new JButton("비밀번호 찾기");
        forgotPasswordSubmitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        forgotPasswordSubmitButton.setBounds(50, 270, 300, 40);
        forgotPasswordSubmitButton.addActionListener(e -> {
            forgotPasswordFrame.dispose(); // 창 닫기
            openVerificationWindow(); // 인증번호 창 열기
        });
        forgotPasswordFrame.add(forgotPasswordSubmitButton);

        forgotPasswordFrame.setVisible(true);
    }


    private void openVerificationWindow() {
        JFrame verificationFrame = new JFrame("인증번호 입력");
        verificationFrame.setSize(400, 200);
        verificationFrame.setLocationRelativeTo(null);
        verificationFrame.setLayout(null);

        JLabel verificationLabel = new JLabel("인증번호");
        verificationLabel.setBounds(50, 20, 300, 30);
        verificationFrame.add(verificationLabel);

        JTextField verificationField = new JTextField();
        verificationField.setBounds(50, 50, 300, 40);
        verificationFrame.add(verificationField);

        JButton verifyButton = new JButton("확인");
        verifyButton.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        verifyButton.setBounds(50, 100, 300, 40);
        verifyButton.addActionListener(e -> verificationFrame.dispose()); // 창 닫기
        verificationFrame.add(verifyButton);

        verificationFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new LoginForm();
    }
}
