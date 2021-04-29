package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class XmlAddHeart
 */
@WebServlet("/member/AddHeart")
public class XmlAddHeart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("memberId");

		Member member = memberService.selectMemberId(id);
		
		int result = memberService.addHeart(member);
		
		Member heartMember = memberService.selectMemberId(id);
		
		request.setAttribute("heartMember", heartMember);
		request.getRequestDispatcher("/WEB-INF/views/member/hearts.jsp").forward(request, response);
	}

}
