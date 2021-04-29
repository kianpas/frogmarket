package graph.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graph.model.service.GraphService;
import graph.model.vo.Graph;

/**
 * Servlet implementation class GraphDrawServlet
 */
@WebServlet("/graph/drawGraph")
public class GraphDrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GraphService graphService = new GraphService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GraphDrawServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			String keyword = (String) session.getAttribute("searchKeyword");
	
			List<Graph> list =graphService.searchProductList(keyword);

			response.setContentType("application/json; charset=utf-8");
			Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd").create();
			String jsonStr = gson.toJson(list);

			System.out.println(jsonStr);
			PrintWriter out = response.getWriter();
			out.print(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		
		//if(keyword !=null || graphKeyword!=null) {
		//}
//		 for(Graph g : list) {
//		 System.out.println(g.getRegDate());
//		 }
		//String graphKeyword = request.getParameter("header-search");
		//System.out.println("그래프 키워드" + graphKeyword);
		//System.out.println(keyword);
	
		// List <Graph> list = graphService.selectList(keyword);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
