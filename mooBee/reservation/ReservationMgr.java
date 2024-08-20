package reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import DAO.DBConnectionMgr;
import movie.MovieBean;

public class ReservationMgr {

	private DBConnectionMgr pool;

	public ReservationMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// 예매 post
	public boolean insertRsvn(ReservationBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO tblreservation (userID, cinemaNum, RSVDATE, docid, seatID, price, ageGroup) VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getUserId());
			pstmt.setInt(2, bean.getCinemaNum());
			pstmt.setString(3, bean.getRSVDATE());
			pstmt.setInt(4, bean.getDocid());
			pstmt.setInt(5, bean.getSeatId());
			pstmt.setInt(6, bean.getPrice());
			pstmt.setString(7, bean.getAgeGroup());
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
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
			if (rs.next()) {
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

	// 해당 유저의 동일한 예약 내역 조회
	public int getSumUser(int cinemaNum, String viewDate, int docid, String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int cnt = 0;

		try {
			con = pool.getConnection();
			sql = "select count(*) from tblreservation where cinemaNum = ? and viewDate =? and docId = ? and userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			pstmt.setString(2, viewDate);
			pstmt.setInt(3, docid);
			pstmt.setString(4, userId);
			rs = pstmt.executeQuery();
			if (rs.next())
				cnt = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return cnt;
	}

	// 영화관 찾기
	public String getCinema(int cinemaNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String cinemaName = null;

		try {
			con = pool.getConnection();
			sql = "select cinemaName from tblCinema where cinemaNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cinemaName = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return cinemaName;
	}

	// 아이디별 예매 내역
	public Vector<ReservationBean> listRSVNUserId(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReservationBean> vlist = new Vector<ReservationBean>();
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
	// 아이디별 예매 내역(중복 제거) --> 이 코드는 나중에 지울 것.
	public Vector<ReservationBean> distinctRSVNUserId(String userId) {
		String sql = "SELECT RSVNNum, userId, cinemaNum, viewDate, docid, seatId AS seatId, price, ageGroup FROM tblreservation WHERE userId = ?";
		Vector<ReservationBean> vlist = new Vector<>();
		try (Connection con = pool.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ReservationBean bean = new ReservationBean();
					bean.setRSVNNum(rs.getInt("RSVNNum"));
					bean.setDocid(rs.getInt("docid"));
					bean.setViewDate(rs.getString("viewDate"));
					bean.setCinemaNum(rs.getInt("cinemaNum"));
					bean.setPrice(rs.getInt("price"));
					if (bean != null) {
						vlist.add(bean);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vlist;
	}

	// 유저의 예약 상세 내용을 조회
	public String getSeatNum(String userId, int RSVNNum) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ReservationBean bean = null;
	    String seat = null;
	    
	    try {
	        con = pool.getConnection();

	        // 예약 정보와 좌석 번호를 조인하여 가져오는 쿼리
	        String sql = "SELECT s.seatNum FROM tblreservation r JOIN tblseat s ON r.seatId = s.seatId WHERE r.userId = ? AND r.RSVNNum = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        pstmt.setInt(2, RSVNNum);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	seat = rs.getString(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt, rs);
	    }
	    return seat;
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
		} finally 
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}	
  
// 예매한 영화(docid)를 통해 영화 정보 가져오기
  public MovieBean getMovieData(int docid) {
		MovieBean bean = new MovieBean();
		try {
			con = pool.getConnection();
			sql = "select title, posterUrl from tblmovie where docid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setTitle(rs.getString(1));
				bean.setPosterUrl(rs.getString(2));
      }}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return bean;
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
			if (pstmt.executeUpdate() == 1)
				flag = true;
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
		Vector<ReservationBean> vlist = mgr.listRSVNUserId("root");

	}

}
