package movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import DAO.DBConnectionMgr;

public class MovieMgr {

	private DBConnectionMgr pool;
	
	public MovieMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	// kmdb 데이터 저장
	// 인수1. releaseDte = 현재날짜
	// 인수2. listCount = 10
	
	
	// 상영 중인 영화 리스트 출력
	public Vector<MovieBean> listMember() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MovieBean> vlist = new Vector<MovieBean>();
		try {
			con = pool.getConnection();
			sql = "select * from tblmovie";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MovieBean bean = new MovieBean();
				
				bean.setDocid(rs.getInt(1));
				bean.setMovieSeq(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setDirectorNm(rs.getString(4));
				bean.setActorNm(rs.getString(5));
				bean.setPlot(rs.getString(6));
				bean.setGenre(rs.getString(7));
				bean.setReleaseDate(rs.getString(8));
				bean.setRubtime(rs.getString(9));
				bean.setPosterUrl(rs.getString(10));
				bean.setVodUrl(rs.getString(11));
				bean.setAudiAcc(rs.getString(12));
				bean.setRating(rs.getString(13));
				
				/* 영화진흥원 api에서 가져오기
				 * setboxofficeType setruum setrank setmovieCd setmovieNm setopenDt setaudiAcc
				 */

				vlist.addElement(bean); // 리턴 안하는 경우 사용
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return vlist;
	}
	
	// 선택한 하나의 영화 상세 정보 출력
	public MovieBean getMovie(int docid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MovieBean bean = new MovieBean();
		try {
			con = pool.getConnection();
			sql = "select * from tblmovie where docid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			rs = pstmt.executeQuery();
			if(rs.next()) { // 하나의 레코드만 리턴
				bean.setDocid(rs.getInt("docid"));
				bean.setTitle(rs.getString("title"));
				bean.setPosterUrl(rs.getString("posterUrl"));
				bean.setVodUrl(rs.getString("vodUrl"));
				bean.setReleaseDate(rs.getString("releaseDate"));
				bean.setRubtime(rs.getString("rubTime"));
				bean.setDirectorNm(rs.getString("directorNm"));
				bean.setActorNm(rs.getString("actorNm"));
				bean.setPlot(rs.getString("plot"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return bean;
	}
}
