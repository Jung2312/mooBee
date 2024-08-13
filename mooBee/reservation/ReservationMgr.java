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
            sql = "insert into tbluser values (?,?,?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bean.getRSVNNum());
            pstmt.setString(2, bean.getUserId());
            pstmt.setInt(3, bean.getCinemaNum());
            pstmt.setString(4, bean.getViewDate());
            pstmt.setString(5, bean.getRSVDATE());
            pstmt.setInt(6, bean.getDocid());
            pstmt.setInt(7, bean.getSeatId());
            pstmt.setInt(8, bean.getPrice());
            pstmt.setString(9, bean.getAgeGroup());
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
	//영화관, 관람 시간 ,영화, 좌석 번호로 예매내역확인
		public Vector<ReservationBean> listRSVN(int cinemaNum, String viewDate,int docid, int seatId){
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<ReservationBean>vlist = new Vector<ReservationBean>();
			try {
				con = pool.getConnection();
				sql = "select * from tblreservation where cinemaNum = ?,viewDate =? ,docId = ?, seatId =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cinemaNum);
				pstmt.setString(2, viewDate);
				pstmt.setInt(3, docid);
				pstmt.setInt(4, seatId);
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
