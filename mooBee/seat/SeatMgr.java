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
	public Vector<SeatBean> listSeat() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<SeatBean>vlist = new Vector<SeatBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblseat";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SeatBean bean = new SeatBean();
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
		
		return vlist;
		
	}
	
	public static void main(String[] args) {
		SeatBean bean = new SeatBean();
		SeatMgr mgr = new SeatMgr();
	}
}
