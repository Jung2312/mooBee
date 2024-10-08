package movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import DAO.Config;
import DAO.DBConnectionMgr;

public class MovieMgr {

	private DBConnectionMgr pool;
	// 요청(Request) 요청 변수
	private final static String getListURL = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
	private final static String getBoxOfficeURL = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
	private final static String getkmDBURL = "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2";

	// api key
	String apiKEY = Config.apiKEY;
	String apiKEY2 = Config.apiKEY2;
	String kmdbKEY = Config.kmdbKEY;

	private StringBuilder response;
	private String apiUrl;
	
	private String videoUrl;
	private String posterUrl;

	public MovieMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// 날짜 포맷팅
	public static String getDateFormat(int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -num); // -1일 기준으로 계산

		Date date = calendar.getTime();

		return sdf.format(date);
	}

	// 개봉일이 00일인 경우 포맷
	public static String adjustDate(String dateStr) throws DateTimeParseException {
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// 길이 맞춤
		if (dateStr.length() != 8) {
			throw new DateTimeParseException("Date string must be in 'yyyyMMdd' format", dateStr, 0);
		}

		// 년, 월, 일 자르기
		String yearStr = dateStr.substring(0, 4);
		String monthStr = dateStr.substring(4, 6);
		String dayStr = dateStr.substring(6, 8);
		String formatDate = null;

		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		int day = Integer.parseInt(dayStr);
		LocalDate date;
		// 일이 00일인지 확인
		if (day == 0) {
			date = LocalDate.of(year, month, day + 1);
		} else {
			date = LocalDate.of(year, month, day);
		}
		return date.format(outputFormatter);
	}

	// 인수1. releaseDte = 현재날짜
	// 인수2. listCount = 출력하고자 하는 페이지 수
	public boolean insertMovie() {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql;
		int listCount = 500; // 변경 가능
		boolean flag = false;
		String apiUrl = getkmDBURL + "&detail=Y&ServiceKey=" + kmdbKEY + "&listCount=" + listCount + "&releaseDts="
				+ getDateFormat(30) + "&releaseDte=" + getDateFormat(0);

		try {
			getResponse(apiUrl);

			// json parse
			JSONObject jsonResponse = new JSONObject(response.toString());

			// "Data" 필드가 있는지 확인
			if (jsonResponse.has("Data")) {
				JSONArray dataArray = jsonResponse.getJSONArray("Data");

				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject dataObject = dataArray.getJSONObject(i);

					// "Result" 필드가 있는지 확인
					if (dataObject.has("Result")) {
						JSONArray movieList = dataObject.getJSONArray("Result");

						for (int j = 0; j < movieList.length(); j++) {
							JSONObject movie = movieList.getJSONObject(j);

							String releaseDate = movie.optString("repRlsDate");
							releaseDate = adjustDate(releaseDate);

							con = pool.getConnection();

							// 영화 정보 추출
							String movieSeq = movie.optString("movieSeq");

							// 개봉일(30일)이 지난 경우 테이블에서 삭제
							sql = "SELECT releaseDate FROM tblmovie WHERE movieSeq = ?";
							try (PreparedStatement checkStmt = con.prepareStatement(sql)) {
								checkStmt.setString(1, movieSeq);
								ResultSet rs = checkStmt.executeQuery();
								if (rs.next()) {
									sql = "DELETE FROM tblmovie WHERE releaseDate < ?";
									pstmt = con.prepareStatement(sql);
									pstmt.setString(1, releaseDate);
									pstmt.executeUpdate();

									// 중복된 레코드가 있는 경우 삽입하지 않음
									continue;
								}
							}

							String posters = movie.optString("posters");
							String vodUrl = movie.optJSONObject("vods").optJSONArray("vod").optJSONObject(0)
									.optString("vodUrl");

							if (posters.equals("")) {
								continue;
							}

							String[] poster = posters.split("\\s*\\|\\s*|\\s*\\n\\s*");

							String title = movie.optString("title");
							String director = movie.optJSONObject("directors").optJSONArray("director").optJSONObject(0)
									.optString("directorNm");
							JSONArray actors = movie.optJSONObject("actors").optJSONArray("actor");
							String actorNm = actors.optJSONObject(0).optString("actorNm");
							if (actors.length() > 1) {
								actorNm += ("," + actors.optJSONObject(1).optString("actorNm"));
							}

							String plot = movie.optJSONObject("plots").optJSONArray("plot").optJSONObject(0)
									.optString("plotText");
							String genre = movie.optString("genre");
							String runtime = movie.optString("runtime");

							String audiAcc = movie.optString("audiAcc");
							String rating = movie.optJSONObject("ratings").optJSONArray("rating").optJSONObject(0)
									.optString("ratingGrade");

							if (rating.equals("")) {
								rating = "15세이상관람가";
							}

							sql = "insert into tblmovie VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, movieSeq);
							pstmt.setString(2, title);
							pstmt.setString(3, director);
							pstmt.setString(4, actorNm);
							pstmt.setString(5, plot);
							pstmt.setString(6, genre);
							pstmt.setString(7, releaseDate);
							pstmt.setString(8, runtime);
							pstmt.setString(9, poster[0]);
							pstmt.setString(10, vodUrl);
							pstmt.setString(11, audiAcc);
							pstmt.setString(12, rating);

							if (pstmt.executeUpdate() == 1) {
								flag = true;
							}

						}
					} else {
						System.out.println("No 'Result' field in Data object.");
					}
				}
			} else {
				System.out.println("No 'Data' field in response.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); // con은 반납, pstmt는 close
		}
		return flag;
	}

	// http를 통해 api 응답을 저장하는 메소드

	    // API 응답을 저장하는 메소드
	    public String getResponse(String fullApiUrl) {
	        try {
	            URL url = new URL(fullApiUrl);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");

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
	            return response.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

		/*
		 * // 영화 진흥원의 영화 대표 코드 검색 public CompletableFuture<String>
		 * getMovieCdAsync(String title, String directorNm) { return
		 * CompletableFuture.supplyAsync(() -> { String movieCd = null; try { String
		 * encodedMovieNm = URLEncoder.encode(title, "UTF-8"); String encodedDirectorNm
		 * = URLEncoder.encode(directorNm, "UTF-8"); String fullApiUrl = getListURL +
		 * "?key=" + apiKEY + "&movieNm=" + encodedMovieNm + "&directorNm=" +
		 * encodedDirectorNm;
		 * 
		 * String jsonResponseStr = getResponse(fullApiUrl); if (jsonResponseStr !=
		 * null) { JSONObject jsonResponse = new JSONObject(jsonResponseStr); if
		 * (jsonResponse.has("movieListResult")) { JSONObject movieListResult =
		 * jsonResponse.getJSONObject("movieListResult"); if
		 * (movieListResult.has("movieList")) { JSONArray movieList =
		 * movieListResult.getJSONArray("movieList"); if (movieList.length() > 0) {
		 * JSONObject movie = movieList.getJSONObject(0); movieCd =
		 * movie.getString("movieCd"); } } } } } catch (Exception e) {
		 * e.printStackTrace(); } return movieCd; }); }
		 * 
		 * // 일별 박스오피스 순위 API 값 추출 public CompletableFuture<Void>
		 * getBoxOfficeDataAsync(MovieBean bean) { return CompletableFuture.runAsync(()
		 * -> { try { String movieCd = bean.getMovieCd(); String fullApiUrl =
		 * getBoxOfficeURL + "?key=" + apiKEY2 + "&movieCd=" + movieCd + "&targetDt=" +
		 * getDateFormat(1);
		 * 
		 * String jsonResponseStr = getResponse(fullApiUrl); if (jsonResponseStr !=
		 * null) { JSONObject jsonResponse = new JSONObject(jsonResponseStr); if
		 * (jsonResponse.has("boxOfficeResult")) { JSONObject boxOfficeResult =
		 * jsonResponse.getJSONObject("boxOfficeResult"); if
		 * (boxOfficeResult.has("dailyBoxOfficeList")) { JSONArray dailyBoxOfficeList =
		 * boxOfficeResult.getJSONArray("dailyBoxOfficeList"); for (int i = 0; i <
		 * dailyBoxOfficeList.length(); i++) { JSONObject movieData =
		 * dailyBoxOfficeList.getJSONObject(i); if
		 * (movieData.getString("movieCd").equals(movieCd)) {
		 * bean.setBoxofficeType("일간 박스오피스"); bean.setRuum(movieData.optString("rnum"));
		 * bean.setRank(movieData.optString("rank"));
		 * bean.setMovieCd(movieData.optString("movieCd"));
		 * bean.setMovieNm(movieData.optString("movieNm"));
		 * bean.setOpenDt(movieData.optString("openDt"));
		 * bean.setAudiAcc(movieData.optString("audiAcc")); break; } } } } } } catch
		 * (Exception e) { e.printStackTrace(); } }); }
		 */

	    // 상영 중인 영화 리스트 출력
	    public Vector<MovieBean> listMovie() {
	    	Vector<MovieBean> vlist = new Vector<>();
	        Connection con = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            con = pool.getConnection();
	            String sql = "SELECT docid, movieSeq, title, directorNm, actorNm, plot, genre, releaseDate, runtime, posterUrl, vodUrl, audiAcc, rating\r\n"
	            		+ "FROM tblmovie ORDER BY RAND() LIMIT 16;";
	            pstmt = con.prepareStatement(sql);
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                if (vlist.size() >= 16) break; // Stop if size is 16

	                MovieBean bean = new MovieBean();
	                bean.setDocid(rs.getInt("docid"));
	                bean.setMovieSeq(rs.getString("movieSeq"));
	                bean.setTitle(rs.getString("title"));
	                bean.setDirectorNm(rs.getString("directorNm"));
	                bean.setActorNm(rs.getString("actorNm"));
	                bean.setPlot(rs.getString("plot"));
	                bean.setGenre(rs.getString("genre"));
	                bean.setReleaseDate(rs.getString("releaseDate"));
	                bean.setRuntime(rs.getString("runtime"));
	                bean.setPosterUrl(rs.getString("posterUrl"));
	                bean.setVodUrl(rs.getString("vodUrl"));
	                bean.setAudiAcc(rs.getString("audiAcc"));
	                bean.setRating(rs.getString("rating"));

	                vlist.addElement(bean);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            pool.freeConnection(con, pstmt, rs);
	        }
	        return vlist;
	    }


	// 영화 검색
	    public Vector<MovieBean> searchMovie(String title) {
	        Connection con = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        String sql = null;
	        Vector<MovieBean> vlist = new Vector<MovieBean>();

	        try {
	            con = pool.getConnection();
	            sql = "SELECT * FROM tblmovie WHERE title LIKE ?";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setString(1, "%" + title + "%");
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
	                bean.setRuntime(rs.getString(9));
	                bean.setPosterUrl(rs.getString(10));
	                bean.setVodUrl(rs.getString(11));
	                bean.setAudiAcc(rs.getString(12));
	                bean.setRating(rs.getString(13));

	                vlist.addElement(bean); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            pool.freeConnection(con, pstmt, rs);
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
				bean.setRuntime(rs.getString("runTime"));
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

	// 선택 영화 비디오 url 출력
	public String getVideoUrl(int docid) {
		// ex. https://www.kmdb.or.kr/trailer/play/MK061240_P02.mp4
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			con = pool.getConnection();
			sql = "SELECT vodUrl FROM tblmovie WHERE docid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, docid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				videoUrl = rs.getString(1);
			}
			videoUrl = videoUrl.replaceAll("trailerPlayPop\\?pFileNm=", "play/");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return videoUrl;
	}
	
	// 랜덤으로 비디오 url 출력
	public String randomVideoUrl() {
		// ex. https://www.kmdb.or.kr/trailer/play/MK061240_P02.mp4
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "SELECT vodUrl FROM tblmovie WHERE vodUrl IS NOT NULL AND vodUrl != '' ORDER BY RAND() LIMIT 1;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				videoUrl = rs.getString(1);
			}
			videoUrl = videoUrl.replaceAll("trailerPlayPop\\?pFileNm=", "play/");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return videoUrl;
	}
	
	// 랜덤으로 포스터 url 출력
	public String randomPosterUrl() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "SELECT posterUrl FROM tblmovie ORDER BY RAND() LIMIT 1;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				posterUrl = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return posterUrl;
	}


}
