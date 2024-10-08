package UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.mysql.cj.protocol.x.Notice;

import notice.NoticeBean;
import notice.NoticeMgr;

import javax.swing.border.CompoundBorder;
import javax.swing.JLabel;

public class NoticeList {

	private JFrame frame;
	private MainForm mainForm; // MainForm에 대한 참조
	private CardLayout cardLayout;
	private JPanel contentPane;
	private JPanel noticeListPanel;
	private JPanel noticeDetailPanel;
	private JPanel noticeCreatePanel;
	private JTextArea noticeDetailTextArea;
	private JTextArea createNoticeTextArea;
	private JTextField createNoticeTitleField;
	private DefaultTableModel tableModel;
	private int currentIndex;
	private JButton prevButton;
	private JButton nextButton;
	private JLabel notice_Label;
	private JLabel noticeTitleLabel; // 클래스 멤버로 이동
	private JLabel noticeInfoLabel; // 클래스 멤버로 이동
	private static String userId;
	private NoticeBean noticebean;
	private NoticeMgr noticemgr;
	private int noticeNum;
	private boolean isEditMode;
	/**
	 * Create the application.
	 */
	// 생성자
	public NoticeList(String userId) {
		this.userId = userId;
		initialize();
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		noticebean = new NoticeBean();
		noticemgr = new NoticeMgr();
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardLayout = new CardLayout();
		contentPane = new JPanel(cardLayout);
		frame.setContentPane(contentPane);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);

		// 공지사항 리스트 패널
		noticeListPanel = new JPanel();
		noticeListPanel.setLayout(null);
		contentPane.add(noticeListPanel, "NoticeListPanel");
		noticemgr.listNotice();
		// 테이블 데이터 모델 설정
		String[] columnNames = { "번호", "제목","작성 날짜"};
		Object[][] data = {};

		tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 셀 편집 비활성화
			}
		};
		loadNoticeList();
		// JTable 생성
		JTable table = new JTable(tableModel);
		table.setBorder(new CompoundBorder());
		table.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		table.setRowHeight(40);

		// JTable의 모든 셀 중앙 정렬
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);

		// JTableHeader 설정
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("나눔고딕", Font.BOLD, 20));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setBackground(Color.LIGHT_GRAY);

		// 열 너비 조정 (번호와 구분 열의 너비를 줄임)
		TableColumn column0 = table.getColumnModel().getColumn(0);
		column0.setPreferredWidth(50); // 번호 열 너비

		TableColumn column2 = table.getColumnModel().getColumn(1);
		column2.setPreferredWidth(650); // 내용 열 너비
		
		TableColumn column3 = table.getColumnModel().getColumn(2);
		column2.setPreferredWidth(50); // 내용 열 너비
		// 테이블을 포함한 스크롤 패널 추가
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(94, 139, 788, 446);
		noticeListPanel.add(scrollPane);

		// 홈 버튼
		JButton HomeButton = new ControlButton("홈");
		HomeButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		HomeButton.setBounds(45, 36, 92, 33);
		noticeListPanel.add(HomeButton);

		HomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MainForm mf = new MainForm(userId);
				mf.setVisible(true);
				frame.dispose();
			}
		});

		notice_Label = new JLabel("공지사항");
		notice_Label.setHorizontalAlignment(SwingConstants.CENTER);
		notice_Label.setFont(new Font("나눔고딕", Font.PLAIN, 30));
		notice_Label.setBounds(94, 93, 117, 35);
		noticeListPanel.add(notice_Label);

		// 관리자 계정일 경우 공지사항 작성 버튼 추가
		if (userId.equals("root")) {
			JButton addNoticeButton = new ControlButton("공지사항 작성");
			addNoticeButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			addNoticeButton.setBounds(700, 95, 180, 33);
			noticeListPanel.add(addNoticeButton);

			addNoticeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 공지사항 작성 화면으로 이동
			        isEditMode = false;
					showNoticeCreatePanel(noticebean);
				}
			});
		}

		// 공지사항 세부 내용 패널
		noticeDetailPanel = new JPanel();
		noticeDetailPanel.setLayout(null);
		contentPane.add(noticeDetailPanel, "NoticeDetailPanel");

		// 공지 제목 레이블
		noticeTitleLabel = new JLabel();
		noticeTitleLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		noticeTitleLabel.setBounds(94, 54, 787, 40);
		noticeDetailPanel.add(noticeTitleLabel);

		// 작성일 및 조회수 레이블
		noticeInfoLabel = new JLabel();
		noticeInfoLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		noticeInfoLabel.setBounds(94, 104, 787, 20);
		noticeDetailPanel.add(noticeInfoLabel);

		// 공지 내용 텍스트 에어리어
		noticeDetailTextArea = new JTextArea();
		noticeDetailTextArea.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		noticeDetailTextArea.setBounds(94, 134, 787, 500);
		noticeDetailTextArea.setLineWrap(true);
		noticeDetailTextArea.setWrapStyleWord(true);
		noticeDetailTextArea.setEditable(false);
		noticeDetailPanel.add(noticeDetailTextArea);
		
		imageLabel = new JLabel();  // 여기에 imageLabel 초기화 추가
		imageLabel.setBounds(94, 300, 787, 800);  // 이미지 위치와 크기 설정
		noticeDetailPanel.add(imageLabel);  // 패널에 추가
		
		JButton backButton = new ControlButton("목록");
		backButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		backButton.setBounds(430, 597, 94, 43);
		noticeDetailPanel.add(backButton);

		// 이전 글, 다음 글 버튼
		prevButton = new ControlButton2("이전 글");
		prevButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		prevButton.setBounds(94, 488, 150, 33);
		noticeDetailPanel.add(prevButton);

		nextButton = new ControlButton2("다음 글");
		nextButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		nextButton.setBounds(94, 531, 150, 33);
		noticeDetailPanel.add(nextButton);

		if (userId.equals("root")) {
			// 관리자 전용 수정 및 삭제 버튼
			JButton editButton = new ControlButton("수정");
			editButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			editButton.setBounds(687, 488, 94, 33);
			noticeDetailPanel.add(editButton);

			editButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {

			        noticeNum = (int) tableModel.getValueAt(currentIndex, 0); // 현재 선택된 공지사항 번호
			        noticemgr = new NoticeMgr();
			        noticebean = noticemgr.getNotice(noticeNum);
			        isEditMode = true;
			        // 공지사항 수정 화면으로 이동
			        showNoticeCreatePanel(noticebean);
			    }
			});
			JButton deleteButton = new ControlButton("삭제");
			deleteButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			deleteButton.setBounds(787, 488, 94, 33);
			noticeDetailPanel.add(deleteButton);

			// 공지사항 삭제 버튼 클릭 시
			deleteButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	noticeNum = (int) tableModel.getValueAt(currentIndex, 0);
			        tableModel.removeRow(currentIndex); // 현재 선택된 행 삭제
			        noticemgr = new NoticeMgr();
			        noticemgr.deleteNotice(noticeNum);
			       // renumberTableRows(); // 삭제 후 번호 재정렬
			        cardLayout.show(contentPane, "NoticeListPanel");
			    }
			});
		}

		// 테이블 항목 클릭 시 세부 내용 패널로 이동
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					currentIndex = table.getSelectedRow();
					showNoticeDetail(currentIndex);
				}
			}
		});

		// 목록 버튼 클릭 시
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noticeDetailPanel.repaint();
				cardLayout.show(contentPane, "NoticeListPanel");
			     // 기존 컴포넌트를 제거
			}
		});

		// 이전 글 버튼 클릭 시
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentIndex > 0) {
					currentIndex--;
					showNoticeDetail(currentIndex);
				}
			}
		});

		// 다음 글 버튼 클릭 시
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentIndex < tableModel.getRowCount() - 1) {
					currentIndex++;
					showNoticeDetail(currentIndex);
				}
			}
		});

		frame.setVisible(true);
	}
	private void loadNoticeList() {
	    NoticeMgr noticeMgr = new NoticeMgr();
	    List<NoticeBean> noticeList = noticeMgr.listNotice(); // 공지사항 목록 가져오기

	    // 데이터 배열 생성
	    Object[][] data = new Object[noticeList.size()][3];
	    
	    for (int i = 0; i < noticeList.size(); i++) {
	        NoticeBean notice = noticeList.get(i);
	        data[i][0] = notice.getNoticeNum(); // 번호
	        data[i][1] = notice.getTitle(); // 제목
	        data[i][2] = notice.getNoticeDate();
	    }

	    // 기존 데이터 삭제
	    tableModel.setRowCount(0);
	    
	    // 새로운 데이터 추가
	    for (Object[] row : data) {
	        tableModel.addRow(row);
	    }
	}
	private JLabel imageLabel; // 이미지 표시를 위한 JLabel
    private String selectedImagePath; // 선택된 이미지 경로 저장
	// 공지사항 세부 내용 패널로 이동
	private void showNoticeDetail(int noticeNum) {
		for (Component component : noticeDetailPanel.getComponents()) {
	        if (component instanceof JScrollPane) {
	            noticeDetailPanel.remove(component);
	        }
	    }
		NoticeMgr noticemgr = new NoticeMgr();
        NoticeBean noticeList = noticemgr.getNotice((int) (tableModel.getValueAt(noticeNum, 0)));
        String content = noticeList.getContent();
        String date = noticeList.getNoticeDate();
        String imagePath = noticeList.getNoticeImg(); 
		String title = (String) tableModel.getValueAt(noticeNum, 1);
		content = noticeList.getContent();
		date = noticeList.getNoticeDate();
		
		noticeTitleLabel.setText(title);
		noticeInfoLabel.setText("작성일: " + date);
		
		noticeDetailTextArea.setText(content);
	    noticeDetailTextArea.setCaretPosition(0);
	    
	    JScrollPane textScrollPane = new JScrollPane(noticeDetailTextArea);
	    textScrollPane.setBounds(94, 134, 787, 150); // 스크롤 패널의 위치와 크기 설정
	    noticeDetailPanel.add(textScrollPane);
	   
	    noticeDetailPanel.add(textScrollPane);
	    
		 // 이미지 표시
        if (imagePath != null) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setIcon(null);
        }
        // 이미지 스크롤을 위한 JScrollPane 설정
        JScrollPane imageScrollPane = new JScrollPane(imageLabel);
        imageScrollPane.setBounds(94, 300, 787, 180); // 스크롤패인 크기 설정
        noticeDetailPanel.add(imageScrollPane);
		// 이전 글/다음 글 버튼 활성화 여부 설정
		prevButton.setEnabled(noticeNum > 0);
		nextButton.setEnabled(noticeNum < tableModel.getRowCount() - 1);
		noticeDetailPanel.revalidate();
	    noticeDetailPanel.repaint();
		cardLayout.show(contentPane, "NoticeDetailPanel");
	}

	// 공지사항 작성/수정 패널로 이동
    private void showNoticeCreatePanel(NoticeBean notice) {
        if (noticeCreatePanel == null) {
            noticeCreatePanel = new JPanel();
            noticeCreatePanel.setLayout(null);
            contentPane.add(noticeCreatePanel, "NoticeCreatePanel");

            JLabel titleLabel = new JLabel("제목:");
            titleLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
            titleLabel.setBounds(94, 50, 100, 30);
            noticeCreatePanel.add(titleLabel);

            createNoticeTitleField = new JTextField();
            createNoticeTitleField.setFont(new Font("나눔고딕", Font.PLAIN, 20));
            createNoticeTitleField.setBounds(200, 50, 681, 30);
            noticeCreatePanel.add(createNoticeTitleField);

            createNoticeTextArea = new JTextArea();
            createNoticeTextArea.setFont(new Font("나눔고딕", Font.PLAIN, 20));
            createNoticeTextArea.setBounds(94, 118, 787, 500);
            createNoticeTextArea.setLineWrap(true);
            createNoticeTextArea.setWrapStyleWord(true);
            
            noticeCreatePanel.add(createNoticeTextArea);
            JScrollPane textScrollPane = new JScrollPane(createNoticeTextArea);
            textScrollPane.setBounds(94, 118, 787, 150); // 스크롤 패널의 위치와 크기
            noticeCreatePanel.add(textScrollPane);
            
            // 이미지 파일 선택 버튼
            JButton imageButton = new ControlButton("이미지 선택");
            imageButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
            imageButton.setBounds(94, 280, 150, 30);
            noticeCreatePanel.add(imageButton);

            // 이미지 미리보기 레이블
            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER); // 이미지 중앙 정렬
            JScrollPane imageScrollPane = new JScrollPane(imageLabel);
            imageScrollPane.setBounds(94, 320, 200, 220); // 이미지 스크롤 패널의 위치와 크기
            noticeCreatePanel.add(imageScrollPane);

            noticeNum = noticebean.getNoticeNum();
            
            imageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        selectedImagePath = fileChooser.getSelectedFile().getAbsolutePath();
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(imageIcon);
                    }
                }
            });

            JButton saveButton = new ControlButton("저장");
            saveButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
            saveButton.setBounds(459, 566, 94, 43);
            noticeCreatePanel.add(saveButton);

            JButton cancelButton = new ControlButton("취소");
            cancelButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
            cancelButton.setBounds(300, 566, 94, 43);
            noticeCreatePanel.add(cancelButton);

            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(contentPane, "NoticeListPanel");
                }
            });
            
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    NoticeMgr noticemgr = new NoticeMgr();
                    NoticeBean noticebean = new NoticeBean();

                    // 수정 모드일 경우 기존 공지사항 번호를 사용, 새로운 공지사항일 경우 번호를 0으로 설정
                    noticebean.setNoticeNum(isEditMode ? noticeNum : 0);
                    noticebean.setTitle(createNoticeTitleField.getText());
                    noticebean.setContent(createNoticeTextArea.getText());
                    noticebean.setNoticeImg(selectedImagePath); // 이미지 경로 저장

                    if (isEditMode) {
                        noticemgr.updateNotice(noticebean); // 공지사항 수정
                    } else {
                        noticemgr.insertNotice(noticebean); // 새 공지사항 추가
                    }

                    loadNoticeList();
                    cardLayout.show(contentPane, "NoticeListPanel");
                }
            });


        }

        // 수정 모드 초기화
        if (isEditMode && notice != null) {
            createNoticeTitleField.setText(notice.getTitle());
            createNoticeTextArea.setText(notice.getContent());
            selectedImagePath = notice.getNoticeImg();

            // 기존 이미지 미리보기 표시
            if (selectedImagePath != null) {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
            } else {
                imageLabel.setIcon(null);
            }
        } else {
            createNoticeTitleField.setText("");
            createNoticeTextArea.setText("");
            imageLabel.setIcon(null);
            selectedImagePath = null;
        }
        cardLayout.show(contentPane, "NoticeCreatePanel");
    }

	/*
	 * // 테이블 인덱스 재정렬 (번호 열 업데이트) private void renumberTableRows() { for (int i = 0;
	 * i < tableModel.getRowCount(); i++) {
	 * tableModel.setValueAt(tableModel.getRowCount() - i, i, 0); // 번호를 순차적으로 재설정 }
	 * }
	 */


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoticeList window = new NoticeList(userId);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
