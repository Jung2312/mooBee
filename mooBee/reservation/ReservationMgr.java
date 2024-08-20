package reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class ReservationMgr {
	
	private DBConnectionMgr pool;
	
	public ReservationMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//예매 post 
	public boolean insertRsvn(ReservationBean bean){
		Connection con = null;
		PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "INSERT INTO tblreservation (userID, cinemaNum, ViewDate, docid, seatID, price, ageGroup) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getUserId());
            pstmt.setInt(2, bean.getCinemaNum());
            pstmt.setString(3, bean.getViewDate());
            pstmt.setInt(4, bean.getDocid());
            pstmt.setInt(5, bean.getSeatId());
            pstmt.setInt(6, bean.getPrice());
            pstmt.setString(7, bean.getAgeGroup());
            int cnt = pstmt.executeUpdate(); 
            if(cnt == 1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        return flag;
    }
	
	//RSVNNum을 이용해 예매 내역 한개 조회
	public ReservationBean getRsvn(int RSVNNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ReservationBean bean = new ReservationBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblreservation where RSVNNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, RSVNNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setRSVNNum(rs.getInt(1));
				bean.setUserId(rs.getString(2));
				bean.setCinemaNum(rs.getInt(3));
				bean.setViewDate(rs.getString(4));
				bean.setRSVDATE(rs.getString(5));
				bean.setDocid(rs.getInt(6));
				bean.setSeatId(rs.getInt(7));
				bean.setPrice(rs.getInt(8));
				bean.setAgeGroup(rs.getString(9));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	//아이디별 예매 내역
	public Vector<ReservationBean> listRSVNUserId(String userId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReservationBean>vlist = new Vector<ReservationBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblreservation where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				ReservationBean bean = new ReservationBean();
				bean.setRSVNNum(rs.getInt(1));
				bean.setUserId(rs.getString(2));
				bean.setCinemaNum(rs.getInt(3));
				bean.setViewDate(rs.getString(4));
				bean.setRSVDATE(rs.getString(5));
				bean.setDocid(rs.getInt(6));
				bean.setSeatId(rs.getInt(7));
				bean.setPrice(rs.getInt(8));
				bean.setAgeGroup(rs.getString(9));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//영화관, 관람 시간 ,영화로 예매내역확인
	public Vector<ReservationBean> listRSVN(int cinemaNum, String viewDate,int docid){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReservationBean>vlist = new Vector<ReservationBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblreservation where cinemaNum = ?,viewDate =? ,docId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			pstmt.setString(2, viewDate);
			pstmt.setInt(3, docid);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				ReservationBean bean = new ReservationBean();
				bean.setRSVNNum(rs.getInt(1));
				bean.setUserId(rs.getString(2));
				bean.setCinemaNum(rs.getInt(3));
				bean.setViewDate(rs.getString(4));
				bean.setRSVDATE(rs.getString(5));
				bean.setDocid(rs.getInt(6));
				bean.setSeatId(rs.getInt(7));
				bean.setPrice(rs.getInt(8));
				bean.setAgeGroup(rs.getString(9));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	public Vector<ReservationBean> listAgeGroup(int cinemaNum){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReservationBean>vlist = new Vector<ReservationBean>();
		try {
			con = pool.getConnection();
			sql = "select ageGroup from tblreservation where cinemaNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				ReservationBean bean = new ReservationBean();
				bean.setAgeGroup(rs.getString(1));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}	
	public ReservationBean getTemp(int seatId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ReservationBean bean = new ReservationBean();
		try {
			con = pool.getConnection();
			sql = "select u.temp "
					+ "from tbluser u "
					+ "JOIN tblreservation r ON r.userId = u.userId "
					+ "where r.seatId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setTemp(rs.getDouble(1));
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	//예매 삭제
	public boolean deleteRsvn(int RSVNNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from tblreservation where RSVNNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, RSVNNum);
			if(pstmt.executeUpdate()==1) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public static void main(String[] args) {
		ReservationBean bean = new ReservationBean();
		ReservationMgr mgr = new ReservationMgr();

	}

}
