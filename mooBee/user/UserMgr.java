package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class UserMgr {
	
	private DBConnectionMgr pool;
	
	public UserMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//저장 post
	public boolean insertUser(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt =null;
		String sql = null;
		boolean flag =false;
		try {
			con = pool.getConnection();
			sql = "insert tbluser values (?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,bean.getUserId());
			pstmt.setString(2,bean.getName());		
			pstmt.setString(3,bean.getPassword());
			pstmt.setInt(4, 0);
			pstmt.setString(5,bean.getBirthDate());
			pstmt.setString(6,bean.getPhone());
			pstmt.setString(7, "Bronze");
			pstmt.setFloat(8, 36.5f);
			pstmt.setBoolean(9, false);
			int cnt = pstmt.executeUpdate(); 
			if(cnt ==1)
				flag = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con,pstmt);
		}
		
		return flag;
	}
	
	//리스트
	public Vector<UserBean> listUser(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<UserBean>vlist = new Vector<UserBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tbluser";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				UserBean bean = new UserBean();
				bean.setUserId(rs.getString(1));
				bean.setName(rs.getString(2));
				bean.setBirthDate(rs.getString(3));
				bean.setPhone(rs.getString(4));
				bean.setPaymentAmount(rs.getInt(5));
				bean.setGrade(rs.getString(6));
				bean.setTemp(rs.getFloat(7));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//select 검색
	public UserBean getUser(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		UserBean bean = new UserBean();
		try {
			con = pool.getConnection();
			sql = "select * from tbluser where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) { //2개 이상의 레코드는 절대 리턴되지않음
				bean.setUserId(rs.getString(1)); //1은 첫번째 컬럼
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setPhone(rs.getString(4));
				bean.setBirthDate(rs.getString(5));
				bean.setPaymentAmount(rs.getInt(6));
				bean.setGrade(rs.getString(7));
				bean.setTemp(rs.getFloat(8));
				bean.setManager(rs.getInt(9));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	//update 수정
	public boolean updateUser(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update  tbluser set name =?, password = ?, birthDate = ? ,phone = ? WHERE userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2,bean.getPassword());
			pstmt.setString(3,bean.getBirthDate());
			pstmt.setString(4,bean.getPhone());
			pstmt.setString(5, bean.getUserId());
			if(pstmt.executeUpdate()==1) flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	//delete 삭제
	public boolean deleteUser(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from tblUser where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);;
			if(pstmt.executeUpdate()==1) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
}
	public static void main(String[] args) {
		UserMgr mgr = new UserMgr();
		UserBean bean = new UserBean();
	}
}
