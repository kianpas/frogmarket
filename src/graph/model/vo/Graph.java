package graph.model.vo;

import java.sql.Date;

public class Graph {
	
	private int boardNo;
	private String sellerId;
	private String title;
	private String status;
	private int price;
	private String desc;
	private Date regDate;
	private String area;
	public Graph() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Graph(int boardNo, String sellerId, String title, String status, int price, String desc, Date regDate,
			String area) {
		super();
		this.boardNo = boardNo;
		this.sellerId = sellerId;
		this.title = title;
		this.status = status;
		this.price = price;
		this.desc = desc;
		this.regDate = regDate;
		this.area = area;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Override
	public String toString() {
		return boardNo + "," + sellerId + "," + title + "," + status
				+ "," + price + "," + desc + "," + regDate + "," + area;
	}
	
	

}
