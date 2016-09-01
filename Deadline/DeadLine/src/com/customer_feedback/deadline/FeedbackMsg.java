package com.customer_feedback.deadline;

public class FeedbackMsg {
	public static final int TYPE_RECRIVE=0;
	public static final int TYPE_SEND=1;
	private String content;
	private int type;
	
	public FeedbackMsg(String content, int type) {
		super();
		this.content = content;
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
