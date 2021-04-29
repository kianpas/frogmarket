package market.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import market.model.service.MarketService;
import market.model.vo.Product;

/**
 * Servlet implementation class XmlProductList
 */
@WebServlet("/market/XmlProductList")
public class XmlProductList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MarketService marketService = new MarketService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("memberId");
		
		List<Product> list = marketService.selectMemberList(id);

		request.setAttribute("memberId", id);
		request.setAttribute("productList", list);
		request.getRequestDispatcher("/WEB-INF/views/market/xmlMarketList.jsp").forward(request, response);
	}

}
