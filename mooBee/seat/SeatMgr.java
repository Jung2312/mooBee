package seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;
import user.UserBean;

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
			sql = "select seatNum,seatId,seatChk from tblseat where cinemaNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SeatBean bean = new SeatBean();
				bean.setSeatNum(rs.getString(1));
				bean.setSeatId(rs.getInt(2));
				bean.setSeatChk(rs.getBoolean(3));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//좌석 선택되어있는지여부
	public SeatBean getSeat(int seatId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		SeatBean bean = new SeatBean();
		try {
			con = pool.getConnection();
			sql = "select seatChk from tblseat where seatId = ?";
		 	pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setSeatChk(rs.getBoolean(1));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	//좌석 결제시 선택했다고 설정
	public boolean updateSeatChk(SeatBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update  tblseat SET SeatChk = ? WHERE SeatId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setBoolean(1, bean.isSeatChk());
			pstmt.setInt(2, bean.getSeatId());
			if(pstmt.executeUpdate()==1) flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	public static void main(String[] args) {
		SeatBean bean = new SeatBean();
		SeatMgr mgr = new SeatMgr();
	}
}
