package movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import DAO.Config;
import DAO.DBConnectionMgr;

public class MovieMgr {

	private DBConnectionMgr pool;
	// 요청(Request) 요청 변수
	private final static String getListURL = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
	private final static String getBoxOfficeURL = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
	
	// api key
	String apiKEY = Config.apiKEY;

	private StringBuilder response;
	private String apiUrl;
	
	public MovieMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// kmdb 데이터 저장 메소드 추가 필요
	// 인수1. releaseDte = 현재날짜
	// 인수2. listCount = 10

	// http를 통해 api 응답을 저장하는 메소드
	public void getResponse(String fullApiUrl) {
		try {
			URL url = new URL(fullApiUrl);
			// http 연결
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			// 200이 아닌 경우 오류 메시지 출력
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("HTTP GET Request Failed with Error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			response = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			br.close();
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 영화 진흥원의 영화 대표 코드 검색
	public String getMovieCd(String title, String directorNm) {
		apiUrl = getListURL + "?key=" + apiKEY;
		String movieCd = null;
		try {
			// URL Encoding
			String encodedMovieNm = java.net.URLEncoder.encode(title, "UTF-8");
			String encodedDirectorNm = java.net.URLEncoder.encode(directorNm, "UTF-8");

			String fullApiUrl = apiUrl + "&movieNm=" + encodedMovieNm + "&directorNm=" + encodedDirectorNm;
			
			getResponse(fullApiUrl);

			// json parse
			JSONObject jsonResponse = new JSONObject(response.toString());

			// "movieListResult" 존재 여부 확인
			if (jsonResponse.has("movieListResult")) {
				JSONObject movieListResult = jsonResponse.getJSONObject("movieListResult");

				// "movieList" 존재 여부 확인
				if (movieListResult.has("movieList")) {
					JSONArray movieList = movieListResult.getJSONArray("movieList");
					// 빈 결과가 아니라면 영화 코드 추출
					if (movieList.length() > 0) {
						JSONObject movie = movieList.getJSONObject(0);
						movieCd = movie.getString("movieCd");
					} else {
						System.out.println("결과가 없습니다.");
					}
				} else {
					System.out.println("No 'movieList' field in response.");
				}
			} else {
				System.out.println("No 'movieListResult' field in response.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieCd;
	}

	
	// 박스오피스 전날 일별 순위 날짜 포맷팅
    public static String getYesterdayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);  // -1일 기준으로 계산

        Date yesterday = calendar.getTime();

        return sdf.format(yesterday);
    }
	
    // 일별 박스오피스 순위 api 값 추출
	public void getBoxOfficeData(MovieBean bean) {
		String movieCd = bean.getMovieCd();
		apiUrl = getBoxOfficeURL + "?key=" + apiKEY;
		try { 
			
			// URL Encoding
			String encodedMovieCd = java.net.URLEncoder.encode(movieCd, "UTF-8");

			String fullApiUrl = apiUrl + "&movieCd=" + encodedMovieCd + "&targetDt=" + getYesterdayDate();
			
			getResponse(fullApiUrl);

			// json parse
			JSONObject jsonResponse = new JSONObject(response.toString());

			// "boxOfficeResult" 존재 여부 확인
			if (jsonResponse.has("boxOfficeResult")) {
				JSONObject boxOfficeResult = jsonResponse.getJSONObject("boxOfficeResult");

				// "movieList" 존재 여부 확인
				if (boxOfficeResult.has("dailyBoxOfficeList")) {
					JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
					// 빈 결과가 아니라면 영화 코드 추출
					for (Object object : dailyBoxOfficeList) {
						bean.setBoxofficeType(jsonResponse.getString("boxofficeType"));
						bean.setRuum(jsonResponse.getString("rnum"));
						bean.setRank(jsonResponse.getString("rank"));
						bean.setMovieCd(jsonResponse.getString("movieCd"));
						bean.setMovieNm(jsonResponse.getString("movieNm"));
						bean.setOpenDt(jsonResponse.getString("openDt"));
					}
				} else {
					System.out.println("No 'dailyBoxOfficeList' field in response.");
				}
			} else {
				System.out.println("No 'boxOfficeResult' field in response.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 상영 중인 영화 리스트 출력
	public Vector<MovieBean> listMovie() {
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

				/*
				 * 영화진흥원 영화목록 조회 API 서비스와 movie 테이블에서 영화 제목, 감독명 비교 후 movieCd 가져와서 일별 박스오피스 API
				 * 서비스에서 이용 setboxofficeType setruum setrank setmovieCd setmovieNm setopenDt
				 * setaudiAcc
				 */

				String movieCd = getMovieCd(bean.getTitle(), bean.getDirectorNm()); // 영화진흥원 대표 코드 가져오기
				bean.setMovieCd(movieCd);
				// getBoxOfficeData(bean); // 영화진흥원 박스오피스 가져오기
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
			if (rs.next()) { // 하나의 레코드만 리턴
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
