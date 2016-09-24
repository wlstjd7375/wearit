package kr.wearit.android.model;

import java.util.ArrayList;
import java.util.List;

public class PostObservable {

	public static class Observer {

		public void onAdd(Board board, Shop shop) {
		}

		public void onRemove(Post post) {
		}
	}

	//

	private static List<Observer> observers = new ArrayList<Observer>();

	//

	public static void add(Observer observer) {
		observers.add(observer);
	}

	public static void remove(Observer observer) {
		observers.remove(observer);
	}

	public static void notifyAdd(Board board, Shop shop) {
		for (Observer observer : observers)
			observer.onAdd(board, shop);
	}

	public static void notifyRemove(Post post) {
		for (Observer observer : observers)
			observer.onRemove(post);
	}
}
