package presentation.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import presentation.model.ActionPanelModel;
import presentation.model.MainWindowModel;

/**
 * Represents the tabbed pane on which the (three) main panes are placed.
 */
public class LibTabPane extends JTabbedPane implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel[] tabPanel;
	private MainWindowModel model;
	private String[][] tabInformation = {
			{ "Recherche", "img/search.png",
					"Suchen nach Benutzern oder Büchern" },
			{ "Buch", "img/book.png", "Details eines Buches anzeigen" },
			{ "Benutzer", "img/user.png",
					"Personalien und Ausleihen eines Benutzers anzeigen" } };
	private LibTabPaneModel tabModel;

	public LibTabPane(MainWindowModel model) {
		this.model = model;
		tabModel = new LibTabPaneModel();
		initGUI();
		initChangeListener();
	}

	private void initGUI() {
		tabPanel = new JPanel[tabInformation.length];
		ActionPanelModel action_panel_model = new ActionPanelModel();

		tabPanel[0] = new TabSearchPanel(action_panel_model, tabModel);
		tabPanel[1] = new TabBookPanel(action_panel_model);
		tabPanel[2] = new TabUserPanel(action_panel_model);

		for (int i = 0; i < tabInformation.length; i++) {
			this.addTab(null, null, tabPanel[i], tabInformation[i][2]);
			ImageIcon image = new ImageIcon(tabInformation[i][1]);
			JLabel paneTitle = new JLabel(tabInformation[i][0], image,
					JLabel.TRAILING);
			paneTitle.setVerticalTextPosition(JLabel.BOTTOM);
			paneTitle.setHorizontalTextPosition(JLabel.CENTER);
			this.setTabComponentAt(i, paneTitle);
		}

		this.setTabPlacement(JTabbedPane.LEFT);
	}

	private void initChangeListener() {
		final JTabbedPane t = this;
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				model.setActiveTab(t.getSelectedIndex());
			}
		});
	}

	/**
	 * Dient dem ActionListener der JMenuBar oder einem anderen Controller, den
	 * Tab zu wechseln.
	 * 
	 * @param newTabIndex
	 *            TabsPanel.SEARCH, TabsPanel.BOOK oder TabsPanel.USER
	 */
	public void switchTo(int newTabIndex) {
		if (newTabIndex >= getTabCount())
			return;
		setSelectedIndex(newTabIndex);
	}

	public TabSearchPanel getSearchPanel() {
		return (TabSearchPanel) (tabPanel[model.SEARCH_TAB]);
	}

	public TabBookPanel getBookPanel() {
		return (TabBookPanel) (tabPanel[model.BOOK_TAB]);
	}
	
	public String getActiveTabTitle() {
		if (getSelectedIndex() > tabInformation.length)
			return "";
		return tabInformation[getSelectedIndex()][0];
	}

	public void update(Observable o, Object arg) {
		switchTo(tabModel.getActiveTab());
		getBookPanel().getModel().setActiveBook(model.getActiveBook());
	}

}
