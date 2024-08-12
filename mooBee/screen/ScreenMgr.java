package screen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class ScreenMgr {
	
	private DBConnectionMgr pool;
	
	public ScreenMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//좌석 리스트
	public Vector<ScreenBean> listScrean() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblScrean";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScreenBean bean = new ScreenBean();
				bean.setScreenNum(rs.getInt(1));
				bean.setCinemaNum(rs.getInt(2));
				bean.setDocid(rs.getInt(3));
				bean.setScreenDate(rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return vlist;
	}
	
	public static void main(String[] args) {
		
	}
}
