package market.model.vo;

import java.sql.Date;


public class Product {
	private int no;
	private String id;
	private String title;
	private String Status;
	private int price;
	private String description;
	private Date regDate;
	private String area;
	private pAttach attach;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(int no, String id, String title, String status, int price, String description, Date regDate,
			String area, pAttach attach) {
		super();
		this.no = no;
		this.id = id;
		this.title = title;
		Status = status;
		this.price = price;
		this.description = description;
		this.regDate = regDate;
		this.area = area;
		this.attach = attach;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public pAttach getAttach() {
		return attach;
	}
	public void setAttach(pAttach attach) {
		this.attach = attach;
	}
	@Override
	public String toString() {
		return "Product [no=" + no + ", id=" + id + ", title=" + title + ", Status=" + Status + ", price=" + price
				+ ", description=" + description + ", regDate=" + regDate + ", area=" + area + ", attach=" + attach
				+ "]";
	}
	
}
