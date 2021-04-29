package member.controller;

import java.io.IOException;
import java.util.List;

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
@WebServlet("/member/addCart")
public class MemberAddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAddCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String memberId = request.getParameter("memberId");
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		System.out.println(boardNo);
		System.out.println(memberId);
		
		Cart cart = new Cart();
		
		cart.setMemberId(memberId);
		cart.setBoardNo(boardNo);
		
		List <Cart> list = memberService.selectCartList(memberId, boardNo);
		System.out.println("체크 리스트" + list);
		int result = 0;
		if(list.isEmpty()) {
			result = memberService.insertCart(cart);
		}
		System.out.println(result);
		
		String msg = result >0? "장바구니 등록 성공" : "장바구니 등록 실패!";
		
		//피드백
		request.getSession().setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath()+"/market/marketView?no="+boardNo);
	}

}
