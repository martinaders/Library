package presentation.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;
import presentation.model.TabBookModel;

/**
 * Displays formatted details of a book (without loan details).
 */
public class TabBookDetailJPanel extends JPanel implements Observer {
	private static final long serialVersionUID = -5782308158955619340L;
	private static final String TITLE = "Katalogdaten";
	private static final String AUTHOR_LABEL_TEXT = "Autor: ";
	private static final String CONDITION_LABEL_TEXT = "Zustand: ";
	private static final String PUBLISHER_LABEL_TEXT = "Verlag: ";
	private static final Font DETAIL_LABEL_FONT = new Font("SansSerif", Font.BOLD, 16);
	private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
	private static final String NO_BOOK_ACTIVE_TEXT = "Kein Buch ausgewählt. \n\nBitte unter Recherche ein Buch suchen und auswählen, um seine Details hier anzuzeigen.";
	private JTextArea titleText;
	private JLabel authorLabel;
	private JLabel publishLabel;
	private JTextField authorText;
	private JTextField publishText;
	private JLabel conditionLabel;
	private DetailTextField conditionText;
	private TabBookModel bmodel;
	private final ModelController controller;

	public TabBookDetailJPanel(ModelController controller) {
		this.controller = controller;
		bmodel = controller.booktab_model;
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder(TITLE));

		initTitleText();
		initAuthorText();
		initPublishText();
		initConditionText();
	}
	
	private void initTitleText() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		titleText = new JTextArea();
		titleText.setText(NO_BOOK_ACTIVE_TEXT);
		titleText.setEditable(false);
		titleText.setBackground(this.getBackground());
		titleText.setLineWrap(true);
		titleText.setFont(TITLE_FONT);
		titleText.addMouseListener(new CopyPasteTextFieldListener("Copy title",
				titleText, controller));
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.00001;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(titleText, c);
	}
	
	private void initAuthorText() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		authorLabel = new JLabel(AUTHOR_LABEL_TEXT);
		authorLabel.setVisible(false);
		authorLabel.setFont(DETAIL_LABEL_FONT);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		add(authorLabel, c);
		
		authorText = new DetailTextField();
		authorText.setEditable(false);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.0;
		add(authorText, c);
	}
	
	private void initPublishText() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		publishLabel = new JLabel(PUBLISHER_LABEL_TEXT);
		publishLabel.setVisible(false);
		publishLabel.setFont(DETAIL_LABEL_FONT);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.0;
		add(publishLabel, c);
		
		publishText = new DetailTextField();
		publishText.setEditable(false);
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1.0;
		add(publishText, c);
	}
	
	private void initConditionText() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		conditionLabel = new JLabel(CONDITION_LABEL_TEXT);
		conditionLabel.setVisible(false);
		conditionLabel.setFont(DETAIL_LABEL_FONT);
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		add(conditionLabel, c);
		
		conditionText = new DetailTextField();
		conditionText.setEditable(false);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(conditionText, c);
	}

	private void setDetailsVisibility(boolean isBookActive) {
		authorText.setVisible(isBookActive);
		authorLabel.setVisible(isBookActive);
		publishLabel.setVisible(isBookActive);
		publishText.setVisible(isBookActive);
		conditionText.setVisible(isBookActive);
		conditionLabel.setVisible(isBookActive);
	}
	
	private void updateDetails() {
		conditionText.setText(bmodel.getActiveBook().getConditionString());
		titleText.setText(bmodel.getActiveBook().getTitle().getName());
		authorText.setText(bmodel.getActiveBook().getTitle().getAuthor());
		publishText.setText(bmodel.getActiveBook().getTitle().getPublisher());
	}

	public void update(Observable o, Object arg) {
		boolean isBookActive = bmodel.getActiveBook() != null;
		titleText.setText(isBookActive ? "" : NO_BOOK_ACTIVE_TEXT);
		setDetailsVisibility(isBookActive);

		if (isBookActive)
			updateDetails();
	}
}