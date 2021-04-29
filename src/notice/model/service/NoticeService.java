package notice.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import notice.model.dao.NoticeDao;
import notice.model.vo.Notice;

public class NoticeService {
	
	private NoticeDao noticeDao = new NoticeDao();

	public int insertNotice(Notice notice) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = noticeDao.insertNotice(conn,notice);
			commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			result=0;
			throw e;
		} finally {
			close(conn);			
		}
		return result;
	}

	public List<Notice> selectNoticeList(String memberId) {
		Connection conn = getConnection();
		List<Notice> list = new ArrayList<>();
		try {
			 list= noticeDao.selectNoticeList(conn,memberId);
			commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);			
		}
		return list;
	}
	
	public int deleteNotice(int no) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = noticeDao.deleteNotice(conn,no);
			commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			result=0;
			throw e;
		} finally {
			close(conn);			
		}
		return result;
	}

}
