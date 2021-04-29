package member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import market.model.vo.Product;
import market.model.vo.pAttach;
import member.model.vo.Cart;
import member.model.vo.Member;

public class MemberDao {

	private Properties prop = new Properties();

	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member selectOne(Connection conn, Member loginMember) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("selectOne");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, loginMember.getMemberId());
			pstmt.setString(2, loginMember.getPassword());

			rset = pstmt.executeQuery();

			if (rset.next()) {
				member = new Member();

				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				member.setNickId(rset.getString("NICK_ID"));
				member.setGoodScore(rset.getDouble("GOOD_SCORE"));
				member.setIcon(rset.getString("icon"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return member;
	}

	public int insertMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertMember");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberRole());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.setDouble(6, member.getGoodScore());
			pstmt.setString(7, member.getNickId());
			pstmt.setString(8, member.getIcon());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateMember");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getNickId());
			pstmt.setString(6, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int deleteMember(Connection conn, String membmerId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteMember");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, membmerId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Member> selectList(Connection conn) {
		List<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("selectList");

		try {

			pstmt = conn.prepareStatement(query);

			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				member.setGoodScore(rset.getDouble("GOOD_SCORE"));
				member.setNickId(rset.getString("NICK_ID"));
				member.setIcon(rset.getString("icon"));

				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public List<Member> selectList(Connection conn, int start, int end) {
		List<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectPagedList");
		try {

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				member.setGoodScore(rset.getDouble("GOOD_SCORE"));
				member.setNickId(rset.getString("NICK_ID"));
				member.setIcon(rset.getString("icon"));

				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int updateMemberRole(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateMemberRole");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, member.getMemberRole());
			pstmt.setString(2, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public List<Member> searchMember(Connection conn, Map<String, String> param) {
		List<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("searchPagedMember");

		query = setQuery(query, param.get("searchType"), param.get("searchKeyword"));

		try {

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param.get("start"));
			pstmt.setString(2, param.get("end"));

			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				member.setGoodScore(rset.getDouble("GOOD_SCORE"));
				member.setNickId(rset.getString("NICK_ID"));
				member.setIcon(rset.getString("icon"));

				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int selectMemberCount(Connection conn) {
		int totalContents = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("selectMemberCount");

		try {

			pstmt = conn.prepareStatement(query);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				totalContents = rset.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}

	public int searchMemberCount(Connection conn, Map<String, String> param) {
		int totalContents = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("searchMemberCount");

		query = setQuery(query, param.get("searchType"), param.get("searchKeyword"));
		System.out.println("query@dao = " + query);

		try {

			pstmt = conn.prepareStatement(query);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				totalContents = rset.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}

	public String setQuery(String query, String searchType, String searchKeyword) {
		switch (searchType) {
		case "memberId":
			query = query.replace("#", " member_id like '%" + searchKeyword + "%'");
			break;
		case "memberName":
			query = query.replace("#", " member_name like '%" + searchKeyword + "%'");
			break;
		case "gender":
			query = query.replace("#", " gender = '" + searchKeyword + "'");
			break;
		}

		return query;
	}

	public Member selectMemberId(Connection conn, String id) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query =  prop.getProperty("selectMemberId");
		//String query = "select * from member where member_id=?";
		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, id);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				member.setGoodScore(rset.getDouble("GOOD_SCORE"));
				member.setNickId(rset.getString("NICK_ID"));
				member.setIcon(rset.getString("icon"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return member;

	}

	public int addHeart(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		//String query = "update member set good_score = ? where member_id = ?";
		String query = prop.getProperty("addHeart");
		
		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setDouble(1, member.getGoodScore() + 1);
			pstmt.setString(2, member.getMemberId());

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
	 * 장바구니 담기
	 */
	public int insertCart(Connection conn, Cart cart) {

		PreparedStatement pstmt = null;
		//String sql = "insert into cart values(seq_cart_no.nextval, ?, ?, default)";
		String sql = prop.getProperty("insertCart");
		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, cart.getMemberId());
			pstmt.setInt(2, cart.getBoardNo());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 장바구니 db에서 가져오기
	 */
	public List<Cart> selectCartList(Connection conn, String memberId) {

		PreparedStatement pstmt = null;
		//String sql = "select * from cart where member_id = ?";
		String sql = prop.getProperty("selectCartList");
		List<Cart> list = null;
		ResultSet rset = null;

		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberId);

			rset = pstmt.executeQuery();

			list = new ArrayList<Cart>();

			while (rset.next()) {

				Cart cart = new Cart();
				cart.setBasketNo(rset.getInt("basket_no"));
				cart.setMemberId(rset.getString("member_id"));
				cart.setBoardNo(rset.getInt("board_no"));
				cart.setRegDate(rset.getDate("reg_date"));

				list.add(cart);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	/**
	 * 장바구니 삭제
	 */
	public int deleteCart(Connection conn, Cart cart) {
		int result = 0;
		PreparedStatement pstmt = null;
		//String sql = "delete from cart where member_id = ? and board_no = ?";
		String sql = prop.getProperty("deleteCart");
		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(sql);
			// 쿼리문미완성
			pstmt.setString(1, cart.getMemberId());
			pstmt.setInt(2, cart.getBoardNo());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 장바구니용 상품 선택
	 * 
	 * @param conn
	 * @param boardNo
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Product> selectList(Connection conn, List<Cart> cList, int start, int end) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectPCartList");
		//String sql = "select * from(select row_number() over(order by b.board_no desc) rnum,  b.*, a.filename from p_board b left join (select B.board_no, min(no), min(a.renamed_filename) filename from p_board B left join p_attach A on B.board_no = A.board_no group by B.board_no) a on b.board_no = a.board_no) B where board_no = ?";

		ResultSet rset = null;
		List<Product> list = new ArrayList<Product>();
		Product product = null;
		try {
			// 3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// pstmt.setInt(1, start);
			// pstmt.setInt(2, end);
			for (Cart c : cList) {
				pstmt.setInt(1, c.getBoardNo());

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

					// 첨부파일이 있는 경우
					if (rset.getString("filename") != null) {
						pAttach attach = new pAttach();
						attach.setRenamedFileName(rset.getString("filename"));
						product.setAttach(attach);
					}
					list.add(product);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int selectCartCount(Connection conn, String memberId) {

		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectCartCount");
		//String sql = "select count(*) cnt from cart where member_id = ?";
		int count = 0;

		ResultSet rset = null;
		try {
			System.out.println(memberId);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberId);

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

	public List<Cart> selectCartList(Connection conn, String memberId, int boardNo) {
		PreparedStatement pstmt = null;
		//String sql = "select * from cart where member_id = ?";
		String sql = prop.getProperty("checkCartList");
		List<Cart> list = null;
		ResultSet rset = null;

		try {

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			pstmt.setInt(2, boardNo);
			System.out.println(sql);
			rset = pstmt.executeQuery();

			list = new ArrayList<Cart>();

			while (rset.next()) {

				Cart cart = new Cart();
				cart.setBasketNo(rset.getInt("basket_no"));
				cart.setMemberId(rset.getString("member_id"));
				cart.setBoardNo(rset.getInt("board_no"));
				cart.setRegDate(rset.getDate("reg_date"));

				list.add(cart);
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
