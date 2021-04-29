package market.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import market.model.service.MarketService;

/**
 * Servlet implementation class BoardCommentDeleteServlet
 */
@WebServlet("/market/marketCommentDelete")
public class MarketCommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MarketService marketService = new MarketService(); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarketCommentDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int no = 0;
		int boardNo = 0;
		
		try {
		no = Integer.parseInt(request.getParameter("no"));
		boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
	
		int result = marketService.deleteMarketComment(no, boardNo);
		
		String msg = result >0? "댓글 삭제 성공" : "댓글 삭제 실패!";
		
		//3. 사용자피드백 & 리다이렉트
		request.getSession().setAttribute("msg", msg);
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}

}
