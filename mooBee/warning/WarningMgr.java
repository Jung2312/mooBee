package warning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class WarningMgr {
	
	private DBConnectionMgr pool;
	
	public WarningMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	public Vector<WarningBean> listWarning(String userId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<WarningBean>vlist = new Vector<WarningBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblwarning where userId =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				WarningBean bean = new WarningBean();
				bean.setWarningNum(rs.getInt(1)); 
				bean.setWarningDate(rs.getString(2));
				bean.setCriterion(rs.getString(3));
				bean.setUserId(rs.getString(4));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	

	public static void main(String[] args) {
		WarningMgr mgr = new WarningMgr();
		WarningBean bean = new WarningBean();
	}

}
