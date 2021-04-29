package report.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import report.model.exception.ReportException;
import report.model.vo.RAttach;
import report.model.vo.Report;

public class ReportDao {

	private Properties prop = new Properties();
	
	public ReportDao() {
		String fileName = "/sql/report/report-query.properties";
		String absPath = ReportDao.class.getResource(fileName).getPath();
		try {
			prop.load(new FileReader(absPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<Report> selectList(Connection conn) {
		List<Report> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, start);
//			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Report report = new Report();
				report.setReportNo(rset.getInt("report_no"));
				report.setMemberId(rset.getString("member_id"));
				report.setMemberReportId(rset.getString("member_report_id"));
				report.setRegDate(rset.getDate("reg_date"));
				report.setContent(rset.getString("content"));
				
				//첨부파일이 있는 경우
//				if(rset.getInt("attach_no") != 0) {
//					Attachment attach = new Attachment();
//					attach.setNo(rset.getInt("attach_no"));
//					attach.setBoardNo(rset.getInt("no"));
//					attach.setOriginalFileName(rset.getString("original_filename"));
//					attach.setRenamedFileName(rset.getString("renamed_filename"));
//					attach.setStatus("Y".equals(rset.getString("status")) ? true : false);
//					board.setAttach(attach);
//				}
				
				list.add(report);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Report selectReport(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectOne");
		ResultSet rset=null;
		Report report = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			while(rset.next()) {
				report = new Report();
				report.setReportNo(rset.getInt("report_no"));
				report.setMemberId(rset.getString("member_id"));
				report.setMemberReportId(rset.getString("member_report_id"));
				report.setRegDate(rset.getDate("reg_date"));
				report.setContent(rset.getString("content"));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return report;
	}

	public RAttach selectAttach(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectAttach");
		ResultSet rset=null;
		RAttach attach = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			while(rset.next()) {
				attach = new RAttach();
				
				attach.setNo(rset.getInt("no"));
				attach.setReportNo(rset.getInt("report_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				attach.setRegDate(rset.getDate("reg_date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return attach;
	}

	public int insertReport(Connection conn, Report report) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertReport");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, report.getMemberId());
			pstmt.setString(2, report.getMemberReportId());
			pstmt.setString(3, report.getContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ReportException("게시물 등록 오류", e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int selectLastReportNo(Connection conn) {
		int reportNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectLastReportNo");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				reportNo = rset.getInt("report_no");
			}
		} catch (SQLException e) {
			throw new ReportException("게시물 등록 번호 조회 오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		return reportNo;
	}

	public int insertAttachment(Connection conn, RAttach attach) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertAttachment");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, attach.getReportNo());
			pstmt.setString(2, attach.getOriginalFileName());
			pstmt.setString(3, attach.getRenamedFileName());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ReportException("게시물 첨부파일 등록 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
		
	}

	public List<Report> searchReport(Connection conn, String searchTarget) {
		List<Report> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("searchReport");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+searchTarget+"%");

			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Report report = new Report();
				
				report.setReportNo(rset.getInt("report_no"));
				report.setMemberId(rset.getString("member_id"));
				report.setMemberReportId(rset.getString("member_report_id"));
				report.setRegDate(rset.getDate("reg_date"));
				report.setContent(rset.getString("content"));
				
				list.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public List<RAttach> selectAttachList(Connection conn, int no) {
		PreparedStatement pstmt = null;
		List<RAttach> list = new ArrayList<>();
		String sql = prop.getProperty("selectAttachList");
		ResultSet rset = null;
		RAttach attach = null;
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				attach = new RAttach();

				attach.setNo(rset.getInt("no"));
				attach.setReportNo(rset.getInt("report_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
				attach.setRegDate(rset.getDate("reg_date"));

				list.add(attach);
			}

		} catch (Exception e) {
			throw new ReportException("첨부파일 조회 오류",e);
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int deleteReport(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteReport");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ReportException("게시물 삭제 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

}
