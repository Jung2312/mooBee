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
	//상영 리스트(영화)
	public Vector<ScreenBean> listScreenMovie() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT DISTINCT m.title, s.docid " +
	                  "FROM tblScreen s " +
	                  "JOIN tblMovie m ON s.docid = m.docid " +
	                  "ORDER BY m.title";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScreenBean bean = new ScreenBean();
				bean.setTitle(rs.getString(1));
				bean.setDocid(rs.getInt(2));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//상영 리스트 (영화관)
	public Vector<ScreenBean> listScreenCinema(int docid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql =  "SELECT c.cinemaName, MIN(s.docid) AS docid, MIN(s.cinemaNum) AS cinemaNum " +
                    "FROM tblScreen s " +
                    "JOIN tblCinema c ON s.cinemaNum = c.cinemaNum " +
                    "JOIN tblMovie m ON s.docid = m.docid " +
                    "WHERE s.docid = ? " +
                    "GROUP BY c.cinemaName "+
                    "ORDER BY c.cinemaName";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScreenBean bean = new ScreenBean();
				bean.setCinemaName(rs.getString(1));
				bean.setDocid(rs.getInt(2));
				bean.setCinemaNum(rs.getInt(3));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//상영 리스트 (날짜)
	public Vector<ScreenBean> listScreenDate(int docid, String cinemaName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT DISTINCT s.screenDate, c.cinemaName, s.docid " +
		             "FROM tblScreen s " +
		             "JOIN tblCinema c ON s.cinemaNum = c.cinemaNum " +
		             "WHERE s.docid = ? AND c.cinemaName LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			pstmt.setString(2, cinemaName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ScreenBean bean = new ScreenBean();
				bean.setScreenDate(rs.getString(1));
				bean.setCinemaName(rs.getString(2));
				bean.setDocid(rs.getInt(3));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//상영 리스트 (영화 시간)
	public Vector<ScreenBean> listScreenTime(int docid, String cinemaName, String screenDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT * " +
					"FROM tblScreen s " +
					"JOIN tblCinema c ON s.cinemaNum = c.cinemaNum " +
					"WHERE s.docid = ? AND c.cinemaName = ? AND s.screenDate LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			pstmt.setString(2, cinemaName);
			pstmt.setString(3, "%"+screenDate+"%");
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
