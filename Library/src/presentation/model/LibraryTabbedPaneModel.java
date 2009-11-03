package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class LibraryTabbedPaneModel extends Observable {

	private int activeTab = 0;
	private Book activeBook;
	private ModelController controller;
	public String[][] tabInformation = {
			{ "Recherche", "img/search.png",
				"Suchen nach Benutzern oder Büchern" },
			{ "Buch", "img/book.png", "Details eines Buches anzeigen" },
			{ "Benutzer", "img/user.png",
				"Personalien und Ausleihen eines Benutzers anzeigen" } };
	public final static int SEARCH_TAB = 0;
	public final static int BOOK_TAB = 1;
	public final static int USER_TAB = 2;

	public LibraryTabbedPaneModel(ModelController controller) {
		this.controller = controller;
	}
	
	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int newIndex) {
		this.activeTab = newIndex;
		controller.main_model.setTitle(getActiveTabTitle());
		setChanged();
		notifyObservers(newIndex);
	}

	public void setActiveBook(Book newBook) {
		controller.booktab_model.setActiveBook(newBook);
		setChanged();
		notifyObservers(newBook);
	}

	public Book getActiveBook() {
		return activeBook;
	}

	public String getActiveTabTitle() {
		if (getActiveTab() > tabInformation.length)
			return "";
		return tabInformation[getActiveTab()][0];
	}
	
	public String[][] getTabInformation() {
		return tabInformation;
	}

	public void activateUser(Customer selected) {
		controller.activeuser_model.setNewActiveUser(selected);
	}
}