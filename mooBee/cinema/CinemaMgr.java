package cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class CinemaMgr {
	
	private DBConnectionMgr pool;
	
	public CinemaMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	//전체 영화관 리스트
	public Vector<CinemaBean> listCinema() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CinemaBean>vlist = new Vector<CinemaBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblCinema";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CinemaBean bean = new CinemaBean();
				bean.setCinemaNum(rs.getInt(1));
				bean.setCinemaName(rs.getString(2));
				bean.setCinemaLocation(rs.getString(3));
				bean.setTheaterNum(rs.getInt(4));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return vlist;
	}
	//영화관별 관 리스트
	public Vector<CinemaBean> listCinemaName(String cinemaName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CinemaBean>vlist = new Vector<CinemaBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblcinema where cinemaName = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cinemaName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CinemaBean bean = new CinemaBean();
				bean.setCinemaNum(rs.getInt(1));
				bean.setCinemaName(rs.getString(2));
				bean.setCinemaLocation(rs.getString(3));
				bean.setTheaterNum(rs.getInt(4));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return vlist;
	}
	public CinemaBean getCinema(int cinemaNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		CinemaBean bean = new CinemaBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblcinema where cinemaNum = ?";
		 	pstmt = con.prepareStatement(sql);
		 	pstmt.setInt(1, cinemaNum);
		 	rs = pstmt.executeQuery();
		 	if(rs.next()) {
		 		bean.setCinemaNum(rs.getInt(1));
		 		bean.setCinemaName(rs.getString(2));
		 		bean.setCinemaLocation(rs.getString(3));
		 		bean.setTheaterNum(rs.getInt(4));
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return bean;
	}
	
	public static void main(String[] args) {
		CinemaBean bean = new CinemaBean();
		CinemaMgr mgr = new CinemaMgr();
	
	}

}
