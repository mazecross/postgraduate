package uk.ac.manchester.comp61532.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InternshipCourseUnit extends CourseUnit implements Serializable {

	private Integer duration;
	private String organizationName;

	public InternshipCourseUnit(String courseUnitCode, String courseUnitName,
			String courseUnitType, Integer maxNumOfStudents, Integer duration,
			String organizationName) {
		super(courseUnitCode, courseUnitName, courseUnitType, maxNumOfStudents);
		this.duration = duration;
		this.organizationName = organizationName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

}
