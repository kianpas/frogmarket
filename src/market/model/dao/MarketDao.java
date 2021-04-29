package market.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import market.model.vo.Product;
import market.model.vo.ProductComment;
import market.model.vo.ProductCommentExt;
import market.model.vo.pAttach;

public class MarketDao {
	private Properties prop = new Properties();

	public MarketDao() {
		// board-query.properties의 내용 읽어와서 prop에 저장

		String fileName = MarketDao.class // 클래스 객체
				.getResource("/sql/market/market-query.properties") // url객체
				.getPath(); // String객체 : 절대경로
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertProduct(Connection conn, Product product) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProduct");
		//String sql = "insert into p_board values( seq_p_board_no.nextval ,?,?,?,?,?,default,?)";
		int result = 0;

		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setString(1, product.getId());
			pstmt.setString(2, product.getTitle());
			pstmt.setString(3, product.getStatus());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getDescription());
			pstmt.setString(6, product.getArea());
			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			// 5. 자원반납(pstmt)
			close(pstmt);
		}

		return result;
	}

	public int selectLastProductNo(Connection conn) {
		int boardNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectLastProductNo");
		//String sql = "select seq_p_board_no.currval board_no from dual";
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				boardNo = rset.getInt("board_no");
			}
		} catch (SQLException e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return boardNo;
	}

	public int insertAttachment(Connection conn, pAttach attach) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertAttachment");
