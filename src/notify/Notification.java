package notify;
public class Notification {

	private String type;
	private Object body;

	public Notification(String type, Object body) {
		this.type = type;
		this.body = body;
	}

	public String getType() {
		return type;
	}

	public Object getBody() {
		return body;
	}

}
