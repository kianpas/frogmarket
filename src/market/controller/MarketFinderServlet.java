package market.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.MvcUtils;
import market.model.service.MarketService;
import market.model.vo.Product;
import member.model.vo.Member;

/**
 * Servlet implementation class MarketFinderServlet
 */
@WebServlet("/market/marketFinder")
public class MarketFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MarketService marketService = new MarketService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값 : 현재페이지 cPage
				final int numPerPage = 9;
				int cPage = 1;
				try {
					cPage = Integer.parseInt(request.getParameter("cPage"));
				} catch (NumberFormatException e) {
					// 처리 코드 없음. 기본값 1 유지.
				}
				int end = cPage * numPerPage;
				int start = (cPage-1)*numPerPage + 1;
				//1. 사용자 입력값 처리
				String searchKeyword = request.getParameter("header-search");
				System.out.println("searchKeyword@servlet = "+searchKeyword);
						
				//헤더의 검색값가져오기
//				String searchKeyword = request.getParameter("header-search");
				request.getSession().setAttribute("searchKeyword", searchKeyword);
				//2. 업무로직
				
				List<Product> list = marketService.searchProductList(searchKeyword, start, end);
				System.out.println("list@servlet = "+list);
				
				//3. pagebar영역 작업
//				List<Member> listOrigin = memberService.searchMember(param);
//				int totalContents = listOrigin.size(); 
				//searchMember 부르지 말고 param을 매개변수로 갖는 count함수를 만들자.
				int totalContents = marketService.searchProductCount(searchKeyword);
				System.out.println("list 카운트값 : "+totalContents);		
				///mvc/admin/memberFinder?searchType=memberId&searchKeyword=a
				String url = request.getRequestURI()+"?header-search="+searchKeyword;
				String pageBar = MvcUtils.getPageBar(
							cPage,
							numPerPage,
							totalContents,
							url
						);
				
				//4. jsp에 html응답메세지 작성 위임
				//request.getSession().setAttribute("sList", sList);
				request.setAttribute("pageBar", pageBar);
				request.setAttribute("list", list);
				request.setAttribute("searchKeyword", searchKeyword);
				request.getRequestDispatcher("/WEB-INF/views/market/marketList.jsp")
						.forward(request, response);
	}

}
