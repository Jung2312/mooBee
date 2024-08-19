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
        mgr = new UserMgr();
        setTitle("MooBee");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 배경 이미지 설정 
        ImageIcon backgroundImageIcon = new ImageIcon("C:/moobee/mooBee/mooBee/UI/images/loginimage.png");
        Image backgroundImage = backgroundImageIcon.getImage(); 
        Image resizedImage = backgroundImage.getScaledInstance(1000, 700, Image.SCALE_SMOOTH); // 이미지 크기 조절
        ImageIcon resizedBackgroundImageIcon = new ImageIcon(resizedImage); // 조절된 이미지로 새로운 ImageIcon 생성
        JLabel backgroundLabel = new JLabel(resizedBackgroundImageIcon);
        backgroundLabel.setSize(1000, 700);
        setContentPane(backgroundLabel);
        backgroundLabel.setLayout(null); 

        // 로그인 버튼 생성 및 스타일링
        JButton loginButton = new HoneyButton("로그인");
        loginButton.setBounds(312, 480, 176, 50);  // 위치와 크기 설정
        backgroundLabel.add(loginButton); 

        // 회원가입 버튼 생성 및 스타일링
        JButton signupButton = new HoneyButton("회원가입");
        signupButton.setBounds(512, 480, 176, 50);  // 위치와 크기 설정
        backgroundLabel.add(signupButton); 

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
        emailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        emailLabel.setBounds(50, 20, 300, 30);
        loginFrame.add(emailLabel);

        JTextField loginEmailField = new JTextField();
        loginEmailField.setBounds(50, 50, 300, 40);
        loginFrame.add(loginEmailField);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        passwordLabel.setBounds(50, 90, 300, 30);
        loginFrame.add(passwordLabel);

        JPasswordField loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(50, 120, 300, 40);
        loginFrame.add(loginPasswordField);

        JButton loginSubmitButton = new HoneyButton("로그인");
        loginSubmitButton.setBounds(50, 170, 300, 40);
        loginFrame.add(loginSubmitButton);

        loginSubmitButton.addActionListener(e -> {
            String userId = loginEmailField.getText();
            String password = new String(loginPasswordField.getPassword());
            boolean isValidUser = mgr.login(userId, password);
            if (isValidUser) {
                JOptionPane.showMessageDialog(loginFrame, "로그인 성공!");
                loginFrame.dispose(); // 로그인 성공 시 창 닫기
                dispose();
                openMainForm(userId);  // 메인 폼 열기
            } else {
                JOptionPane.showMessageDialog(loginFrame, "로그인 실패. 이메일 또는 비밀번호가 잘못되었습니다.");
            }
        });

        JLabel forgotPasswordLabel = new JLabel("<HTML><U>비밀번호 찾기</U></HTML>"); // 밑줄 추가
        forgotPasswordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
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

    private void openMainForm(String userId) {
        new MainForm(userId);  // MainForm 호출
    }

    private void openSignupForm() {

        mgr = new UserMgr();

        JFrame signupFrame = new JFrame("회원가입 창");
        signupFrame.setSize(400, 550);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        nameLabel.setBounds(50, 20, 300, 30);
        signupFrame.add(nameLabel);

        JTextField signupNameField = new JTextField();
        signupNameField.setBounds(50, 50, 300, 40);
        signupFrame.add(signupNameField);

        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        emailLabel.setBounds(50, 90, 300, 30);
        signupFrame.add(emailLabel);

        JTextField signupEmailField = new JTextField();
        signupEmailField.setBounds(50, 120, 300, 40);
        signupFrame.add(signupEmailField);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        passwordLabel.setBounds(50, 160, 300, 30);
        signupFrame.add(passwordLabel);

        JPasswordField signupPasswordField = new JPasswordField();
        signupPasswordField.setBounds(50, 190, 300, 40);
        signupFrame.add(signupPasswordField);

        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        confirmPasswordLabel.setBounds(50, 230, 300, 30);
        signupFrame.add(confirmPasswordLabel);

        JPasswordField signupConfirmPasswordField = new JPasswordField();
        signupConfirmPasswordField.setBounds(50, 260, 300, 40);
        signupFrame.add(signupConfirmPasswordField);

        JLabel phoneLabel = new JLabel("전화번호");
        phoneLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        phoneLabel.setBounds(50, 300, 300, 30);
        signupFrame.add(phoneLabel);

        JTextField signupPhoneField = new JTextField();
        signupPhoneField.setBounds(50, 330, 300, 40);
        signupFrame.add(signupPhoneField);

        JLabel dobLabel = new JLabel("생년월일");
        dobLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        dobLabel.setBounds(50, 370, 300, 30);
        signupFrame.add(dobLabel);

        JTextField signupDobField = new JTextField("ex) 2000-01-01");
        signupDobField.setBounds(50, 400, 300, 40);
        signupDobField.setForeground(Color.GRAY); // Placeholder 색상 설정
        signupDobField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
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

        JButton signupSubmitButton = new HoneyButton("회원가입");
        signupSubmitButton.setBounds(50, 450, 300, 40);
        signupFrame.add(signupSubmitButton);

        signupSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj == signupSubmitButton) {
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

        signupFrame.setVisible(true);
    }

    private void openForgotPasswordWindow() {
        JFrame forgotPasswordFrame = new JFrame("비밀번호 찾기 창");
        forgotPasswordFrame.setSize(400, 400); // 창 사이즈 확대
        forgotPasswordFrame.setLocationRelativeTo(null);
        forgotPasswordFrame.setLayout(null);

        JLabel nameLabel = new JLabel("이름");
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        nameLabel.setBounds(50, 20, 300, 30);
        forgotPasswordFrame.add(nameLabel);

        JTextField forgotPasswordNameField = new JTextField();
        forgotPasswordNameField.setBounds(50, 50, 300, 40);
        forgotPasswordFrame.add(forgotPasswordNameField);

        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        emailLabel.setBounds(50, 100, 300, 30);
        forgotPasswordFrame.add(emailLabel);

        JTextField forgotPasswordEmailField = new JTextField();
        forgotPasswordEmailField.setBounds(50, 130, 300, 40);
        forgotPasswordFrame.add(forgotPasswordEmailField);

        JLabel phoneLabel = new JLabel("전화번호");
        phoneLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        phoneLabel.setBounds(50, 180, 300, 30);
        forgotPasswordFrame.add(phoneLabel);

        JTextField forgotPasswordPhoneField = new JTextField();
        forgotPasswordPhoneField.setBounds(50, 210, 300, 40);
        forgotPasswordFrame.add(forgotPasswordPhoneField);

        JButton forgotPasswordSubmitButton = new HoneyButton("비밀번호 찾기");
        forgotPasswordSubmitButton.setBounds(50, 270, 300, 40);
        forgotPasswordFrame.add(forgotPasswordSubmitButton);

        forgotPasswordSubmitButton.addActionListener(e -> {
            forgotPasswordFrame.dispose(); // 창 닫기
            openVerificationWindow(); // 인증번호 창 열기
        });

        forgotPasswordFrame.setVisible(true);
    }

    private void openVerificationWindow() {
        JFrame verificationFrame = new JFrame("인증번호 입력");
        verificationFrame.setSize(400, 200);
        verificationFrame.setLocationRelativeTo(null);
        verificationFrame.setLayout(null);

        JLabel verificationLabel = new JLabel("인증번호");
        verificationLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        verificationLabel.setBounds(50, 20, 300, 30);
        verificationFrame.add(verificationLabel);

        JTextField verificationField = new JTextField();
        verificationField.setBounds(50, 50, 300, 40);
        verificationFrame.add(verificationField);

        JButton verifyButton = new HoneyButton("확인");
        verifyButton.setBounds(50, 100, 300, 40);
        verificationFrame.add(verifyButton);

        verifyButton.addActionListener(e -> verificationFrame.dispose()); // 창 닫기

        verificationFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
