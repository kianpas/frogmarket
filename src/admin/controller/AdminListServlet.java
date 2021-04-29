package admin.controller;

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

/**
 * Servlet implementation class AdminListServlet
 */
@WebServlet("/admin/adminList")
public class AdminListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final int numPerPage = 10;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {

		}

		int end = cPage * numPerPage;
		int start = (cPage - 1) * numPerPage + 1;

		List<Member> list = memberService.selectList(start, end);

		int totalContents = memberService.selectMemberCount();
		String url = request.getRequestURI();
		String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);

		request.setAttribute("pageBar", pageBar);
		request.setAttribute("adminlist", list);
		request.getRequestDispatcher("/WEB-INF/views/member/memberView.jsp").forward(request, response);
	}

}
