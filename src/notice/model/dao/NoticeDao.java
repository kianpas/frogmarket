package notice.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import market.model.vo.pAttach;
import notice.model.vo.Notice;

public class NoticeDao {

	public int insertNotice(Connection conn, Notice notice) {
		PreparedStatement pstmt = null;
		String sql = "insert into notice values(seq_notice_no.nextval, ?, ?, ?, ?, ?)";
		int result = 0;

		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setInt(1, notice.getBoardNo());
			pstmt.setString(2, notice.getSenderId());
			pstmt.setString(3, notice.getReceiverId());
			pstmt.setString(4, notice.getTitle());
			pstmt.setString(5, notice.getContent());
			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			// 5. 자원반납(pstmt)
			close(pstmt);
		}

		return result;
	}

	public List<Notice> selectNoticeList(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		List<Notice> list = new ArrayList<>();
//		String sql = prop.getProperty("selectAttachList");
		String sql = "select no,board_no,sender_id, nick_id, receiver_id,title,content from (select * from notice N left join member M on n.sender_id = M.member_id) N where receiver_id=? order by no desc";
		ResultSet rset = null;
		Notice notice = null;
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			rset = pstmt.executeQuery();
			// 4-1) ResultSet -> Java객체 옮겨담기
			while (rset.next()) {
				notice = new Notice();
				notice.setNo(rset.getInt("no"));
				notice.setBoardNo(rset.getInt("board_no"));
				notice.setSenderId(rset.getString("sender_id"));
				notice.setSenderNick(rset.getString("nick_id"));
				notice.setReceiverId(rset.getString("receiver_id"));
				notice.setTitle(rset.getString("title"));
				notice.setContent(rset.getString("content"));

				list.add(notice);
			}

		} catch (Exception e) {
//			throw new BoardException("첨부파일 조회 오류",e);
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int deleteNotice(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = "delete from notice where no=?";
		int result = 0;

		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setInt(1, no);
			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			// 5. 자원반납(pstmt)
			close(pstmt);
		}

		return result;
	}

}
