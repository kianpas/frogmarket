package market.model.vo;

import java.sql.Date;

public class ProductComment {

	private int no;			  //PK
	private int boardNo; //참조게시글
	private String writer;
	private String content;
	private Date regDate;
	
	public ProductComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductComment(int no, int boardNo, String writer, String content, Date regDate) {
		super();
		this.no = no;
		this.boardNo = boardNo;
		this.writer = writer;
		this.content = content;
		this.regDate = regDate;
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
		return "ProductComment [no=" + no + ", boardNo=" + boardNo + ", writer=" + writer + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}
	
	
}
