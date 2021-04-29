package graph.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import graph.model.vo.Graph;
import market.model.vo.Product;

import static common.JDBCTemplate.*;

public class GraphDao {
	private Properties prop = new Properties();

	public GraphDao() {
		String fileName = GraphDao.class.getResource("/sql/graph/graph-query.properties").getPath();

		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Graph> selectList(Connection conn, String keyword) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Graph> list = null;
		
		String sql = prop.getProperty("graphSelectList");
	
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + keyword+"%");
			
			rset = pstmt.executeQuery();
					
			
			list = new ArrayList<Graph>();
			
		
			while(rset.next()) {
								
				Graph graph = new Graph();
				
				graph.setBoardNo(rset.getInt("board_no"));
				graph.setSellerId(rset.getString("seller_id"));
				graph.setTitle(rset.getString("title"));
				graph.setStatus(rset.getString("status"));
				graph.setPrice(rset.getInt("sell_price"));
				graph.setDesc(rset.getString("description"));
				graph.setRegDate(rset.getDate("reg_date"));
				graph.setArea(rset.getString("area_info"));
				
				list.add(graph);
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
	 * 문자열 자르기 메소드
	 */
	public String setQuery(String sql, String[] keywordArr) {
		String sharp = "";
		for(String str : keywordArr) {
			if(sharp!="")
				sharp+="and ";
			sharp+="title like '%" + str + "%'";
		}
		sql = sql.replace("#", sharp);
		return sql;
	}
	
	
	/**
	 * 검색 리스트 출력 
	 */
	public List<Graph> searchProductList(Connection conn, String[] keywordArr) {
		PreparedStatement pstmt = null;

		String sql = prop.getProperty("searchProductList");
		//String sql = "select * from (select row_number() over(order by board_no desc) rnum, B.* from p_board B where # ) B";

		sql = setQuery(sql, keywordArr);
		System.out.println("searchProductList : "+sql);
		
		ResultSet rset=null;
		List<Graph> list = new ArrayList<>();
		Graph graph = null;
		try {
			pstmt = conn.prepareStatement(sql);
		
			
			rset = pstmt.executeQuery();

			while(rset.next()) {
				graph = new Graph();
				graph.setBoardNo(rset.getInt("board_no"));
				//graph.set(rset.getString("seller_id"));
				graph.setTitle(rset.getString("title"));
				graph.setStatus(rset.getString("status"));
				graph.setPrice(rset.getInt("sell_price"));
				graph.setDesc(rset.getString("description"));
				graph.setRegDate(rset.getDate("reg_date"));
				graph.setArea(rset.getString("area_info"));
				
				list.add(graph);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//5. 자원반납(생성역순 rset - pstmt)
		close(rset);
		close(pstmt);
		
		return list;
	}

}
