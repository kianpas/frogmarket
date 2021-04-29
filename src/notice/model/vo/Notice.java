package notice.model.vo;

public class Notice {
	private int no;
	private int boardNo;
	private String senderId;
	private String senderNick;
	private String receiverId;
	private String title;
	private String content;
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Notice(int no, int boardNo, String senderId, String senderNick, String receiverId, String title,
			String content) {
		super();
		this.no = no;
		this.boardNo = boardNo;
		this.senderId = senderId;
		this.senderNick = senderNick;
		this.receiverId = receiverId;
		this.title = title;
		this.content = content;
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
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderNick() {
		return senderNick;
	}
	public void setSenderNick(String senderNick) {
		this.senderNick = senderNick;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Notice [no=" + no + ", boardNo=" + boardNo + ", senderId=" + senderId + ", senderNick=" + senderNick
				+ ", receiverId=" + receiverId + ", title=" + title + ", content=" + content + "]";
	}
	
}