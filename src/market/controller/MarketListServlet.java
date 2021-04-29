package market.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.MvcUtils;
import market.model.service.MarketService;
import market.model.vo.Product;

/**
 * Servlet implementation class MarketListServlet
 */
@WebServlet("/market/marketList")
public class MarketListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MarketService marketService = new MarketService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0. 인코딩처리는 EncodingFilter가 선처리
		//1. 사용자 입력값
		final int numPerPage = 9;
		int cPage = 1;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			// 처리 코드 없음. 기본값 1 유지.
		}
		
		//2. 업무로직
		//a. contents영역 start ~ end
		int end = cPage * numPerPage;
		int start = (cPage-1)*numPerPage + 1;
		
		List<Product> list = marketService.selectList(start,end);
		//System.out.println("marketListServlet : "+list.get(0).getAttach().getRenamedFileName()); 
		//System.out.println("list@servlet = "+list);
		
		//댓글카운트 추가

		//List<BoardVer2> list = boardService.selectListVer2(start,end);

		//b. pageBar영역 
		int totalContents = marketService.selectProductCount();
		String url = request.getRequestURI(); // /mvc/board/boardList
		String pageBar = MvcUtils.getPageBar(
					cPage,
					numPerPage,
					totalContents,
					url
				);
		
		//3. 응답 html처리
		
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/views/market/marketList.jsp")
				.forward(request, response);
	}

}
