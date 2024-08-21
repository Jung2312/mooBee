package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import DAO.DBConnectionMgr;

public class UserMgr {

	private DBConnectionMgr pool;

	public UserMgr() {
		pool = DBConnectionMgr.getInstance();

	}

	// 비밀번호 암호화
	public class SHA256 {
		public static String encrypt(String text) throws NoSuchAlgorithmException {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			return bytesToHex(md.digest());
		}

		private static String bytesToHex(byte[] bytes) {
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		}
	}

	// 회원 정보 확인
	public int findUser(String userId, String name, String phone) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int cnt = 0;

		try {
			con = pool.getConnection();
			sql = "select count(*) from tbluser where userId = ? and name = ? and phone = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs); // con은 반납, pstmt/rs는 close
		}
		return cnt;
	}
	
	// 비밀번호 재설성
	public boolean updatePassword(String userId, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			con = pool.getConnection();
			sql = "update tbluser set password = ? where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, SHA256.encrypt(password));
			pstmt.setString(2, userId);
			if (pstmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt); // con은 반납, pstmt는 close
		}
		return flag;
	}

	// 로그인 메서드 추가
	public boolean login(String userId, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean isValidUser = false;
		try {
			con = pool.getConnection();
			sql = "SELECT password FROM tbluser WHERE userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String storedPassword = rs.getString("password");
				// 비밀번호 암호화 후 비교
				String encryptedPassword = SHA256.encrypt(password);
				if (encryptedPassword.equals(storedPassword)) {
					isValidUser = true; // 로그인 성공
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return isValidUser;
	}

	// 저장 post
	public boolean insertUser(UserBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert into tbluser values (?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getUserId());
			pstmt.setString(2, bean.getName());
			// 비밀번호 암호화 후 저장
			String encryptedPassword = SHA256.encrypt(bean.getPassword());
			pstmt.setString(3, encryptedPassword);
			pstmt.setInt(4, 0);
			pstmt.setString(5, bean.getBirthDate());
			pstmt.setString(6, bean.getPhone());
			pstmt.setString(7, "Bronze");
			pstmt.setFloat(8, 36.5f);
			pstmt.setBoolean(9, false);
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// select 검색
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
			if (rs.next()) { // 2개 이상의 레코드는 절대 리턴되지않음
				bean.setUserId(rs.getString(1)); // 1은 첫번째 컬럼
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
				bean.setPaymentAmount(rs.getInt(4));
				bean.setBirthDate(rs.getString(5));
				bean.setPhone(rs.getString(6));
				bean.setGrade(rs.getString(7));
				bean.setTemp(rs.getFloat(8));
				bean.setManager(rs.getBoolean(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}

	// update 수정
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
			pstmt.setString(2, SHA256.encrypt(bean.getPassword()));
			pstmt.setString(3, bean.getBirthDate());
			pstmt.setString(4, bean.getPhone());
			pstmt.setString(5, bean.getUserId());
			if (pstmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// delete 삭제
	public boolean deleteUser(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from tblUser where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			if (pstmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// 결제 금액 검색
	public int getpay(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int pay = 0;
		try {
			con = pool.getConnection();
			sql = "select PaymentAmount from tbluser where userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 2개 이상의 레코드는 절대 리턴되지않음
				pay =rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return pay;
	}
	// 결제금액 업데이트

	public boolean updatepay(int pay , String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "update  tbluser set paymentAmount = ? WHERE userId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pay);
			pstmt.setString(2, userId);
			if (pstmt.executeUpdate() == 1)
				flag = true;
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
