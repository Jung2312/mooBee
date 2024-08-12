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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return vlist;
		
	}
	public static void main(String[] args) {
		CinemaBean bean = new CinemaBean();
		CinemaMgr mgr = new CinemaMgr();

	}

}
