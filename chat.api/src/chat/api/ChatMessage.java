package chat.api;

public class ChatMessage {
	private long timestamp;
	private String user;
	private String text;

	public ChatMessage() {
	}
	
	public ChatMessage(long timestamp, String user, String text) {
		this.timestamp = timestamp;
		this.user = user;
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
