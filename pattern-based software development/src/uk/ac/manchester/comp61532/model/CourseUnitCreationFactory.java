package uk.ac.manchester.comp61532.model;

/*
 * This is the Factory class in charge with the task of creating the model (objects).
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
public class CourseUnitCreationFactory {

	private static CourseUnitCreationFactory instance;

	private CourseUnitCreationFactory() {

	}

	public static synchronized CourseUnitCreationFactory getInstance() {
		if (null == instance) {
			instance = new CourseUnitCreationFactory();
		}

		return instance;
	}

	// Construct (course unit) objects based on the input arguments/parameters.
	public CourseUnit createCourseUnit(String courseUnitCode,
			String courseUnitName, String courseUnitType,
			Integer maxNumOfStudents, Integer semester, Integer duration,
			String supervisor, String organizationName) {

		if (CourseUnitType.TAUGHT.getType().equalsIgnoreCase(courseUnitType)) {
			return new TaughtCourseUnit(courseUnitCode, courseUnitName,
					courseUnitType, maxNumOfStudents, semester);
		} else if (CourseUnitType.RESEARCH.getType().equalsIgnoreCase(
				courseUnitType)) {
			return new ResearchCourseUnit(courseUnitCode, courseUnitName,
					courseUnitType, maxNumOfStudents, duration, supervisor);
		} else if (CourseUnitType.INTERNSHIP.getType().equalsIgnoreCase(
				courseUnitType)) {
			return new InternshipCourseUnit(courseUnitCode, courseUnitName,
					courseUnitType, maxNumOfStudents, duration,
					organizationName);
		}

		return null;
	}
}
