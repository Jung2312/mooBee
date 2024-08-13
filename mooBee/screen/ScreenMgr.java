package screen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;
import review.ReviewBean;

public class ScreenMgr {
	
	private DBConnectionMgr pool;
	
	public ScreenMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//상영 리스트 (영화id , 영화관번호)
	public Vector<ScreenBean> listScrean(int docid , int cinemaNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblScrean where docid = ?, cinemaNum =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			pstmt.setInt(2, cinemaNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScreenBean bean = new ScreenBean();
				bean.setScreenNum(rs.getInt(1));
				bean.setCinemaNum(rs.getInt(2));
				bean.setDocid(rs.getInt(3));
				bean.setScreenDate(rs.getString(4));
				bean.setScreenTime(rs.getString(5));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	//해당 상영 번호로 검색
	public ScreenBean getScreen(int screenNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ScreenBean bean = new ScreenBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblscreen where screenNum= ?";
		 	pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, screenNum);
			rs = pstmt.executeQuery();
			if(rs.next()) { //2개 이상의 레코드는 절대 리턴되지않음
				bean.setScreenNum(rs.getInt(1));
				bean.setCinemaNum(rs.getInt(2));
				bean.setDocid(rs.getInt(3));
				bean.setScreenDate(rs.getString(4));
				bean.setScreenTime(rs.getString(5));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	public static void main(String[] args) {
		ScreenBean bean = new ScreenBean();
		ScreenMgr mgr = new ScreenMgr();
	}
}