//		String sql = "insert into p_attach(no, board_no, original_filename, renamed_filename) values(seq_p_attach_no.nextval,?,?,?)";
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setInt(1, attach.getProductNo());
			pstmt.setString(2, attach.getOriginalFileName());
			pstmt.setString(3, attach.getRenamedFileName());
			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (Exception e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public Product selectProduct(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectProduct");
//		String sql = "select * from p_board where board_no=?";
		ResultSet rset = null;
		Product product = null;
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			rset = pstmt.executeQuery();
			// 4-1) ResultSet -> Java객체 옮겨담기
			while (rset.next()) {
				product = new Product();
				product.setNo(rset.getInt("board_no"));
				product.setId(rset.getString("seller_id"));
				product.setTitle(rset.getString("title"));
				product.setStatus(rset.getString("status"));
				product.setPrice(rset.getInt("sell_price"));
				product.setDescription(rset.getString("description"));
				product.setRegDate(rset.getDate("reg_date"));
				product.setArea(rset.getString("area_info"));
			}
		} catch (SQLException e) {
//			throw new BoardException("게시글 조회 오류",e);
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return product;
	}

	public pAttach selectAttach(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectAttach");
//		String sql = "select * from p_attach where board_no=?";
		ResultSet rset = null;
		pAttach attach = null;
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			rset = pstmt.executeQuery();
			// 4-1) ResultSet -> Java객체 옮겨담기
			while (rset.next()) {
				attach = new pAttach();

				attach.setNo(rset.getInt("no"));
				attach.setProductNo(rset.getInt("board_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));
			}

		} catch (Exception e) {
//			throw new BoardException("첨부파일 조회 오류",e);
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return attach;
	}

	public List<Product> selectList(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectList");
//		String sql = "select * from(select row_number() over(order by b.board_no desc) rnum,  b.*, a.filename from p_board b left join (select B.board_no, min(no), min(a.renamed_filename) filename from p_board B left join p_attach A on B.board_no = A.board_no group by B.board_no) a on b.board_no = a.board_no) B where rnum between ? and ?";
		ResultSet rset = null;
		List<Product> list = new ArrayList<Product>();
		Product product = null;
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			rset = pstmt.executeQuery();
			// 4-1) ResultSet -> Java객체 옮겨담기
			while (rset.next()) {
				product = new Product();
				product.setNo(rset.getInt("board_no"));
				product.setTitle(rset.getString("title"));
				product.setStatus(rset.getString("status"));
				product.setPrice(rset.getInt("sell_price"));
				product.setArea(rset.getString("area_info"));

//				product.setId(rset.getString("seller_id"));
//				product.setDescription(rset.getString("description"));
//				product.setRegDate(rset.getDate("reg_date"));

				// 첨부파일이 있는 경우
//				if(rset.getInt("attach_no")!=0) {
//					pAttach attach = new pAttach();
//					attach.setProductNo(rset.getInt("board_no"));
//					attach.setNo(rset.getInt("no"));
//					attach.setOriginalFileName(rset.getString("original_filename"));
//					attach.setRenamedFileName(rset.getString("renamed_filename"));
//					product.setAttach(attach);
//				}
				// 첨부파일이 있는 경우
				if (rset.getString("filename") != null) {
					pAttach attach = new pAttach();
					attach.setRenamedFileName(rset.getString("filename"));
//					System.out.println("selectList@marketDao : "+attach.getRenamedFileName());
					product.setAttach(attach);
				}

				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int selectProductCount(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectProductCount");
//		String sql = "select count(*) cnt from p_board";
		int count = 0;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				count = rset.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return count;
	}

	public List<pAttach> selectAttachList(Connection conn, int no) {
		PreparedStatement pstmt = null;
		List<pAttach> list = new ArrayList<>();
		String sql = prop.getProperty("selectAttachList");
//		String sql = "select * from p_attach where board_no=?";
		ResultSet rset = null;
		pAttach attach = null;
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			rset = pstmt.executeQuery();
			// 4-1) ResultSet -> Java객체 옮겨담기
			while (rset.next()) {
				attach = new pAttach();

				attach.setNo(rset.getInt("no"));
				attach.setProductNo(rset.getInt("board_no"));
				attach.setOriginalFileName(rset.getString("original_filename"));
				attach.setRenamedFileName(rset.getString("renamed_filename"));

				list.add(attach);
			}

		} catch (Exception e) {
//			throw new BoardException("첨부파일 조회 오류",e);
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public List<Product> searchProductList(Connection conn, String[] keywordArr, int start, int end) {
		PreparedStatement pstmt = null;

		String sql = prop.getProperty("searchProductList");
//		String sql = "select * from ( select row_number() over(order by B.board_no desc) rnum, B.*,filename from p_board B left join (select B.board_no, min(no), min(a.renamed_filename) filename from p_board B left join p_attach A on B.board_no = A.board_no group by B.board_no) a on b.board_no = a.board_no where # ) B where rnum between ? and ?";

		sql = setQuery(sql, keywordArr);
		System.out.println("searchProductList : " + sql);

		ResultSet rset = null;
		List<Product> list = new ArrayList<>();
		Product product = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				product = new Product();
				product.setNo(rset.getInt("board_no"));
				product.setId(rset.getString("seller_id"));
				product.setTitle(rset.getString("title"));
				product.setStatus(rset.getString("status"));
				product.setPrice(rset.getInt("sell_price"));
				product.setDescription(rset.getString("description"));
				product.setRegDate(rset.getDate("reg_date"));
				product.setArea(rset.getString("area_info"));

				// 첨부파일이 있는 경우
				if (rset.getString("filename") != null) {
					pAttach attach = new pAttach();
					attach.setRenamedFileName(rset.getString("filename"));
//					System.out.println("selectList@marketDao : "+attach.getRenamedFileName());
					product.setAttach(attach);
				}
				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 5. 자원반납(생성역순 rset - pstmt)
		close(rset);
		close(pstmt);

		return list;
	}

	public String setQuery(String sql, String[] keywordArr) {
		String sharp = "";
		for (String str : keywordArr) {
			if (sharp != "")
				sharp += "and ";
			sharp += "title like '%" + str + "%'";
		}
		sql = sql.replace("#", sharp);
		return sql;
	}

	public int searchProductCount(Connection conn, String[] keywordArr) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("searchProductCount");
//		String sql = "select count(*) from p_board where #";

		sql = setQuery(sql, keywordArr);

		int count = 0;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				count = rset.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(rset);
		close(pstmt);

		return count;
	}

	public int updateProduct(Connection conn, Product product) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateProduct");
//		String sql = "update p_board set title=? ,status=? ,sell_price=? ,description=? ,area_info=? where board_no=?";
		int result = 0;

		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setString(1, product.getTitle());
			pstmt.setString(2, product.getStatus());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getArea());
			pstmt.setInt(6, product.getNo());
			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			// 5. 자원반납(pstmt)
			close(pstmt);
		}

		return result;
	}

	/**
	 * 
	 * 댓글출력
	 */
	public List<ProductComment> selectCommentList(Connection conn, int no) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCommentList");
		//String sql = "select * from reply where board_no = ?";
		List<ProductComment> commentList = null;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			commentList = new ArrayList<>();

			while (rset.next()) {
				ProductComment pc = new ProductComment();

				pc.setNo(rset.getInt("no"));
				pc.setBoardNo(rset.getInt("board_no"));
				pc.setWriter(rset.getString("member_id"));
				pc.setContent(rset.getString("content"));
				pc.setRegDate(rset.getDate("reg_date"));

				commentList.add(pc);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return commentList;
	}

	/**
	 * 
	 * 댓글추가
	 */
	public int insertMarketComment(Connection conn, ProductComment pc) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMarketComment");
		//String sql = "insert into reply(no, board_no, member_id, content, reg_date) values(seq_reply_no.nextval, ?, ?, ?, default)";
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pc.getBoardNo());
			pstmt.setString(2, pc.getWriter());
			pstmt.setString(3, pc.getContent());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(pstmt);
		}

		return result;
	}

	/**
	 * 
	 * 댓글 삭제
	 */
	public int deleteMarketComment(Connection conn, int no, int boardNo) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMarketComment");
		//String sql = "delete from reply where no = ? and board_no = ?";

		// prop.getProperty("deleteBoardComment");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no); // where no = '2' 자동형변환
			pstmt.setInt(2, boardNo);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(pstmt);
		}

		return result;
	}

	public int deleteAttachment(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteAttachment");
//		String sql = "delete from p_attach where no=?";
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setInt(1, no);
			// 4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (Exception e) {
//			throw new BoardException("게시물 첨부파일 등록 오류",e);
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int deleteProduct(Connection conn, int no) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteProduct");
//		String sql = "delete from p_board where board_no=?";
		int result = 0;

		try {
			//3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			//3-1) 미완성쿼리의 '?'에 값 대입
			pstmt.setInt(1, no);
			//4. 실행 DML(executeUpdate) -> int , DQL(executeQuery) -> REsultSet
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			//throw new BoardException("게시물 삭제 오류",e);
			e.printStackTrace();
		}finally {
			//5. 자원반납(pstmt)
			close(pstmt);
		}
		
		return result;
	}

	public List<ProductCommentExt> selectCommentExtList(Connection conn, int no) {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCommentListExt");
		//String sql = "select * from reply R join member M using(member_id) where board_no = ?";
		List<ProductCommentExt> commentList = null;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			rset = pstmt.executeQuery();

			commentList = new ArrayList<>();

			while (rset.next()) {
				ProductCommentExt pc = new ProductCommentExt();

				pc.setNo(rset.getInt("no"));
				pc.setBoardNo(rset.getInt("board_no"));
				pc.setWriter(rset.getString("member_id"));
				pc.setContent(rset.getString("content"));
				pc.setRegDate(rset.getDate("reg_date"));
				pc.setPassword(rset.getString("password"));
				pc.setMemberRole(rset.getString("member_role"));
				pc.setPhone(rset.getString("phone"));
				pc.setEmail(rset.getString("email"));
				pc.setEnrollDate(rset.getDate("enroll_date"));
				pc.setGoodScore(rset.getInt("good_score"));
				pc.setNickId(rset.getString("nick_id"));
				pc.setIcon(rset.getString("icon"));

				commentList.add(pc);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return commentList;
	}

	public List<Product> selectMemberList(Connection conn, String id) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectMemberList");
		ResultSet rset = null;
		List<Product> list = new ArrayList<Product>();
		Product product = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				product = new Product();
				product.setNo(rset.getInt("board_no"));
				product.setTitle(rset.getString("title"));
				product.setStatus(rset.getString("status"));
				product.setPrice(rset.getInt("sell_price"));
				product.setArea(rset.getString("area_info"));
				product.setId(rset.getString("seller_id"));
				product.setDescription(rset.getString("description"));
				product.setRegDate(rset.getDate("reg_date"));

				// 첨부파일이 있는 경우
				if (rset.getString("filename") != null) {
					pAttach attach = new pAttach();
					attach.setRenamedFileName(rset.getString("filename"));
					product.setAttach(attach);
				}

				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}
	
	
}
