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
 * Servlet implementation class XmlMemberList
 */
@WebServlet("/member/XmlMemberList")
public class XmlMemberList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Member> list = memberService.selectList();
		
		request.setAttribute("adminlist", list);
		request.getRequestDispatcher("/WEB-INF/views/admin/xmlAdminMembers.jsp").forward(request, response);

	}

}
