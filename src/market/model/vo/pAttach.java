package market.model.vo;

public class pAttach {
	
	private int no;
	private int productNo;
	private String originalFileName;
	private String renamedFileName;
	public pAttach() {
		super();
		// TODO Auto-generated constructor stub
	}
	public pAttach(int no, int productNo, String originalFileName, String renamedFileName) {
		super();
		this.no = no;
		this.productNo = productNo;
		this.originalFileName = originalFileName;
		this.renamedFileName = renamedFileName;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getRenamedFileName() {
		return renamedFileName;
	}
	public void setRenamedFileName(String renamedFileName) {
		this.renamedFileName = renamedFileName;
	}
	@Override
	public String toString() {
		return "pAttach [no=" + no + ", productNo=" + productNo + ", originalFileName=" + originalFileName
				+ ", renamedFileName=" + renamedFileName + "]";
	}
	
}
