package notify;

import java.util.ArrayList;
import java.util.HashMap;

public class Notify implements Receiver {

	private static ArrayList<Notify> receiver;

	private static HashMap<String, ArrayList<Notify>> notifications;

	private static void addListener(String type, Notify listener) {
		ArrayList<Notify> typeListener = notifications.get(type);
		if (typeListener == null) {
			typeListener = new ArrayList<Notify>();
			notifications.put(type, typeListener);
		}
		typeListener.add(listener);
	}

	private static void notifyListener(Notification note) {
		String type = note.getType();
		ArrayList<Notify> typeListener = notifications.get(type);
		if (typeListener != null && typeListener.size() > 0) {
			for (Notify listener : typeListener) {
				listener.handleNotification(note);
			}
		}
	}

	private void sendNotification(Notification note) {

	}

	private void bindNotification(String type) {

		addListener(type, this);

	}

	public void handleNotification(Notification note) {

	}
}
