package market.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import market.model.dao.MarketDao;
import market.model.vo.Product;
import market.model.vo.ProductComment;
import market.model.vo.ProductCommentExt;
import market.model.vo.pAttach;

public class MarketService {
	private MarketDao marketDao = new MarketDao();

	public int insertProduct(Product product, pAttach[] attArr) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = marketDao.insertProduct(conn,product);
			
			//생성된 product_no를 가져오기
			int productNo =  marketDao.selectLastProductNo(conn);
			product.setNo(productNo);
			System.out.println("productNo : "+productNo);
			
			int i=0;
			while(i<5 && attArr[i]!=null) {
				attArr[i].setProductNo(productNo);
				result = marketDao.insertAttachment(conn,attArr[i]);
				i++;
			}

			
			commit(conn);

		} catch (Exception e) {
//			e.printStackTrace();
			rollback(conn);
			result=0;
			throw e;
		} finally {
			close(conn);			
		}
	
//		if(result>0) commit(conn);
//		else rollback(conn);
		
		return result;
	}

	public Product selectProduct(int no) {
		Connection conn = getConnection();
		pAttach attach = null;
		Product product = null;

		try {
			product = marketDao.selectProduct(conn,no);
			
//			if(product.getAttach()!=null) {
//				attach = marketDao.selectAttach(conn,no);
//			}
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
		} finally {
			close(conn);
		}
		return product;
	}

	public List<Product> selectList(int start, int end) {
		Connection conn = getConnection();
		//--------Dao 요청----------
		List<Product> list = marketDao.selectList(conn,start,end);
		close(conn);
		
		return list;
	}

	public int selectProductCount() {
		Connection conn = getConnection();
		int totalContent = marketDao.selectProductCount(conn);
		close(conn);
		
		return totalContent;
	}

	public List<pAttach> selectAttachList(int no) {
		Connection conn = getConnection();
		//--------Dao 요청----------
		List<pAttach> list = marketDao.selectAttachList(conn,no);

		close(conn);
		
		return list;
	}

	public List<Product> searchProductList(String searchKeyword, int start, int end) {
		Connection conn = getConnection();
		//검색어를 나눠서 배열로 넘김
		String[] keywordArr = searchKeyword.split(",|\\s+");		
		List<Product> list = marketDao.searchProductList(conn,keywordArr,start,end);

		close(conn);
		return list;
	}

	public int searchProductCount(String searchKeyword) {
		Connection conn = getConnection();
		//검색어를 나눠서 배열로 넘김
		String[] keywordArr = searchKeyword.split(",|\\s+");	
		int totalContent = marketDao.searchProductCount(conn,keywordArr);
		close(conn);
		return totalContent;
	}
	//3가지기능 : product수정, file제거, file추가
	public int updateProduct(Product product, int[] prevAttachNo, pAttach[] attArr) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = marketDao.updateProduct(conn,product);

			if(prevAttachNo!=null) {
				for(int i=0;i<prevAttachNo.length;i++) {
					result = marketDao.deleteAttachment(conn,prevAttachNo[i]);	//delete previous files
				}
			}
			
			int i=0;
			while(attArr[i]!=null) {
				attArr[i].setProductNo(product.getNo());
				result = marketDao.insertAttachment(conn,attArr[i]);	//insert new files
				i++;
			}
			
			
			
			
			commit(conn);
		} catch (Exception e) {
//			e.printStackTrace();
			rollback(conn);
			result=0;
			throw e;
		} finally {
			close(conn);			
		}
		
		return result;
	}

	/**
	 * 
	 * 댓글리스트
	 */
	public List<ProductComment> selectCommentList(int no) {
		
		Connection conn = getConnection();
		
		List<ProductComment> list = marketDao.selectCommentList(conn, no);
		
		close(conn);
		
		return list;
	}
	
	/**
	 * 
	 * 댓글 추가
	 */
	public int insertMarketComment(ProductComment pc) {
		
		Connection conn = getConnection();
		
		int result = 0;
		try {
			result = marketDao.insertMarketComment(conn, pc);
			commit(conn);
		}catch (Exception e){
			rollback(conn);
			throw e;
		} finally {
			close(conn);
			
		
		}
		return result;
	}
	
	/**
	 *  
	 * 댓글 삭제
	 */
	public int deleteMarketComment(int no, int boardNo) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
		result = marketDao.deleteMarketComment(conn, no, boardNo);
		
		
			commit(conn);
		} catch(Exception e) {

			rollback(conn);
			throw e; //controller가 예외처리를 결정할 수 있도록 넘김
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteProduct(int no) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = marketDao.deleteProduct(conn,no);
			if(result==0)
				throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. : "+no);
			commit(conn);
		} catch (Exception e) {
//			e.printStackTrace();
			rollback(conn);
			throw e; //controller가 예외처리를 결정할 수 있도록 연결.
		} finally {
			close(conn);
		}

		return result;
	}

	public List<ProductCommentExt> selectCommentExtList(int no) {

		Connection conn = getConnection();
		
		List<ProductCommentExt> list = marketDao.selectCommentExtList(conn, no);
		
		close(conn);
		
		return list;
	}

	public List<Product> selectMemberList(String id) {
		Connection conn = getConnection();
		
		List<Product> list = marketDao.selectMemberList(conn, id);
		
		close(conn);
		
		return list;
	}

}
