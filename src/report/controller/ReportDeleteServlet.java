package report.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import report.model.service.ReportService;

/**
 * Servlet implementation class ReportDeleteServlet
 */
@WebServlet("/report/reportDelete")
public class ReportDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReportService reportService = new ReportService();
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			int result = reportService.deleteReport(no);
			String msg = result > 0 ? "게시글 삭제 성공!" : "게시글 삭제 실패!";
			
			request.getSession().setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/report/reportList");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
