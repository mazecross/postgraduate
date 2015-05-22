package uk.ac.manchester.comp61532.model;

import java.util.List;

public interface FileHandlingStrategy {

	public void writeToFile(List<CourseUnit> listOfCourseUnits);

	public List<CourseUnit> readFromFile();
}
