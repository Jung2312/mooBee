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
	//상영 리스트(영화)
	public Vector<ScreenBean> listScreanMovie() {
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
	//상영 리스트 (영화관)
	public Vector<ScreenBean> listScreanCinema(int docid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblScrean where docid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
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
	//상영 리스트 (날짜)
	public Vector<ScreenBean> listScreanDate(int docid, int cinemaNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblScrean where docid = ?, cinemaNum = ?";
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
	//상영 리스트 (영화 시간)
	public Vector<ScreenBean> listScreanTime(int docid, int cinemaNum, String screenDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ScreenBean>vlist = new Vector<ScreenBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblScrean where docid = ?, cinemaNum =?, screenDate = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			pstmt.setInt(2, cinemaNum);
			pstmt.setString(3, screenDate);
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
