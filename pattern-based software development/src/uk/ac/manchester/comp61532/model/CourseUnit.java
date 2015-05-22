package uk.ac.manchester.comp61532.model;

import java.io.Serializable;

/*
 * This is the abstract/parent class of the (course unit) model.
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
@SuppressWarnings("serial")
public abstract class CourseUnit implements Serializable {

	protected String courseUnitCode;
	protected String courseUnitName;
	protected String courseUnitType;
	protected Integer maxNumOfStudents;

	public CourseUnit(String courseUnitCode, String courseUnitName,
			String courseUnitType, Integer maxNumOfStudents) {
		this.courseUnitCode = courseUnitCode;
		this.courseUnitName = courseUnitName;
		this.courseUnitType = courseUnitType;
		this.maxNumOfStudents = maxNumOfStudents;
	}

	public String getCourseUnitCode() {
		return courseUnitCode;
	}

	public void setCourseUnitCode(String courseUnitCode) {
		this.courseUnitCode = courseUnitCode;
	}

	public String getCourseUnitName() {
		return courseUnitName;
	}

	public void setCourseUnitName(String courseUnitName) {
		this.courseUnitName = courseUnitName;
	}

	public String getCourseUnitType() {
		return courseUnitType;
	}

	public void setCourseUnitType(String courseUnitType) {
		this.courseUnitType = courseUnitType;
	}

	public Integer getMaxNumOfStudents() {
		return maxNumOfStudents;
	}

	public void setMaxNumOfStudents(Integer maxNumOfStudents) {
		this.maxNumOfStudents = maxNumOfStudents;
	}

}
