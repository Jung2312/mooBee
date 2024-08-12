package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLConnection {

	private DBConnectionMgr pool;
	
	public MySQLConnection() {
		try {
			pool = DBConnectionMgr.getInstance();
			Connection con = pool.getConnection();
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MySQLConnection mm = new MySQLConnection();
		mm.selectAll();
	}
	
	public void  selectAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "select count(*) from tblmembership";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				System.out.println("cnt : " + cnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

}
