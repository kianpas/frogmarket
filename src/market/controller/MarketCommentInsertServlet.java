package market.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import market.model.service.MarketService;
import market.model.vo.Product;
import market.model.vo.ProductComment;
import member.model.service.MemberService;
import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/market/marketCommentInsert")
public class MarketCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MarketService marketService = new MarketService();
	private NoticeService noticeService = new NoticeService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MarketCommentInsertServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// 1. 사용자 입력값 처리
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			String writer = request.getParameter("writer");
			
			String content = request.getParameter("content");
			ProductComment pc = new ProductComment(0, boardNo, writer, content, null);

			System.out.println("boardComment@servlet = " + pc);

			// 2. 업무로직
			int result = marketService.insertMarketComment(pc);
			String msg = result > 0 ? "댓글 등록 성공" : "댓글 등록 실패!";
			// 2-2. 알림용 업무로직
			Product product = marketService.selectProduct(boardNo);
			Notice notice=new Notice();
			notice.setBoardNo(boardNo);
			notice.setSenderId(writer);
			notice.setReceiverId(product.getId());
			notice.setTitle(product.getTitle());
			notice.setContent(content);
			int noticeResult = noticeService.insertNotice(notice);
			System.out.println(noticeResult==1? "알림입력성공":"알림입력실패");
			

			// 3. 사용자피드백 & 리다이렉트
			request.getSession().setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/market/marketView?no=" + boardNo);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
