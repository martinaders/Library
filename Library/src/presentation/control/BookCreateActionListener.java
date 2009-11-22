/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;
import domain.Book;
import domain.Title;

public class BookCreateActionListener implements ActionListener {
	private final ModelController controller;

	public BookCreateActionListener(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		Title addTitle = controller.library.createAndAddTitle("Neuer Titel");
		addTitle.setAuthor("");
		addTitle.setPublisher("");
		Book addBook = controller.library.createAndAddBook(addTitle);
		controller.status_model.setTempStatus("Neues Buch erstellt mit id " + addBook.getInventoryNumber());
		controller.booktab_model.setActiveBook(addBook);
		controller.booktab_model.backupBookContent();
		controller.booktab_model.setEditing(true);
		controller.tabbed_model.setBookTabActive();
	}
}