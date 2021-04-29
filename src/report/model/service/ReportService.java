package report.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import report.model.dao.ReportDao;
import report.model.vo.RAttach;
import report.model.vo.Report;

public class ReportService {

	private ReportDao reportDao = new ReportDao();

	public List<Report> selectList() {
		Connection conn = getConnection();
		List<Report> list = reportDao.selectList(conn);
		close(conn);
		return list;
	}

	public int insertReport(Report report, RAttach[] attArr) {
		Connection conn = getConnection();
		int result = 0;

		try {
			result = reportDao.insertReport(conn, report);

			// 생성된 product_no를 가져오기
			int reportNo = reportDao.selectLastReportNo(conn);
			report.setReportNo(reportNo);

			int i=0;
			while(attArr[i]!=null) {
				attArr[i].setReportNo(reportNo);
				result = reportDao.insertAttachment(conn,attArr[i]);
				i++;
			}
			commit(conn);

		} catch (Exception e) {
			rollback(conn);
			result = 0;
			throw e;
		} finally {
			close(conn);
		}

		return result;
	}

	public Report selectReport(int no) {
		Connection conn = getConnection();
		Report report = null;
		RAttach attach = null;

		try {
			report = reportDao.selectReport(conn, no);

			if (report.getAttach() != null) {
				attach = reportDao.selectAttach(conn, no);
			}
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
		} finally {
			close(conn);
		}
		return report;
	}

	public List<Report> searchMember(String searchTarget) {
		Connection conn = getConnection();
		List<Report> list = reportDao.searchReport(conn, searchTarget);
		close(conn);
		return list;
	}

	public List<RAttach> selectAttachList(int no) {
		Connection conn = getConnection();
		List<RAttach> list = reportDao.selectAttachList(conn, no);
		close(conn);
		return list;
	}

	public int deleteReport(int no) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = reportDao.deleteReport(conn, no);
			if(result == 0)
				throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. : " + no );
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

}
