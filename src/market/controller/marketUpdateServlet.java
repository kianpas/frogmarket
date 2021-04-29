package market.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import common.MvcFileRenamePolicy;
import market.model.service.MarketService;
import market.model.vo.Product;
import market.model.vo.pAttach;

/**
 * Servlet implementation class marketUpdateServlet
 */
@WebServlet("/market/marketUpdate")
public class marketUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MarketService marketService = new MarketService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값
		int no = Integer.parseInt(request.getParameter("no"));
		
		//2. 업무로직
		Product product = marketService.selectProduct(no);
		List<pAttach> attachList = marketService.selectAttachList(no);
		
		
		//3. jsp 포워딩
		request.setAttribute("product", product);
		request.setAttribute("attachList", attachList);
		request.getRequestDispatcher("/WEB-INF/views/market/marketUpdateForm.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. MultipartRequest객체 생성
		// /WebContent/upload/board/업로드파일명.jpg
		// getRealPath :  web root dir를 절대경로로 반환
		
		String saveDirectory = getServletContext().getRealPath("/upload/market");
		System.out.println("saveDirectory@servlet ="+saveDirectory);
		
		//최대 파일 허용 크기 10mb = 10 * 1kb * 1kb
		int maxPostSize = 10*1024*1024;
		
		//인코딩
		String encoding = "utf-8";
		
		FileRenamePolicy policy = new MvcFileRenamePolicy();
		
		MultipartRequest multipartRequest = 
				new MultipartRequest(
						request,
						saveDirectory, 
						maxPostSize, 
						encoding, 
						policy
					);

		try {
			//2. db에 게시글/첨부파일 정보 저장
			
			//2-1. 사용자 입력값처리
			// title writer upFile content
			int no = Integer.parseInt(multipartRequest.getParameter("no"));
			String id = multipartRequest.getParameter("writer");
			String title = multipartRequest.getParameter("title");
			String status = multipartRequest.getParameter("status");
			int price = Integer.parseInt(multipartRequest.getParameter("price"));
			String description = multipartRequest.getParameter("description");
			String area = multipartRequest.getParameter("local");
			//대체된 이전 파일번호
			String[] delFile = multipartRequest.getParameterValues("delFile");
			
			int [] prevAttachNo=null;
			if(delFile!=null) {
				int size = delFile.length;
				prevAttachNo = new int [size];
				for(int i=0; i<size; i++) {
					prevAttachNo[i] = Integer.parseInt(delFile[i]);
				}		
				for(int num : prevAttachNo)
					System.out.println("prevAttachNo : "+ num);
			}

			//업로드한 파일명
//			String originalFileName = multipartRequest.getOriginalFileName("upFile");
//			String renamedFileName =  multipartRequest.getFilesystemName("upFile");
			
			String[] originalFileArr=new String[5];
			String[] renamedFileArr=new String[5];
			int i=0;
			while(multipartRequest.getOriginalFileName("upFile"+i)!=null) {
				originalFileArr[i]=multipartRequest.getOriginalFileName("upFile"+i);
				renamedFileArr[i] =  multipartRequest.getFilesystemName("upFile"+i);
				
				System.out.println("upFile"+i+" : "+originalFileArr[i]);
				i++;
			}

			System.out.println("no@servlet = "+no);
			System.out.println("id@servlet = "+id);
			System.out.println("title@servlet = "+title);
			System.out.println("status@servlet = "+status);
			System.out.println("price@servlet = "+price);
			System.out.println("description@servlet = "+description);
			System.out.println("area@servlet = "+area);

			Product product = new Product();
			product.setNo(no);
			product.setId(id);
			product.setTitle(title);
			product.setStatus(status);
			product.setPrice(price);
			product.setDescription(description);
			product.setArea(area);
			
			//첨부파일이 있는 경우
			
			pAttach[] attArr = new pAttach[5];
			i=0;
			while(originalFileArr[i]!=null) {
				pAttach attach = new pAttach();
				attach.setOriginalFileName(originalFileArr[i]);
				attach.setRenamedFileName(renamedFileArr[i]);
//				product.setAttach(attach);
				attArr[i]=attach;
				i++;
			}
			
			//2. 업무로직 : db에 insert		
			//3가지기능 : product수정, file제거, file추가
			int result = marketService.updateProduct(product,prevAttachNo,attArr);
			System.out.println("처리결과 = "+result);
			
			//가입 성공/실패여부 판단
			//1. 가입 성공: result==1
			//2. 가입 실패 : result==0 || result==null
			
			HttpSession	session = request.getSession();
			
			String msg = (result>0) ? "게시글 수정 성공!" : "게시글 수정 실패!";
			String location = request.getContextPath();
			location+= (result>0) ? "/market/marketView?no="+no : "/market/marketList";
			
			session.setAttribute("msg", msg);
			response.sendRedirect(location);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

}
