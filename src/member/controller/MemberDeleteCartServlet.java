package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Cart;

/**
 * Servlet implementation class MemberAddCartServlet
 */
@WebServlet("/member/deleteCart")
public class MemberDeleteCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	public MemberDeleteCartServlet() {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String id = request.getParameter("id");
			int no = Integer.parseInt(request.getParameter("no"));

			System.out.println(id);
			System.out.println(no);

			Cart cart = new Cart();

			cart.setMemberId(id);
			cart.setBoardNo(no);

			int result = memberService.deleteCart(cart);
			String msg = result > 0 ? "장바구니 삭제 성공" : "장바구니 삭제 실패!";

			// 피드백

			//response.sendRedirect("http://localhost:9090/frog/member/cartList");
			response.sendRedirect(request.getContextPath()+ "/member/cartList");
		
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}


}
