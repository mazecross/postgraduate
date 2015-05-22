package uk.ac.manchester.comp61532.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.manchester.comp61532.model.CSVFileHandlingStrategy;
import uk.ac.manchester.comp61532.model.CourseUnit;
import uk.ac.manchester.comp61532.model.CourseUnitCreationFactory;
import uk.ac.manchester.comp61532.model.CourseUnitType;
import uk.ac.manchester.comp61532.model.FileHandlingStrategy;
import uk.ac.manchester.comp61532.model.InternshipCourseUnit;
import uk.ac.manchester.comp61532.model.ResearchCourseUnit;
import uk.ac.manchester.comp61532.model.SerializableFileHandlingStrategy;
import uk.ac.manchester.comp61532.model.TaughtCourseUnit;
import uk.ac.manchester.comp61532.view.MainFrame;

/*
 * This is the Controller class of the application.
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
public class CourseUnitCreationController implements ActionListener {

	private List<CourseUnit> model;
	private MainFrame view;

	// Constructor -- called by Whiteboard.java/main class.
	public CourseUnitCreationController(List<CourseUnit> model, MainFrame view) {
		this.model = model;
		this.view = view;

		// add action listener for the MainFrame's submit button.
		this.view.setSubmitButtonListener(this);

		// Populate the view with data accorddingly.
		if (!model.isEmpty()) {

			for (CourseUnit courseUnit : model) {

				String courseUnitCode = courseUnit.getCourseUnitCode();
				String courseUnitName = courseUnit.getCourseUnitName();
				String courseUnitType = courseUnit.getCourseUnitType();
				String maxNumOfStudents = null;
				if (null != courseUnit.getMaxNumOfStudents()) {
					maxNumOfStudents = courseUnit.getMaxNumOfStudents()
							.toString();
				}

				if (CourseUnitType.TAUGHT.getType().equalsIgnoreCase(
						courseUnitType)) {

					String semester = null;
					if (null != ((TaughtCourseUnit) courseUnit).getSemester()) {
						semester = ((TaughtCourseUnit) courseUnit)
								.getSemester().toString();
					}

					view.updateView(courseUnitCode, courseUnitName,
							courseUnitType, maxNumOfStudents, semester, null,
							null, null);
				} else if (CourseUnitType.RESEARCH.getType().equalsIgnoreCase(
						courseUnitType)) {

					String duration = null;
					if (null != ((ResearchCourseUnit) courseUnit).getDuration()) {
						duration = ((ResearchCourseUnit) courseUnit)
								.getDuration().toString();
					}

					String supervisor = ((ResearchCourseUnit) courseUnit)
							.getSupervisor();

					view.updateView(courseUnitCode, courseUnitName,
							courseUnitType, maxNumOfStudents, null, duration,
							supervisor, null);

				} else if (CourseUnitType.INTERNSHIP.getType()
						.equalsIgnoreCase(courseUnitType)) {

					String duration = null;
					if (null != ((InternshipCourseUnit) courseUnit)
							.getDuration()) {
						duration = ((InternshipCourseUnit) courseUnit)
								.getDuration().toString();
					}

					String organization = ((InternshipCourseUnit) courseUnit)
							.getOrganizationName();

					view.updateView(courseUnitCode, courseUnitName,
							courseUnitType, maxNumOfStudents, null, duration,
							null, organization);
				}
			}

		}
	}

	// Submit button's action listener.
	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent e) {

		// Retrieve values from the view's fields.
		List<JPanel> listOfCourseUnitPanels = view
				.getListOfInnerCourseUnitPanels();

		Map<JPanel, JTextField> mapOfCourseUnitCodeFieldToPanel = view
				.getMapOfCourseUnitCodeFieldToPanel();

		Map<JPanel, JTextField> mapOfCourseUnitNameFieldToPanel = view
				.getMapOfCourseUnitNameFieldToPanel();

		Map<JPanel, JTextField> mapOfMaxNumOfStudentsFieldToPanel = view
				.getMapOfMaxNumOfStudentsFieldToPanel();

		Map<JPanel, JComboBox> mapOfPanelToCourseUnitTypeComboBox = view
				.getMapOfPanelToCourseUnitTypeComboBox();

		Map<JPanel, JTextField> mapOfSemesterFieldToPanel = view
				.getMapOfSemesterFieldToPanel();

		Map<JPanel, JTextField> mapOfDurationFieldToPanel = view
				.getMapOfDurationFieldToPanel();

		Map<JPanel, JTextField> mapOfSupervisorFieldToPanel = view
				.getMapOfSupervisorFieldToPanel();

		Map<JPanel, JTextField> mapOfOrganizationFieldToPanel = view
				.getMapOfOrganizationFieldToPanel();

		model.clear();

		try {

			// Create/construct model objects to store the value of the fields.
			for (JPanel courseUnitPanel : listOfCourseUnitPanels) {
				String courseUnitCode = ((JTextField) mapOfCourseUnitCodeFieldToPanel
						.get(courseUnitPanel)).getText();

				String courseUnitName = ((JTextField) mapOfCourseUnitNameFieldToPanel
						.get(courseUnitPanel)).getText();

				if (courseUnitCode.isEmpty() || courseUnitName.isEmpty()) {
					throw new NullPointerException();
				}

				Integer maxNumOfStudents = null;
				if (!((JTextField) mapOfMaxNumOfStudentsFieldToPanel
						.get(courseUnitPanel)).getText().isEmpty()) {
					maxNumOfStudents = Integer
							.parseInt(((JTextField) mapOfMaxNumOfStudentsFieldToPanel
									.get(courseUnitPanel)).getText());

					if (maxNumOfStudents <= 0) {
						throw new RuntimeException(
								"[ERROR] Maximum No. of Students must be more than 0!");
					}
				}

				String courseUnitType = (String) ((JComboBox) mapOfPanelToCourseUnitTypeComboBox
						.get(courseUnitPanel)).getSelectedItem();

				if (CourseUnitType.TAUGHT.getType().equalsIgnoreCase(
						courseUnitType)) {

					Integer semester = null;
					if (!((JTextField) mapOfSemesterFieldToPanel
							.get(courseUnitPanel)).getText().isEmpty()) {
						semester = Integer
								.parseInt(((JTextField) mapOfSemesterFieldToPanel
										.get(courseUnitPanel)).getText());

						if (semester <= 0) {
							throw new RuntimeException(
									"[ERROR] Semester must be more than 0!");
						}
					}

					model.add(CourseUnitCreationFactory.getInstance()
							.createCourseUnit(courseUnitCode, courseUnitName,
									courseUnitType, maxNumOfStudents, semester,
									null, null, null));

				} else if (CourseUnitType.RESEARCH.getType().equalsIgnoreCase(
						courseUnitType)) {

					Integer duration = null;
					if (!((JTextField) mapOfDurationFieldToPanel
							.get(courseUnitPanel)).getText().isEmpty()) {
						duration = Integer
								.parseInt(((JTextField) mapOfDurationFieldToPanel
										.get(courseUnitPanel)).getText());

						if (duration <= 0) {
							throw new RuntimeException(
									"[ERROR] Duration must be more than 0!");
						}
					}

					String supervisor = ((JTextField) mapOfSupervisorFieldToPanel
							.get(courseUnitPanel)).getText();

					model.add(CourseUnitCreationFactory.getInstance()
							.createCourseUnit(courseUnitCode, courseUnitName,
									courseUnitType, maxNumOfStudents, null,
									duration, supervisor, null));

				} else if (CourseUnitType.INTERNSHIP.getType()
						.equalsIgnoreCase(courseUnitType)) {

					Integer duration = null;
					if (!((JTextField) mapOfDurationFieldToPanel
							.get(courseUnitPanel)).getText().isEmpty()) {
						duration = Integer
								.parseInt(((JTextField) mapOfDurationFieldToPanel
										.get(courseUnitPanel)).getText());

						if (duration <= 0) {
							throw new RuntimeException(
									"[ERROR] Duration must be more than 0!");
						}
					}

					String organization = ((JTextField) mapOfOrganizationFieldToPanel
							.get(courseUnitPanel)).getText();

					// Add the newly constructed object to a list.
					model.add(CourseUnitCreationFactory.getInstance()
							.createCourseUnit(courseUnitCode, courseUnitName,
									courseUnitType, maxNumOfStudents, null,
									duration, null, organization));

				}
			}

			// Write the newly constructed object(s) to the file store.
			FileHandlingStrategy strategy = null;
			if (view.isSerializable())
				strategy = new SerializableFileHandlingStrategy();
			else if (view.isCSV())
				strategy = new CSVFileHandlingStrategy();

			strategy.writeToFile(model);

		} catch (NumberFormatException nfe) {
			view.showErrorMessage("[ERROR] Semester/Duration/Maximum No. of Students must be of number type!");
			return;
		} catch (NullPointerException npe) {
			view.showErrorMessage("[ERROR] Course Unit code/name cannot be empty!");
			return;
		} catch (RuntimeException rfe) {
			view.showErrorMessage(rfe.getMessage());
			return;
		}

		view.showSubmitConfirmationMessage();
	}
}
