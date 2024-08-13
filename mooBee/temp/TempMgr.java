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
	public static void main(String[] args) {
		TempBean bean = new TempBean();
		TempMgr mgr = new TempMgr();

	}

}
