package report.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import member.model.service.MemberService;
import member.model.vo.Member;
import report.model.exception.ReportException;
import report.model.service.ReportService;
import report.model.vo.RAttach;
import report.model.vo.Report;

/**
 * Servlet implementation class ReportViewServlet
 */
@WebServlet("/report/reportView")
public class ReportViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ReportService reportService = new ReportService();
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int no = 0;

			try {
				no = Integer.parseInt(request.getParameter("no"));
			} catch (NumberFormatException e) {
				throw new ReportException("유효한 게시글 번호가 아닙니다.", e);
			}

			Report report = reportService.selectReport(no);
			
			if (report == null) {
				System.out.println("게시글이 존재하지 않음. 임시경고.");
			}
			String id = report.getMemberId();
			Member member = memberService.selectMemberId(id);

			List<RAttach> attachList = reportService.selectAttachList(no);
			
			report.setContent(MvcUtils.escapeHtml(report.getContent()));
			report.setContent(MvcUtils.convertLineFeedToBr(report.getContent()));

			request.setAttribute("report", report);
			request.setAttribute("member", member);
			request.setAttribute("attachList", attachList);
			
			request.getRequestDispatcher("/WEB-INF/views/report/reportView.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
