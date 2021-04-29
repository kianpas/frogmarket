package report.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import report.model.service.ReportService;
import report.model.vo.Report;

/**
 * Servlet implementation class xmlReportList
 */
@WebServlet("/report/xmlReportList")
public class xmlReportList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ReportService reportService = new ReportService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchTarget = request.getParameter("searchTarget");
		List<Report> list = null;

		if (searchTarget == null || searchTarget.isEmpty()) {
			list = reportService.selectList();
		} else {
			list = reportService.searchMember(searchTarget);
		}

		request.setAttribute("reportList", list);
		request.getRequestDispatcher("/WEB-INF/views/report/reports.jsp").forward(request, response);
	}

}
