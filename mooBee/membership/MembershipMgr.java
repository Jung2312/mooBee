package membership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DAO.DBConnectionMgr;

public class MembershipMgr {
	
	private DBConnectionMgr pool;
	
	public MembershipMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//등급에 따라 검색
	public membershipBean getmembership(String grade) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		membershipBean bean = new membershipBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblmembership where grade = ?";
		 	pstmt = con.prepareStatement(sql);
			pstmt.setString(1, grade);
			rs = pstmt.executeQuery();
			if(rs.next()) { 
				bean.setGrade(rs.getString(1)); 
				bean.setPaymentCriteria(rs.getInt(2));
				bean.setDiscount(rs.getFloat(3));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	public static void main(String[] args) {
		membershipBean bean = new membershipBean();
		MembershipMgr mgr = new MembershipMgr();
	}

}
