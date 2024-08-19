package review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class ReviewMgr {
	
	private DBConnectionMgr pool;
	
	public ReviewMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//리뷰 작성
	public boolean insertReview(ReviewBean bean) {
		Connection con = null;
		PreparedStatement pstmt =null;
		String sql = null;
		boolean flag =false;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO tblreview (userId, title, content, docid, scope, recommend) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,bean.getUserId());
			pstmt.setString(2,bean.getTitle());
			pstmt.setString(3,bean.getContent());
			pstmt.setInt(4,bean.getDocid());
			pstmt.setInt(5,bean.getScope());
			pstmt.setInt(6,bean.getRecommend());
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
	//리뷰 리스트
	public Vector<ReviewBean> listReview(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReviewBean>vlist = new Vector<ReviewBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblreview ORDER BY reviewNum DESC;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				ReviewBean bean = new ReviewBean();
				bean.setReviewNum(rs.getInt(1)); //1은 첫번째 컬럼
				bean.setUserId(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setContent(rs.getString(4));
				bean.setDocid(rs.getInt(5));
				bean.setReviewDate(rs.getString(6));
				bean.setScope(rs.getInt(7));
				bean.setRecommend(rs.getInt(8));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	// 한명의 리뷰 리스트
	public Vector<ReviewBean> findMemberReview(String userId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ReviewBean>vlist = new Vector<ReviewBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblreview where userId = ? ORDER BY reviewNum DESC;";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) { 
				ReviewBean bean = new ReviewBean();
				bean.setReviewNum(rs.getInt(1)); //1은 첫번째 컬럼
				bean.setUserId(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setContent(rs.getString(4));
				bean.setDocid(rs.getInt(5));
				bean.setReviewDate(rs.getString(6));
				bean.setScope(rs.getInt(7));
				bean.setRecommend(rs.getInt(8));
				vlist.addElement(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	//리뷰 한개
	public ReviewBean getReview(int reviewNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ReviewBean bean = new ReviewBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblreview where reviewNum = ?";
		 	pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reviewNum);
			rs = pstmt.executeQuery();
			if(rs.next()) { //2개 이상의 레코드는 절대 리턴되지않음
				bean.setReviewNum(rs.getInt(1)); //1은 첫번째 컬럼
				bean.setUserId(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setContent(rs.getString(4));
				bean.setDocid(rs.getInt(5));
				bean.setReviewDate(rs.getString(6));
				bean.setScope(rs.getInt(7));
				bean.setRecommend(rs.getInt(8));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	
	//리뷰 수정
	public boolean updateReview(ReviewBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update  tblreview set title = ?, content = ?, scope =?, recommend =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,bean.getTitle());
			pstmt.setString(2,bean.getContent());
			pstmt.setInt(3,bean.getScope());
			pstmt.setInt(4,bean.getRecommend());
			if(pstmt.executeUpdate()==1) flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	//리뷰 삭제
	public boolean deleteReview(int reviewNum) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = null;
			boolean flag = false;
			try {
				con = pool.getConnection();
				sql = "delete from tblreview where reviewNum = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, reviewNum);
				if(pstmt.executeUpdate()==1) flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
			return flag;
	}
	
	public static void main(String[] args) {
		ReviewBean bean = new ReviewBean();
		ReviewMgr mgr = new ReviewMgr();

	}

}
