package com.bit2015.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bit2015.mysite.vo.BoardVo;
import com.bit2015.mysite.vo.GuestbookVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {

		Connection connection = null;

		try {
			// 1.드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2.커넥션 만들기(ORACLE DB)
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			connection = DriverManager.getConnection(dbURL, "webdb", "webdb");

		} catch (ClassNotFoundException ex) {
			System.out.println("드라이버 로딩 실패-" + ex);
		}

		return connection;
	}

	public List<BoardVo> getList() {
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			// 1. 커넥션 만들기(ORACLE DB)
			Connection connection = getConnection();

			// 2. Statement 생성
			Statement stmt = connection.createStatement();

			// 3. SQL문 실행
			String sql = "select a.no,a.TITLE,a.MEMBER_NO, b.name as member_name,a.VIEW_CNT,to_char(a.REG_DATE,'yyyy-mm-dd hh:Mi:ss')"
					+ " from board a,member b where a.member_no=b.no order by a.reg_date desc";
			ResultSet rs = stmt.executeQuery(sql);

			// 4. row 가져오기
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long memberNo = rs.getLong(3);
				String memberName = rs.getString(4);
				int viewCnt = rs.getInt(5);
				String regDate = rs.getString(6);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setMemberNo(memberNo);
				vo.setMemberName(memberName);
				vo.setViewCnt(viewCnt);
				vo.setRegDate(regDate);

				list.add(vo);
			}

			// 6. 자원 정리
			rs.close();
			stmt.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("SQL 오류-" + ex);
		}

		return list;
	}

	public void insert(BoardVo vo) {
		try {
			// 1. Connection 가져오기
			Connection connection = getConnection();

			// 2. Statement 준비
			String sql = "insert into board values(board_no_seq.nextval, ? , ?, ?,0,sysdate)";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// 3. binding
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getMemberNo());

			// 4. query 실행
			pstmt.executeUpdate();

			// 5. 자원정리
			pstmt.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("SQL 오류-" + ex);
		}
	}

	public void delete(Long no) {
		try {
			// 1. Connection 가져오기
			Connection connection = getConnection();

			// 2. Statement 준비
			String sql = "delete from board where no=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// 3. binding
			pstmt.setLong(1, no);

			// 4. query 실행
			pstmt.executeUpdate();

			// 5. 자원정리
			pstmt.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("SQL 오류-" + ex);
		}
	}

	public BoardVo find(Long no) {
		BoardVo vo = new BoardVo();
		try {
			// 1. Connection 가져오기
			Connection connection = getConnection();

			// 2. Statement 준비
			String sql = "select * from board where no=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// 3. binding
			pstmt.setLong(1, no);

			// 4. query 실행

			ResultSet rs = pstmt.executeQuery();

			// 5. row 가져오기
			if (rs.next()) {
				no=rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long memberNo=rs.getLong(4);
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setMemberNo(memberNo);
			}

			// 5. 자원정리
			pstmt.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("SQL 오류-" + ex);
		}
		return vo;
	}
	public void update(BoardVo vo) {
		try {
			// 1. Connection 가져오기
			Connection connection = getConnection();

			// 2. Statement 준비
			String sql = "update board set title=?,content=? where no=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// 3. binding
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			// 4. query 실행
			pstmt.executeUpdate();

			// 5. 자원정리
			pstmt.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("SQL 오류-" + ex);
		}
	}
	public void count(BoardVo vo) {
		try {
			// 1. Connection 가져오기
			Connection connection = getConnection();

			// 2. Statement 준비
			String sql = "update board set view_cnt=view_cnt+1 where no=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			// 3. binding
			
			pstmt.setLong(1, vo.getNo());

			// 4. query 실행
			pstmt.executeUpdate();

			// 5. 자원정리
			pstmt.close();
			connection.close();

		} catch (SQLException ex) {
			System.out.println("SQL 오류-" + ex);
		}
	}
}
