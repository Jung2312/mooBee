package reservation;

public class ReservationBean {
	private int RSVNNum;
	private String userId;
	private int cinemaNum;
	private String viewDate;
	private String RSVDATE;
	private int docid;
	private int seatId;
	private int price;
	private String ageGroup;
	public int getRSVNNum() {
		return RSVNNum;
	}
	public void setRSVNNum(int rSVNNum) {
		RSVNNum = rSVNNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCinemaNum() {
		return cinemaNum;
	}
	public void setCinemaNum(int cinemaNum) {
		this.cinemaNum = cinemaNum;
	}
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	public String getRSVDATE() {
		return RSVDATE;
	}
	public void setRSVDATE(String rSVDATE) {
		RSVDATE = rSVDATE;
	}
	public int getDocid() {
		return docid;
	}
	public void setDocid(int docid) {
		this.docid = docid;
	}
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	
}
