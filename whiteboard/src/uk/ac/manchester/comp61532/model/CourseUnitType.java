package uk.ac.manchester.comp61532.model;

public enum CourseUnitType {
	TAUGHT("Taught"), RESEARCH("Research"), INTERNSHIP("Internship");

	private String courseUnitType;

	CourseUnitType(String courseUnitType) {
		this.courseUnitType = courseUnitType;
	}

	public String getType() {
		return courseUnitType;
	}
}
