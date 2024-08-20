package temp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class TempMgr {
	
	private DBConnectionMgr pool;
	
	public TempMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public Vector<TempBean> listTemp(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<TempBean>vlist = new Vector<TempBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tbltemp";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				TempBean bean = new TempBean();
				bean.setCriterion(rs.getString(1)); 
				bean.setDeductedTemp(rs.getFloat(2));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	// 지난 기간의 경고 내역 삭제
	public void deleteWarning(String userId) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        con = pool.getConnection();
	        // 현재 날짜로부터 6개월 이전의 경고를 삭제
	        sql = "DELETE FROM tblWarning WHERE userId = ? AND warningDate < DATE_SUB(CURDATE(), INTERVAL 3 MONTH)";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, userId);

	        // executeUpdate()는 삭제된 행의 수를 반환
	        if (pstmt.executeUpdate() > 0) {
	            updateTemp(userId); // 삭제 후 추가 작업 수행
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt); // con은 반납, pstmt는 close
	    }
	}

	// 해당 유저의 경고 내역 유무 판별
	public int getWarning(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int cnt = 0;
		try {
			con = pool.getConnection();
			sql = "select count(*) from tblWarning where userId = ?";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
				cnt += rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return cnt;
	}
	
	// 신고 내역이 없는 경우 유저 온도 갱신
	public boolean updateTemp(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		if(getWarning(userId) == 0) {
			try {
				con = pool.getConnection();
				sql = "update tbluser set temp = temp ? where userId = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, +1);
				pstmt.setString(2, userId);
				if (pstmt.executeUpdate() == 1)
					flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt); // con은 반납, pstmt는 close
			}
		}
		
		return flag;
	}
	
	// 경고 차감 온도 구하기
	public int getCriterion(String criterion) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int temp = 0;
		try {
			con = pool.getConnection();
			sql = "select deductedTemp from tbltemp where criterion = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, criterion);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				temp = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return temp;
	}

	
	// 해당 좌석을 사용한 유저 찾기
	public String findMember(int cinemaNum, String date, int docid, int seatId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String userId = null;
		
		try {
			con = pool.getConnection();
			System.out.println(docid);
			System.out.println(cinemaNum);
			System.out.println(seatId);
			sql = "select userId from tblreservation where cinemaNum = ? and viewDate = ? and docid = ? and seatId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cinemaNum);
			pstmt.setString(2, date);
			pstmt.setInt(3, docid);
			pstmt.setInt(4, seatId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				userId = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
	
		return userId;
	}
	
	// 해당 좌석의 번호 찾기
	public int findSeatId(String seatNum, int cinemaNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int seatId = 0;
		try {
			con = pool.getConnection();
			sql = "select seatId from tblseat where seatNum = ? and cinemaNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, seatNum);
			pstmt.setInt(2, cinemaNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				seatId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return seatId;
	}
	
	// 유저의 온도 변경
	public boolean deductedTemp(String criterion, String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "update tbluser set temp = temp ? where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, getCriterion(criterion));
			pstmt.setString(2, userId);
			if (pstmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); // con은 반납, pstmt는 close
		}
		return flag;
	}
	
	// 경고 내역 추가
	public boolean insertWarning(String criterion, int cinemaNum, String date, int docid, String seatNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		String userId = findMember(cinemaNum, date, docid, findSeatId(seatNum, cinemaNum));
		
		try {
			con = pool.getConnection();
			sql = "insert INTO tblwarning (criterion, userId) values (?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, criterion);
			pstmt.setString(2,  userId);
			
			if (pstmt.executeUpdate() == 1 && deductedTemp(criterion, userId) == true)
				flag = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); // con은 반납, pstmt는 close
		}
		return flag;
	}
	
	
	
	public static void main(String[] args) {
		TempBean bean = new TempBean();
		TempMgr mgr = new TempMgr();
		//mgr.insertWarning("좌석 발로차기", 1, "2024-08-19", 36, "A11");
	}

}
