package uk.ac.manchester.comp61532.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TaughtCourseUnit extends CourseUnit implements Serializable {

	private Integer semester;

	public TaughtCourseUnit(String courseUnitCode, String courseUnitName,
			String courseUnitType, Integer maxNumOfStudents, Integer semester) {
		super(courseUnitCode, courseUnitName, courseUnitType, maxNumOfStudents);
		this.semester = semester;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

}
