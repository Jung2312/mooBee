package seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class SeatMgr {
	
	private DBConnectionMgr pool;
	
	public SeatMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//좌석 리스트
	public Vector<SeatBean> listSeat(int cinemaNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SeatBean>vlist = new Vector<SeatBean>();
		try {
			con = pool.getConnection();
			sql = "select seatNum from tblseat where cinemaNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SeatBean bean = new SeatBean();
				bean.setSeatNum(rs.getString(1));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	public SeatBean getSeat(int seatId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		SeatBean bean = new SeatBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblseat where seatId = ?";
		 	pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatId);
			rs = pstmt.executeQuery();
			if(rs.next()) { //2개 이상의 레코드는 절대 리턴되지않음
				bean.setSeatId(rs.getInt(1));
				bean.setSeatNum(rs.getString(2));
				bean.setCinemaNum(rs.getInt(3));
				bean.setSeatAmount(rs.getInt(4));
				bean.setSeatImg(rs.getString(5));
				bean.setSeatView(rs.getBoolean(6));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	public static void main(String[] args) {
		SeatBean bean = new SeatBean();
		SeatMgr mgr = new SeatMgr();
	}
}
