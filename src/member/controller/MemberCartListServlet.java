package member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import market.model.vo.Product;
import member.model.service.MemberService;
import member.model.vo.Cart;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberCartList
 */
@WebServlet("/member/cartList")
public class MemberCartListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			final int numPerPage = 9;
			int cPage = 1;
			try {
				cPage = Integer.parseInt(request.getParameter("cPage"));
			} catch (NumberFormatException e) {
				// 처리 코드 없음. 기본값 1 유지.
			}

			// 2. 업무로직
			// a. contents영역 start ~ end
			int end = cPage * numPerPage;
			int start = (cPage - 1) * numPerPage + 1;
			
			Member member = (Member) request.getSession().getAttribute("loginMember");
			
			//System.out.println(member);
			//System.out.println("카트리스트 멤버 아이디" + member.getMemberId());
			//String memberId = request.getParameter("memberId");
			
			String memberId = member.getMemberId();
			List<Cart> list = memberService.selectCartList(memberId);

			// Cart cart =
			List<Product> pList = memberService.selectList(list, start, end);

			// b. pageBar영역
			int totalContents = memberService.selectCartCount(memberId);
			String url = request.getRequestURI(); // /mvc/board/boardList
			String pageBar = MvcUtils.getPageBar(cPage, numPerPage, totalContents, url);
			System.out.println(totalContents);
			System.out.println(pList);
			request.setAttribute("list", pList);
			request.setAttribute("pageBar", pageBar);
			request.getRequestDispatcher("/WEB-INF/views/member/cartList.jsp").forward(request, response);

		} catch (Exception e) {
			throw e;
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
		
	}

}
