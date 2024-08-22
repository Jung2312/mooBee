package UI;

import javax.swing.*;

import reservation.ReservationBean;
import reservation.ReservationMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationCompleteForm extends JFrame {
	private static String userId,ViewDate,RSVseat;
	private static int RSVdocid,RSVcinemaNum, Allcount;
	private static double pay;
    private ReservationBean rsvbean;
    private ReservationMgr rsvmgr;
    public ReservationCompleteForm(String userId, int RSVdocid, int RSVcinemaNum, String ViewDate, double pay,int Allcount, String RSVSeat) {
    	this.userId = userId;
    	this.RSVcinemaNum = RSVcinemaNum;
    	this.RSVdocid = RSVdocid;
    	this.ViewDate = ViewDate;
    	this.Allcount = Allcount;
    	this.RSVseat = RSVSeat;
    	this.pay = pay;
    	rsvbean = new ReservationBean();
    	rsvmgr = new ReservationMgr();
        setTitle("예매 상세 정보");
        setSize(500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
       
     // Main logo image
        ImageIcon logoIcon = new ImageIcon("./UI/images/mainlogo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(80, 20, 320, 200); 
        add(logoLabel);
        
        JLabel completionLabel = new JLabel("예매가 완료되었습니다.", JLabel.CENTER);
        completionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        completionLabel.setBounds(100, 150, 300, 30);
        add(completionLabel);


        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 1, 10, 10));
        infoPanel.setBounds(50, 200, 400, 400);
        add(infoPanel);
        String[] seatStrings = RSVSeat.split(",");
        int[] seatNumbers = new int[seatStrings.length];
        String seat = "";
		 // String 배열의 각 요소를 int로 변환하여 int 배열에 저장합니다.
        try {
            for (int i = 0; i < seatStrings.length; i++) {
                if (seatStrings[i] != null && !seatStrings[i].isEmpty()) {
                    seatNumbers[i] = Integer.parseInt(seatStrings[i]);
                } else {
                    // 잘못된 문자열을 찾았을 경우의 처리
                    System.out.println("Invalid seat number: " + seatStrings[i]);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();  // 잘못된 형식의 숫자일 때 예외 처리
        }
        for (int i = 0; i < seatStrings.length; i++) {
        	
			seat+=rsvmgr.getSeat(seatNumbers[i]);
			if(i < seatStrings.length-1) seat+=", ";
		}
        
        String[] infoLabels = {"예매자 : "+rsvmgr.getName(userId) ,"영화 제목 : "+rsvmgr.getMoive(RSVdocid), 
        		"극장 : "+rsvmgr.getCinema(RSVcinemaNum) +"  "+ rsvmgr.gettheaterNum(RSVcinemaNum)+"관", 
        		"일시 : "+ViewDate, "인원 : "+Allcount + " 명", "좌석 : "+seat, "결제 금액 : " + pay};
    
        for (String label : infoLabels) {
            JLabel infoLabel = new JLabel(label);
            infoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            infoPanel.add(infoLabel);
        }
        JLabel commnetLabel = new JLabel("<html>* 본 영화는 약 10분 후에 시작합니다. *<br>"
        		+ "* 영화 에티켓을 지키지 않을시 매너온도에 불이익이 갈 수 있습니다. * <br>"
        		+ "* 예매 취소는 영화시작 30분 전까지만 가능합니다. *</html>", JLabel.CENTER);
        commnetLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        commnetLabel.setBounds(50, 550, 400, 200);
        add(commnetLabel);
        
        JButton confirmButton = new ControlButton("확인");
        confirmButton.setBounds(200, 750, 100, 30);
        add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm(userId);  
                dispose();      
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReservationCompleteForm(userId, RSVcinemaNum, RSVcinemaNum, ViewDate, pay ,Allcount, RSVseat);
    }
}
