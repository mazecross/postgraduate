package uk.ac.manchester.comp61532.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResearchCourseUnit extends CourseUnit implements Serializable {

	private Integer duration;
	private String supervisor;

	public ResearchCourseUnit(String courseUnitCode, String courseUnitName,
			String courseUnitType, Integer maxNumOfStudents, Integer duration,
			String supervisor) {
		super(courseUnitCode, courseUnitName, courseUnitType, maxNumOfStudents);
		this.duration = duration;
		this.supervisor = supervisor;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

}
