package seat;

public class SeatBean {
	private int seatId;
	private String seatNum;
	private int cinemaNum;
	private int seatAmount;
	private String seatImg;
	private boolean SeatChk;
	
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public int getCinemaNum() {
		return cinemaNum;
	}
	public void setCinemaNum(int cinemaNum) {
		this.cinemaNum = cinemaNum;
	}
	public int getSeatAmount() {
		return seatAmount;
	}
	public void setSeatAmount(int seatAmount) {
		this.seatAmount = seatAmount;
	}
	public String getSeatImg() {
		return seatImg;
	}
	public void setSeatImg(String seatImg) {
		this.seatImg = seatImg;
	}
	public boolean isSeatChk() {
		return SeatChk;
	}
	public void setSeatChk(boolean SeatChk) {
		this.SeatChk = SeatChk;
	}
	@Override
	public String toString() {
		return seatNum;
	}
	
}
