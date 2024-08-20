package UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
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
	private String userid = "root"; // 로그인한 사용자 ID (테스트용)
	private Object[] originalNotice; // 수정 취소 시 복구할 공지사항 정보
	private static String userId;
	private NoticeBean noticebean;
	private NoticeMgr noticemgr;
	/**
	 * Create the application.
	 */
	// 생성자
	public NoticeList(String userId) {
		this.userid = userId;
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
		JButton HomeButton = new JButton("홈");
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
		if (userid.equals("root")) {
			JButton addNoticeButton = new JButton("공지사항 작성");
			addNoticeButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			addNoticeButton.setBounds(700, 95, 180, 33);
			noticeListPanel.add(addNoticeButton);

			addNoticeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 공지사항 작성 화면으로 이동
					showNoticeCreatePanel(false, noticebean);
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
		noticeDetailTextArea.setBounds(94, 134, 787, 344);
		noticeDetailTextArea.setLineWrap(true);
		noticeDetailTextArea.setWrapStyleWord(true);
		noticeDetailTextArea.setEditable(false);
		noticeDetailPanel.add(noticeDetailTextArea);

		JButton backButton = new JButton("목록");
		backButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		backButton.setBounds(430, 597, 94, 43);
		noticeDetailPanel.add(backButton);

		// 이전 글, 다음 글 버튼
		prevButton = new JButton("이전 글");
		prevButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		prevButton.setBounds(94, 488, 150, 33);
		noticeDetailPanel.add(prevButton);

		nextButton = new JButton("다음 글");
		nextButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		nextButton.setBounds(94, 531, 150, 33);
		noticeDetailPanel.add(nextButton);

		if (userid.equals("root")) {
			// 관리자 전용 수정 및 삭제 버튼
			JButton editButton = new JButton("수정");
			editButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			editButton.setBounds(687, 488, 94, 33);
			noticeDetailPanel.add(editButton);

			editButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        int noticeNum = (int) tableModel.getValueAt(currentIndex, 0); // 현재 선택된 공지사항 번호
			        noticemgr = new NoticeMgr();
			        noticebean = noticemgr.getNotice(noticeNum);
			        
			        // 공지사항 수정 화면으로 이동
			        showNoticeCreatePanel(true, noticebean);
			    }
			});
			JButton deleteButton = new JButton("삭제");
			deleteButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
			deleteButton.setBounds(787, 488, 94, 33);
			noticeDetailPanel.add(deleteButton);

			// 공지사항 삭제 버튼 클릭 시
			deleteButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	int noticeNum = (int) tableModel.getValueAt(currentIndex, 0);
			    	System.out.print(noticeNum);
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
				cardLayout.show(contentPane, "NoticeListPanel");
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

	// 공지사항 세부 내용 패널로 이동
	private void showNoticeDetail(int noticeNum) {

		String title = (String) tableModel.getValueAt(noticeNum, 1);
		String content; 
		String date;
		NoticeMgr noticemgr = new NoticeMgr();
		NoticeBean noticeList = noticemgr.getNotice((int)(tableModel.getValueAt(noticeNum, 0)));
		content = noticeList.getContent();
		date = noticeList.getNoticeDate();
		noticeTitleLabel.setText(title);
		noticeInfoLabel.setText("작성일: " + date);
		// 내용 표시
		noticeDetailTextArea.setText(content);
		// 이전 글/다음 글 버튼 활성화 여부 설정
		prevButton.setEnabled(noticeNum > 0);
		nextButton.setEnabled(noticeNum < tableModel.getRowCount() - 1);

		cardLayout.show(contentPane, "NoticeDetailPanel");
	}

	private void showNoticeCreatePanel(boolean isEditMode, NoticeBean notice) {
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
	        createNoticeTextArea.setBounds(94, 118, 787, 389);
	        createNoticeTextArea.setLineWrap(true);
	        createNoticeTextArea.setWrapStyleWord(true);
	        noticeCreatePanel.add(createNoticeTextArea);

	        JButton saveButton = new JButton("저장");
	        saveButton.setFont(new Font("나눔고딕", Font.PLAIN, 20));
	        saveButton.setBounds(459, 566, 94, 43);
	        noticeCreatePanel.add(saveButton);

	        JButton cancelButton = new JButton("취소");
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
	                
	                noticebean.setNoticeNum(notice != null ? notice.getNoticeNum() : 0); // 수정 시 번호 설정
	                noticebean.setTitle(createNoticeTitleField.getText());
	                noticebean.setContent(createNoticeTextArea.getText());

	                if (isEditMode) {
	                    noticemgr.updateNotice(noticebean); // 공지사항 수정
	                } else {
	                    noticemgr.insertNotice(noticebean); // 새 공지사항 추가
	                }

	                loadNoticeList();
	                //renumberTableRows(); // 번호 재정렬
	                cardLayout.show(contentPane, "NoticeListPanel");
	            }
	        });
	    }

	    // 수정 모드 초기화
	    if (isEditMode && notice != null) {
	        createNoticeTitleField.setText(notice.getTitle());
	        createNoticeTextArea.setText(notice.getContent());
	    } else {
	        createNoticeTitleField.setText("");
	        createNoticeTextArea.setText("");
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
