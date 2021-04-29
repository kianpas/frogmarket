package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String memberId = request.getParameter("memberId");
		
		int result = memberService.deleteMember(memberId);
		
		HttpSession session = request.getSession();
		if(result>0) {
			session.setAttribute("msg", "회원정보를 삭제했습니다.");
			response.sendRedirect(request.getContextPath() + "/member/memberLogout");
		}
		else {
			session.setAttribute("msg", "회원정보삭제에 실패했습니다.");
			response.sendRedirect(request.getContextPath());			
		}
	}

}
