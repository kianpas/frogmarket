package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet ("/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	MemberService memberService = new MemberService();
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//String memberId = request.getParameter("memberId");
		String password = request.getParameter("newPassword");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String nickId = request.getParameter("nickId");
		//System.out.println(memberId);
		
		
		HttpSession session = request.getSession();
		Member loginmember = (Member) session.getAttribute("loginMember");
		Member member = new Member();
		
		member.setMemberId(loginmember.getMemberId());
		member.setPassword(password);
		member.setEmail(email);
		member.setPhone(phone);
		member.setNickId(nickId);
		System.out.println("member@servlet = " + member);

		int result = memberService.updateMember(member);
		System.out.println(result);

		
		System.out.println();
		String msg = "";

		if(result > 0){
			msg = "성공적으로 회원정보를 수정했습니다.";
			session.setAttribute("loginMember", memberService.selectOne(member));
		}
		else {
			msg = "회원정보수정에 실패했습니다.";
		}	
		
		session.setAttribute("msg", msg);
		request.getRequestDispatcher("/WEB-INF/views/member/memberView.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String memberId = request.getParameter("memberId");
		
		Member member = memberService.selectMemberId(memberId);

		request.setAttribute("member", member);

		request.getRequestDispatcher("/WEB-INF/views/member/memberUpdate.jsp").forward(request, response);

	}

}
