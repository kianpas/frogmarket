package notice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class noticeListServlet
 */
@WebServlet("/notice/noticeList")
public class noticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService noticeService = new NoticeService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값
		String memberId = request.getParameter("memberId");
		
		//2. 업무로직
		List<Notice> list = new ArrayList<>();
		try {
			list = noticeService.selectNoticeList(memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3. JSON 문자열로 변환 및 응답메시지에 출력하기
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		
		System.out.println(jsonStr);
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
	}

}
