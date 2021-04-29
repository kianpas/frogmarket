package market.model.vo;

import java.io.Serializable;
import java.sql.Date;

import member.model.vo.Member;


public class ProductCommentExt extends Member implements Serializable {

	private int no;			  //PK
	private int boardNo; //참조게시글
	private String writer;
	private String content;
	private Date regDate;
	
	public ProductCommentExt() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProductCommentExt(String memberId, String password, String memberRole, String email, String phone,
			Date enrollDate, String nickId, double goodScore, String icon) {
		super(memberId, password, memberRole, email, phone, enrollDate, nickId, goodScore, icon);
		// TODO Auto-generated constructor stub
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "ProductCommentExt [no=" + no + ", boardNo=" + boardNo + ", writer=" + writer + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}
	
	
	
}
