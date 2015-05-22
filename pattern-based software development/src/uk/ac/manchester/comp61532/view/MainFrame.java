package uk.ac.manchester.comp61532.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import uk.ac.manchester.comp61532.model.CourseUnitType;

/*
 * This is the GUI class of the application.
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static final String IMG_PATH = "/uk/ac/manchester/comp61532/img/";
	public static final String ADD_BUTTON_IMG = IMG_PATH + "add.png";
	public static final String RMV_BUTTON_IMG = IMG_PATH + "remove.png";

	private final int FRAME_WIDTH = 720;
	private final int FRAME_HEIGHT = 600;
	private final int BUTTON_PANEL_WIDTH = 650;
	private final int BUTTON_PANEL_HEIGHT = 70; // 90
	private final int ADD_COURSE_UNIT_PANEL_WIDTH = 650;
	private final int ADD_COURSE_UNIT_PANEL_HEIGHT = 240;
	private final int INNER_ADD_COURSE_UNIT_PANEL_WIDTH = 540;
	private final int INNER_ADD_COURSE_UNIT_PANEL_HEIGHT = 220; // 120
	private final int INNER_REMOVE_BUTTON_PANEL_WIDTH = 520;
	private final int INNER_REMOVE_BUTTON_PANEL_HEIGHT = 25;
	private final int LARGE_TEXT_FIELD_SIZE = 18;
	private final int SMALL_TEXT_FIELD_SIZE = 3;

	private final String WINDOW_TITLE = "Course Unit Management System";
	private final String BUTTON_PANEL_BORDER_TITLE = "Add/Remove Course Units";
	private final String ADD_TOOLTIP = "ADD A NEW COURSE UNIT!";
	private final String REMOVE_TOOLTIP = "REMOVE COURSE UNIT!";
	private final String REMOVE_ALL_TOOLTIP = "REMOVE ALL COURSE UNITS!";
	private final String SUBMIT_BUTTON_NAME = "Submit";
	private final String FILE_MENU_NAME = "File";
	private final String SAVE_FILE_AS_NAME = "Save File As...";
	private final String SERIALIZABLE_FILE_NAME = "Serializable file";
	private final String CSV_FILE_NAME = "CSV file";

	private JPanel containerPanel;
	private JPanel buttonPanel;

	private JButton addButton;
	private JButton removeButton;
	private JButton submitButton;

	private ArrayList<JPanel> listOfNewCourseUnitPanels = new ArrayList<JPanel>();
	private ArrayList<JPanel> listOfInnerCourseUnitPanels = new ArrayList<JPanel>();
	private Map<JPanel, JPanel> mapOfInnerCourseUnitPanelToPanel = new HashMap<JPanel, JPanel>();
	private Map<JPanel, JScrollPane> mapOfScrollPanes = new HashMap<JPanel, JScrollPane>();
	private Map<JButton, JPanel> mapOfInnerRemoveButtonToPanel = new HashMap<JButton, JPanel>();
	@SuppressWarnings("rawtypes")
	private Map<JComboBox, JPanel> mapOfCourseUnitTypeComboBoxToPanel = new HashMap<JComboBox, JPanel>();
	@SuppressWarnings("rawtypes")
	private Map<JPanel, JComboBox> mapOfPanelToCourseUnitTypeComboBox = new HashMap<JPanel, JComboBox>();
	private Map<JPanel, JTextField> mapOfCourseUnitCodeFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JTextField> mapOfCourseUnitNameFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JTextField> mapOfMaxNumOfStudentsFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JTextField> mapOfSemesterFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JTextField> mapOfDurationFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JTextField> mapOfSupervisorFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JTextField> mapOfOrganizationFieldToPanel = new HashMap<JPanel, JTextField>();
	private Map<JPanel, JPanel> mapOfCourseUnitTypeCardToPanel = new HashMap<JPanel, JPanel>();

	private Integer numOfNewCourseUnitPanels = 0;

	private boolean isSerializable = false;
	private boolean isCSV = false;

	public MainFrame() {
		init();
	}

	public void init() {
		buildMenuBar();

		this.setTitle(WINDOW_TITLE);
		this.setResizable(false);
		this.pack();
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		buildContent();
		this.validate();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public void buildContent() {
		containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel,
				BoxLayout.PAGE_AXIS));
		JScrollPane scrollPane = new JScrollPane(containerPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		containerPanel.add(buildButtonPanel());
		this.getContentPane().add(scrollPane);
	}

	private void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = buildFileMenu();

		menuBar.add(fileMenu);

		this.setJMenuBar(menuBar);
	}

	private JMenu buildFileMenu() {
		JMenu fileMenu = new JMenu(FILE_MENU_NAME);

		fileMenu.add(buildSaveFileAsMenuItem());

		return fileMenu;
	}

	private JMenu buildSaveFileAsMenuItem() {
		JMenu saveTestAsMenuItem = new JMenu(SAVE_FILE_AS_NAME);

		ButtonGroup menuButtonGroup = new ButtonGroup();

		JRadioButtonMenuItem serializableFileMenuItem = buildSerializableFileMenuItem();

		menuButtonGroup.add(serializableFileMenuItem);

		JRadioButtonMenuItem csvFileMenuItem = buildCSVFileMenuItem();

		menuButtonGroup.add(csvFileMenuItem);

		menuButtonGroup.setSelected(csvFileMenuItem.getModel(), true);
		setCSV(true);

		saveTestAsMenuItem.add(serializableFileMenuItem);
		saveTestAsMenuItem.add(csvFileMenuItem);

		return saveTestAsMenuItem;
	}

	private JRadioButtonMenuItem buildSerializableFileMenuItem() {
		JRadioButtonMenuItem serializableFileMenuItem = new JRadioButtonMenuItem(
				SERIALIZABLE_FILE_NAME);
		serializableFileMenuItem.setSelected(true);

		setSerializableFileMenuItemListener(serializableFileMenuItem);

		return serializableFileMenuItem;
	}

	private void setSerializableFileMenuItemListener(
			JRadioButtonMenuItem serializableFileMenuItem) {
		serializableFileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSerializable(true);
				setCSV(false);
			}
		});
	}

	private JRadioButtonMenuItem buildCSVFileMenuItem() {
		JRadioButtonMenuItem csvFileMenuItem = new JRadioButtonMenuItem(
				CSV_FILE_NAME);

		setCSVFileMenuItemListener(csvFileMenuItem);

		return csvFileMenuItem;
	}

	private void setCSVFileMenuItemListener(JRadioButtonMenuItem csvFileMenuItem) {
		csvFileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCSV(true);
				setSerializable(false);
			}
		});
	}

	public boolean isSerializable() {
		return isSerializable;
	}

	public void setSerializable(boolean isSerializable) {
		this.isSerializable = isSerializable;
	}

	public boolean isCSV() {
		return isCSV;
	}

	public void setCSV(boolean isCSV) {
		this.isCSV = isCSV;
	}

	private JPanel buildButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH,
				BUTTON_PANEL_HEIGHT));
		buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
		buttonPanel.setBorder(BorderFactory
				.createTitledBorder(BUTTON_PANEL_BORDER_TITLE));
		addButton = new JButton();
		setAddButtonActionListener(addButton);
		addButton.setToolTipText(ADD_TOOLTIP);
		setImgToButton(addButton, ADD_BUTTON_IMG);
		removeButton = new JButton();
		setRemoveButtonActionListener(removeButton);
		removeButton.setToolTipText(REMOVE_ALL_TOOLTIP);
		setImgToButton(removeButton, RMV_BUTTON_IMG);
		submitButton = new JButton(SUBMIT_BUTTON_NAME);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(submitButton);
		return buttonPanel;
	}

	public void setRemoveButtonActionListener(JButton removeButton) {
		removeButton.addActionListener(new ActionListener() {
			@SuppressWarnings("rawtypes")
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer answer = (Integer) JOptionPane
						.showConfirmDialog(
								MainFrame.this,
								"Are you sure that you want to remove ALL course units?",
								"Confirmation", JOptionPane.YES_NO_OPTION);
				if (answer != JOptionPane.YES_OPTION) {
					return;
				}
				Iterator it = mapOfScrollPanes.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					containerPanel.remove((JScrollPane) pair.getValue());
					it.remove(); // avoids a ConcurrentModificationException
				}

				containerPanel.revalidate();
				containerPanel.repaint();
				clearDataStructs(null, e, true);
			}
		});
	}

	public void setAddButtonActionListener(JButton addButton) {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buildAddCourseUnitPanel(null, null, null, null, null, null,
						null, null);
			}
		});
	}

	private JButton setImgToButton(JButton button, String img) {
		try {
			Image image = ImageIO.read(getClass().getResource(img));
			button.setIcon(new ImageIcon(image));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return button;
	}

	public void buildAddCourseUnitPanel(String courseUnitCode,
			String courseUnitName, String courseUnitType,
			String maxNumOfStudents, String semester, String duration,
			String supervisor, String organizationName) {
		JPanel newCourseUnitPanel = new JPanel();
		newCourseUnitPanel.setLayout(new BoxLayout(newCourseUnitPanel,
				BoxLayout.PAGE_AXIS));
		JScrollPane scrollPane = new JScrollPane(newCourseUnitPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(ADD_COURSE_UNIT_PANEL_WIDTH,
				ADD_COURSE_UNIT_PANEL_HEIGHT));
		scrollPane.setMaximumSize(scrollPane.getPreferredSize());
		newCourseUnitPanel.add(buildInnerAddCourseUnitPanel(newCourseUnitPanel,
				courseUnitCode, courseUnitName, courseUnitType,
				maxNumOfStudents, semester, duration, supervisor,
				organizationName));
		listOfNewCourseUnitPanels.add(newCourseUnitPanel);
		mapOfScrollPanes.put(newCourseUnitPanel, scrollPane);
		containerPanel.add(scrollPane);
		revalidate();
		numOfNewCourseUnitPanels++;
	}

	public void buildCourseUnitCodePanel(JPanel innerAddCourseUnitPanel,
			String courseUnitCode) {
		JPanel courseUnitCodePanel = new JPanel();
		JLabel courseUnitCodeLabel = new JLabel("Course Unit Code:");
		courseUnitCodePanel.add(courseUnitCodeLabel);
		JTextField courseUnitCodeField = new JTextField(LARGE_TEXT_FIELD_SIZE);
		courseUnitCodePanel.add(courseUnitCodeField);
		courseUnitCodeField.setText(courseUnitCode);
		innerAddCourseUnitPanel.add(courseUnitCodePanel);
		mapOfCourseUnitCodeFieldToPanel.put(innerAddCourseUnitPanel,
				courseUnitCodeField);
	}

	public void buildCourseUnitNamePanel(JPanel innerAddCourseUnitPanel,
			String courseUnitName) {
		JPanel courseUnitNamePanel = new JPanel();
		JLabel courseUnitNameLabel = new JLabel("Course Unit Name:");
		courseUnitNamePanel.add(courseUnitNameLabel);
		JTextField courseUnitNameField = new JTextField(LARGE_TEXT_FIELD_SIZE);
		courseUnitNamePanel.add(courseUnitNameField);
		courseUnitNameField.setText(courseUnitName);
		innerAddCourseUnitPanel.add(courseUnitNamePanel);
		mapOfCourseUnitNameFieldToPanel.put(innerAddCourseUnitPanel,
				courseUnitNameField);
	}

	public JComboBox<String> buildCourseUnitTypePanel(
			JPanel innerAddCourseUnitPanel) {
		JPanel courseUnitTypePanel = new JPanel();
		JLabel courseUnitTypeLabel = new JLabel("Course Unit Type:");
		courseUnitTypePanel.add(courseUnitTypeLabel);
		JComboBox<String> courseUnitTypeComboBox = new JComboBox<String>();
		courseUnitTypeComboBox.addItem("Taught");
		courseUnitTypeComboBox.addItem("Research");
		courseUnitTypeComboBox.addItem("Internship");
		setComboBoxListener(courseUnitTypeComboBox);
		courseUnitTypePanel.add(courseUnitTypeComboBox);
		innerAddCourseUnitPanel.add(courseUnitTypePanel);
		mapOfCourseUnitTypeComboBoxToPanel.put(courseUnitTypeComboBox,
				innerAddCourseUnitPanel);
		mapOfPanelToCourseUnitTypeComboBox.put(innerAddCourseUnitPanel,
				courseUnitTypeComboBox);

		return courseUnitTypeComboBox;
	}

	public void setComboBoxListener(JComboBox<String> courseUnitTypeComboBox) {
		courseUnitTypeComboBox.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) ((JComboBox<String>) e
						.getSource()).getSelectedItem();
				JPanel innerAddCourseUnitPanel = mapOfCourseUnitTypeComboBoxToPanel
						.get(((JComboBox<String>) e.getSource()));
				JPanel courseUnitTypeCardPanel = mapOfCourseUnitTypeCardToPanel
						.get(innerAddCourseUnitPanel);
				courseUnitTypeCardPanel.removeAll();

				if (selectedItem.equalsIgnoreCase(CourseUnitType.TAUGHT
						.getType())) {
					JPanel semesterPanel = new JPanel();
					JLabel semesterLabel = new JLabel("Semester:");
					semesterPanel.add(semesterLabel);
					JTextField semesterField = new JTextField(
							SMALL_TEXT_FIELD_SIZE);
					semesterPanel.add(semesterField);
					courseUnitTypeCardPanel.add(semesterPanel);
					mapOfSemesterFieldToPanel.put(innerAddCourseUnitPanel,
							semesterField);
				} else if (selectedItem
						.equalsIgnoreCase(CourseUnitType.RESEARCH.getType())) {
					JPanel durationPanel = new JPanel();
					JLabel durationLabel = new JLabel("Duration (year):");
					durationPanel.add(durationLabel);
					JTextField durationField = new JTextField(
							SMALL_TEXT_FIELD_SIZE);
					durationPanel.add(durationField);
					courseUnitTypeCardPanel.add(durationPanel);
					mapOfDurationFieldToPanel.put(innerAddCourseUnitPanel,
							durationField);
					JPanel supervisorPanel = new JPanel();
					JLabel supervisorLabel = new JLabel("Supervisor:");
					supervisorPanel.add(supervisorLabel);
					JTextField supervisorField = new JTextField(
							LARGE_TEXT_FIELD_SIZE);
					supervisorPanel.add(supervisorField);
					courseUnitTypeCardPanel.add(supervisorPanel);
					mapOfSupervisorFieldToPanel.put(innerAddCourseUnitPanel,
							supervisorField);
				} else if (selectedItem
						.equalsIgnoreCase(CourseUnitType.INTERNSHIP.getType())) {
					JPanel durationPanel = new JPanel();
					JLabel durationLabel = new JLabel("Duration (year):");
					durationPanel.add(durationLabel);
					JTextField durationField = new JTextField(
							SMALL_TEXT_FIELD_SIZE);
					durationPanel.add(durationField);
					courseUnitTypeCardPanel.add(durationPanel);
					mapOfDurationFieldToPanel.put(innerAddCourseUnitPanel,
							durationField);
					JPanel organizationPanel = new JPanel();
					JLabel organizationLabel = new JLabel("Organization:");
					organizationPanel.add(organizationLabel);
					JTextField organizationField = new JTextField(
							LARGE_TEXT_FIELD_SIZE);
					organizationPanel.add(organizationField);
					courseUnitTypeCardPanel.add(organizationPanel);
					mapOfOrganizationFieldToPanel.put(innerAddCourseUnitPanel,
							organizationField);
				}
				courseUnitTypeCardPanel.repaint();
				courseUnitTypeCardPanel.revalidate();
			}
		});
	}

	public void buildCourseUnitTypeCardPanel(JPanel innerAddCourseUnitPanel) {
		JPanel courseUnitTypeCardPanel = new JPanel();
		courseUnitTypeCardPanel.setLayout(new BoxLayout(
				courseUnitTypeCardPanel, BoxLayout.PAGE_AXIS));
		innerAddCourseUnitPanel.add(courseUnitTypeCardPanel);
		mapOfCourseUnitTypeCardToPanel.put(innerAddCourseUnitPanel,
				courseUnitTypeCardPanel);
	}

	public void buildMaxNumOfStudentsPanel(JPanel innerAddCourseUnitPanel,
			String maxNumOfStudents) {
		JPanel maxNumOfStudentsPanel = new JPanel();
		JLabel maxNumOfStudentsLabel = new JLabel("Maximum No. of Students:");
		maxNumOfStudentsPanel.add(maxNumOfStudentsLabel);
		JTextField maxNumOfStudentsField = new JTextField(SMALL_TEXT_FIELD_SIZE);
		maxNumOfStudentsPanel.add(maxNumOfStudentsField);
		maxNumOfStudentsField.setText(maxNumOfStudents);
		innerAddCourseUnitPanel.add(maxNumOfStudentsPanel);
		mapOfMaxNumOfStudentsFieldToPanel.put(innerAddCourseUnitPanel,
				maxNumOfStudentsField);
	}

	public JPanel buildInnerAddCourseUnitPanel(JPanel parentPanel,
			String courseUnitCode, String courseUnitName,
			String courseUnitType, String maxNumOfStudents, String semester,
			String duration, String supervisor, String organizationName) {
		JPanel innerAddCourseUnitPanel = new JPanel();
		innerAddCourseUnitPanel.setLayout(new BoxLayout(
				innerAddCourseUnitPanel, BoxLayout.PAGE_AXIS));
		innerAddCourseUnitPanel.setPreferredSize(new Dimension(
				INNER_ADD_COURSE_UNIT_PANEL_WIDTH,
				INNER_ADD_COURSE_UNIT_PANEL_HEIGHT));
		innerAddCourseUnitPanel.setMaximumSize(innerAddCourseUnitPanel
				.getPreferredSize());

		buildCourseUnitCodePanel(innerAddCourseUnitPanel, courseUnitCode);

		buildCourseUnitNamePanel(innerAddCourseUnitPanel, courseUnitName);

		JComboBox<String> courseUnitTypeComboBox = buildCourseUnitTypePanel(innerAddCourseUnitPanel);

		buildCourseUnitTypeCardPanel(innerAddCourseUnitPanel);

		if (null == courseUnitType) {
			courseUnitTypeComboBox.setSelectedIndex(0);
		} else {
			courseUnitTypeComboBox.setSelectedItem(courseUnitType);
			if (CourseUnitType.TAUGHT.getType()
					.equalsIgnoreCase(courseUnitType)) {
				mapOfSemesterFieldToPanel.get(innerAddCourseUnitPanel).setText(
						semester);
			} else if (CourseUnitType.RESEARCH.getType().equalsIgnoreCase(
					courseUnitType)) {
				mapOfDurationFieldToPanel.get(innerAddCourseUnitPanel).setText(
						duration);
				mapOfSupervisorFieldToPanel.get(innerAddCourseUnitPanel)
						.setText(supervisor);
			} else if (CourseUnitType.INTERNSHIP.getType().equalsIgnoreCase(
					courseUnitType)) {
				mapOfDurationFieldToPanel.get(innerAddCourseUnitPanel).setText(
						duration);
				mapOfOrganizationFieldToPanel.get(innerAddCourseUnitPanel)
						.setText(organizationName);
			}
		}

		buildMaxNumOfStudentsPanel(innerAddCourseUnitPanel, maxNumOfStudents);

		buildInnerRemoveButtonPanel(innerAddCourseUnitPanel, parentPanel);

		listOfInnerCourseUnitPanels.add(innerAddCourseUnitPanel);
		mapOfInnerCourseUnitPanelToPanel.put(parentPanel,
				innerAddCourseUnitPanel);
		return innerAddCourseUnitPanel;
	}

	public void buildInnerRemoveButtonPanel(JPanel innerAddCourseUnitPanel,
			JPanel parentPanel) {
		JPanel innerRemoveButtonPanel = new JPanel();
		innerRemoveButtonPanel.setLayout(new BorderLayout());
		innerRemoveButtonPanel.setPreferredSize(new Dimension(
				INNER_REMOVE_BUTTON_PANEL_WIDTH,
				INNER_REMOVE_BUTTON_PANEL_HEIGHT));
		innerRemoveButtonPanel.setMaximumSize(innerRemoveButtonPanel
				.getPreferredSize());
		JButton innerRemoveButton = new JButton();
		setInnerRemoveButtonActionListener(innerRemoveButton);
		setImgToButton(innerRemoveButton, RMV_BUTTON_IMG);
		innerRemoveButton.setToolTipText(REMOVE_TOOLTIP);
		innerRemoveButtonPanel.add(innerRemoveButton, BorderLayout.EAST);
		innerAddCourseUnitPanel.add(innerRemoveButtonPanel);
		mapOfInnerRemoveButtonToPanel.put(innerRemoveButton, parentPanel);
	}

	public void setInnerRemoveButtonActionListener(JButton innerRemoveButton) {
		innerRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer answer = (Integer) JOptionPane
						.showConfirmDialog(
								MainFrame.this,
								"Are you sure that you want to remove this course unit?",
								"Confirmation", JOptionPane.YES_NO_OPTION);
				if (answer != JOptionPane.YES_OPTION) {
					return;
				}
				JPanel panelToBeRemoved = mapOfInnerRemoveButtonToPanel
						.get((JButton) e.getSource());
				JScrollPane scrollPaneToBeRemoved = mapOfScrollPanes
						.get(panelToBeRemoved);

				containerPanel.remove(scrollPaneToBeRemoved);
				containerPanel.revalidate();
				containerPanel.repaint();
				clearDataStructs(panelToBeRemoved, e, false);
			}
		});
	}

	public void clearDataStructs(JPanel panelToBeRemoved, ActionEvent e,
			boolean clearAll) {
		if (clearAll) {
			mapOfScrollPanes.clear();
			listOfNewCourseUnitPanels.clear();
			numOfNewCourseUnitPanels = 0;
			listOfInnerCourseUnitPanels.clear();
			mapOfCourseUnitCodeFieldToPanel.clear();
			mapOfCourseUnitNameFieldToPanel.clear();
			mapOfCourseUnitTypeCardToPanel.clear();
			mapOfCourseUnitTypeComboBoxToPanel.clear();
			mapOfDurationFieldToPanel.clear();
			mapOfInnerRemoveButtonToPanel.clear();
			mapOfMaxNumOfStudentsFieldToPanel.clear();
			mapOfOrganizationFieldToPanel.clear();
			mapOfPanelToCourseUnitTypeComboBox.clear();
			mapOfSemesterFieldToPanel.clear();
			mapOfSupervisorFieldToPanel.clear();
			mapOfInnerCourseUnitPanelToPanel.clear();
		} else {
			mapOfScrollPanes.remove(panelToBeRemoved);
			listOfNewCourseUnitPanels.remove(panelToBeRemoved);
			numOfNewCourseUnitPanels--;
			listOfInnerCourseUnitPanels.remove(mapOfInnerCourseUnitPanelToPanel
					.get(panelToBeRemoved));
			JPanel innerPanel = mapOfInnerCourseUnitPanelToPanel
					.get(panelToBeRemoved);
			mapOfCourseUnitCodeFieldToPanel.remove(innerPanel);
			mapOfCourseUnitNameFieldToPanel.remove(innerPanel);
			mapOfCourseUnitTypeCardToPanel.remove(innerPanel);
			mapOfCourseUnitTypeComboBoxToPanel
					.remove(mapOfPanelToCourseUnitTypeComboBox.get(innerPanel));
			mapOfDurationFieldToPanel.remove(innerPanel);
			mapOfInnerRemoveButtonToPanel.remove((JButton) e.getSource());
			mapOfMaxNumOfStudentsFieldToPanel.remove(innerPanel);
			mapOfOrganizationFieldToPanel.remove(innerPanel);
			mapOfPanelToCourseUnitTypeComboBox.remove(innerPanel);
			mapOfSemesterFieldToPanel.remove(innerPanel);
			mapOfSupervisorFieldToPanel.remove(innerPanel);
			mapOfInnerCourseUnitPanelToPanel.remove(panelToBeRemoved);
		}
	}

	public void setSubmitButtonListener(ActionListener actionListener) {
		submitButton.addActionListener(actionListener);
	}

	public ArrayList<JPanel> getListOfInnerCourseUnitPanels() {
		return listOfInnerCourseUnitPanels;
	}

	public Map<JPanel, JTextField> getMapOfCourseUnitCodeFieldToPanel() {
		return mapOfCourseUnitCodeFieldToPanel;
	}

	public Map<JPanel, JTextField> getMapOfCourseUnitNameFieldToPanel() {
		return mapOfCourseUnitNameFieldToPanel;
	}

	public Map<JPanel, JTextField> getMapOfMaxNumOfStudentsFieldToPanel() {
		return mapOfMaxNumOfStudentsFieldToPanel;
	}

	public Map<JPanel, JTextField> getMapOfSemesterFieldToPanel() {
		return mapOfSemesterFieldToPanel;
	}

	public Map<JPanel, JTextField> getMapOfDurationFieldToPanel() {
		return mapOfDurationFieldToPanel;
	}

	public Map<JPanel, JTextField> getMapOfSupervisorFieldToPanel() {
		return mapOfSupervisorFieldToPanel;
	}

	public Map<JPanel, JTextField> getMapOfOrganizationFieldToPanel() {
		return mapOfOrganizationFieldToPanel;
	}

	@SuppressWarnings("rawtypes")
	public Map<JPanel, JComboBox> getMapOfPanelToCourseUnitTypeComboBox() {
		return mapOfPanelToCourseUnitTypeComboBox;
	}

	public void updateView(String courseUnitCode, String courseUnitName,
			String courseUnitType, String maxNumOfStudents, String semester,
			String duration, String supervisor, String organizationName) {
		buildAddCourseUnitPanel(courseUnitCode, courseUnitName, courseUnitType,
				maxNumOfStudents, semester, duration, supervisor,
				organizationName);
	}

	public void showSubmitConfirmationMessage() {
		JOptionPane.showMessageDialog(this,
				"[SUCCESS] Course Units have been saved to file!", "DONE!",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void showErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}
}
