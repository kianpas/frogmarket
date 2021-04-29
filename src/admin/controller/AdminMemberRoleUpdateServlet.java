package admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberRoleUpdateServlet
 */
@WebServlet("/admin/memberRoleUpdate")
public class AdminMemberRoleUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberRole = request.getParameter("memberRole");
		
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberRole(memberRole);
		
		int result = memberService.updateMemberRole(member);
		String msg = result > 0 ? "사용자 권한 변경에 성공했습니다." : "사용자 권한 변경에 실패했습니다.";
		
		request.getSession().setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/member/memberView");
		
	}

}
