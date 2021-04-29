package report.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.MvcFileRenamePolicy;
import report.model.service.ReportService;
import report.model.vo.RAttach;
import report.model.vo.Report;

/**
 * Servlet implementation class ReportEnrollServlet
 */
@WebServlet("/report/reportEnroll")
public class ReportEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ReportService reportService = new ReportService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String saveDirectory = getServletContext().getRealPath("/upload/report");
		System.out.println("saveDirectory@servlet =" + saveDirectory);

		int maxPostSize = 10 * 1024 * 1024;

		String encoding = "utf-8";

		FileRenamePolicy policy = new MvcFileRenamePolicy();

		MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);

		try {
			String id = multipartRequest.getParameter("writer");
			String target = multipartRequest.getParameter("target");
			String comment = multipartRequest.getParameter("comment");

			Report report = new Report();

			report.setMemberId(id);
			report.setMemberReportId(target);
			report.setContent(comment);

			String[] originalFileArr = new String[5];
			String[] renamedFileArr = new String[5];

			int i = 0;

			while (multipartRequest.getOriginalFileName("upFile" + i) != null) {
				originalFileArr[i] = multipartRequest.getOriginalFileName("upFile" + i);
				renamedFileArr[i] = multipartRequest.getFilesystemName("upFile" + i);

				System.out.println("upFile" + i + " : " + originalFileArr[i]);
				i++;
			}

			RAttach[] attArr = new RAttach[5];

			i = 0;

			while (originalFileArr[i] != null) {
				RAttach attach = new RAttach();
				attach.setOriginalFileName(originalFileArr[i]);
				attach.setRenamedFileName(renamedFileArr[i]);
				attArr[i] = attach;
				i++;
			}

			// 2. 업무로직 : db에 insert
			int result = reportService.insertReport(report, attArr);

			HttpSession session = request.getSession();

			String msg = (result > 0) ? "신고가 접수되었습니다." : "신고 등록에 실패하였습니다.";
			String location = request.getContextPath();
			location += (result > 0) ? "/report/reportView?no=" + report.getReportNo() : "/report/reportList";

			session.setAttribute("msg", msg);
			response.sendRedirect(location);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
