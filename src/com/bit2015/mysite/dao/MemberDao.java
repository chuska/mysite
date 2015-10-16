package com.bit2015.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bit2015.mysite.vo.MemberVo;

public class MemberDao {
	private Connection getconnection() throws SQLException {
		Connection connection = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 커넥션 만들기(ORACLE DB)
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			connection = DriverManager.getConnection(dbURL, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void update(MemberVo vo) {
		try {
			// 1. get Connection
			Connection conn = getconnection();

			// 2. prepared statement
			String sql = "update member set name=?, email=?, password=?, gender=? where no=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// 3. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			pstmt.setLong(5, vo.getNo());

			// 4. execute SQL
			pstmt.executeUpdate();

			// 5. clear resources
			pstmt.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println("sql error:" + ex);
		}
	}

	public MemberVo get(String email, String password) {
		MemberVo vo = null;
		try {
			// 1. get Connection
			Connection conn = getconnection();

			// 2. prepared statement
			String sql = "select no, name, email from member where email=?and password=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// 3. binding
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			// 4. execute SQL
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email2 = rs.getString(3);
				vo = new MemberVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setEmail(email2);
			}

			// 5. clear resources
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println("SQL Error:" + ex);
		}
		return vo;
	}

	public MemberVo get(Long no) {
		MemberVo vo = null;
		return vo;
	}

	public void insert(MemberVo vo) {
		try {
			// 1. DB Connection
			Connection conn = getconnection();

			// 2. prepared statement
			String sql = "insert into member values(member_no_seq.nextval, ?, ?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// 3. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			// 4. execute SQL
			pstmt.executeUpdate();

			// 5. clear resources
			pstmt.close();
			conn.close();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("sql error:" + ex);
		}
	}
}
