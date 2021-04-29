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
import market.model.vo.ProductCommentExt;
import market.model.vo.pAttach;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MarketViewServlet
 */
@WebServlet("/market/marketView")
public class MarketViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MarketService marketService = new MarketService();
	private MemberService memberService = new MemberService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int no = 0;
		try {
			no = Integer.parseInt(request.getParameter("no"));
			
			Product product = marketService.selectProduct(no);
			if(product == null) {
				System.out.println("게시글이 존재하지 않음. 임시경고.");
			}
			String id = product.getId();
			Member member = memberService.selectMemberId(id);
			
			List<pAttach> attachList = marketService.selectAttachList(no);
			
			
			//xss 공격방지
			product.setTitle(MvcUtils.escapeHtml(product.getTitle()));
			product.setDescription(MvcUtils.escapeHtml(product.getDescription()));

			
			// \n 개행문자를 <br/>태그로 변경해주기
			product.setDescription(MvcUtils.convertLineFeedToBr(product.getDescription()));
			
			
			//이 게시글의 댓글 가져오기
//			List<ProductComment> commentList = marketService.selectCommentList(no);
			List<ProductCommentExt> commentList = marketService.selectCommentExtList(no);

			//3. jsp forwarding
			request.setAttribute("product", product);
			request.setAttribute("member", member);
			request.setAttribute("attachList", attachList);
			request.setAttribute("commentList", commentList);
			request.getRequestDispatcher("/WEB-INF/views/market/marketView.jsp")
					.forward(request, response);
		} catch (Exception e) {	
			e.printStackTrace();
			throw e;
		} 		
		
	}

}
