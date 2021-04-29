package report.model.vo;

import java.sql.Date;

public class RAttach {

	private int no;
	private int reportNo;
	private String originalFileName;
	private String renamedFileName;
	private Date regDate;
	
	public RAttach() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RAttach(int no, int reportNo, String originalFileName, String renamedFileName, Date regDate) {
		super();
		this.no = no;
		this.reportNo = reportNo;
		this.originalFileName = originalFileName;
		this.renamedFileName = renamedFileName;
		this.regDate = regDate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getReportNo() {
		return reportNo;
	}

	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
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

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "RAttach [no=" + no + ", reportNo=" + reportNo + ", originalFileName=" + originalFileName
				+ ", renamedFileName=" + renamedFileName + ", regDate=" + regDate + "]";
	}

}
