package notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class NoticeMgr {
	
	private DBConnectionMgr pool;
	
	public NoticeMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	//공지 작성
	public boolean insertNotice(NoticeBean bean) {
		Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        try {
            con = pool.getConnection();
            sql = "insert into tblnotice values (null,?,?,?,null)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, bean.getTitle());
            pstmt.setString(2, bean.getContent());
            pstmt.setString(3,bean.getNoticeImg());
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
	// 모든 공지사항 조회 메서드
    public Vector<NoticeBean> listNotice() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        Vector<NoticeBean> vlist = new Vector<NoticeBean>();
        try {
            con = pool.getConnection();
            sql = "SELECT * FROM tblnotice";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                NoticeBean bean = new NoticeBean();
                bean.setNoticeNum(rs.getInt(1));
                bean.setTitle(rs.getString(2));
                bean.setContent(rs.getString(3));
                bean.setNoticeImg(rs.getString(4));
                bean.setNoticeDate(rs.getString(5));
                vlist.addElement(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return vlist;
    }
	//공지사항 한개 조회
    public NoticeBean getNotice(int noticeNum) {
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		NoticeBean bean = new NoticeBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblnotice where noticeNum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, noticeNum);
			rs = pstmt.executeQuery();
			if(rs.next()) { //2개 이상의 레코드는 절대 리턴되지않음
				bean.setNoticeNum(rs.getInt(1));
				bean.setTitle(rs.getNString(2));
				bean.setContent(rs.getString(3));
				bean.setNoticeImg(rs.getString(4));
				bean.setNoticeDate(rs.getString(5));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
    	return bean;
    }
    // 공지사항 수정
    public boolean updateNotice(NoticeBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update  tblnotice set title = ?, content = ?, noticeImg = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,bean.getTitle());
			pstmt.setString(2,bean.getContent());
			pstmt.setString(3,bean.getNoticeImg());
			if(pstmt.executeUpdate()==1) flag =true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	//공지사항 삭제
	public boolean deleteNotice(int noticeNum) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = null;
			boolean flag = false;
			try {
				con = pool.getConnection();
				sql = "delete from tblmember where noticeNum = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, noticeNum);
				if(pstmt.executeUpdate()==1) flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
			return flag;
	}

	public static void main(String[] args) {
		NoticeMgr mgr = new NoticeMgr();
		NoticeBean bean = new NoticeBean();
	}

}
