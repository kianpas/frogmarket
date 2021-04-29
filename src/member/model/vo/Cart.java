package member.model.vo;

import java.sql.Date;

public class Cart {
	private int basketNo;
	private String memberId;
	private int boardNo;
	private Date regDate;
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cart(int basketNo, String memberId, int boardNo, Date regDate) {
		super();
		this.basketNo = basketNo;
		this.memberId = memberId;
		this.boardNo = boardNo;
		this.regDate = regDate;
	}
	public int getBasketNo() {
		return basketNo;
	}
	public void setBasketNo(int basketNo) {
		this.basketNo = basketNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "Cart [basketNo=" + basketNo + ", memberId=" + memberId + ", boardNo=" + boardNo + ", regDate=" + regDate
				+ "]";
	}
	
	
}
