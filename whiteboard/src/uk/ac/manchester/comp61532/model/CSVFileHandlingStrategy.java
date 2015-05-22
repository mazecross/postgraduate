package uk.ac.manchester.comp61532.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This is the strategy class in charge of handling comma-separated-values files.
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
public class CSVFileHandlingStrategy implements FileHandlingStrategy {

	private final static String FILE_NAME = "data/data.csv";

	@Override
	public void writeToFile(List<CourseUnit> listOfCourseUnits) {

		File fileName = new File(FILE_NAME);

		if (!fileName.exists()) {
			(new File("data/")).mkdir();
		}

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(FILE_NAME);

			for (CourseUnit courseUnit : listOfCourseUnits) {
				fileWriter.append(courseUnit.getCourseUnitCode());
				fileWriter.append(",");
				fileWriter.append(courseUnit.getCourseUnitName());
				fileWriter.append(",");
				fileWriter.append(courseUnit.getCourseUnitType());
				fileWriter.append(",");
				if (null != courseUnit.getMaxNumOfStudents())
					fileWriter.append(courseUnit.getMaxNumOfStudents()
							.toString());
				fileWriter.append(",");

				if (CourseUnitType.TAUGHT.getType().equalsIgnoreCase(
						courseUnit.getCourseUnitType())) {

					if (null != ((TaughtCourseUnit) courseUnit).getSemester()) {
						fileWriter.append(((TaughtCourseUnit) courseUnit)
								.getSemester().toString());
					}
				} else if (CourseUnitType.RESEARCH.getType().equalsIgnoreCase(
						courseUnit.getCourseUnitType())) {

					if (null != ((ResearchCourseUnit) courseUnit).getDuration()) {
						fileWriter.append(((ResearchCourseUnit) courseUnit)
								.getDuration().toString());
					}

					fileWriter.append(",");

					fileWriter.append(((ResearchCourseUnit) courseUnit)
							.getSupervisor());

				} else if (CourseUnitType.INTERNSHIP.getType()
						.equalsIgnoreCase(courseUnit.getCourseUnitType())) {

					if (null != ((InternshipCourseUnit) courseUnit)
							.getDuration()) {
						fileWriter.append(((InternshipCourseUnit) courseUnit)
								.getDuration().toString());
					}

					fileWriter.append(",");

					fileWriter.append(((InternshipCourseUnit) courseUnit)
							.getOrganizationName());
				}

				fileWriter.append("\n");
			}

		} catch (IOException e) {
			throw new RuntimeException("[ERROR WHEN WRITING TO FILE] ::"
					+ e.getMessage());
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				throw new RuntimeException("[ERROR WHEN WRITING TO FILE] ::"
						+ e.getMessage());
			}
		}
	}

	@Override
	public List<CourseUnit> readFromFile() {

		File fileName = new File(FILE_NAME);

		if (!fileName.exists()) {
			(new File("data/")).mkdir();
			return new ArrayList<CourseUnit>();
		}

		List<CourseUnit> listOfCourseUnits = new ArrayList<CourseUnit>();
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(FILE_NAME));

			String line = "";

			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(",", 6);

				if (tokens.length > 0) {
					CourseUnit courseunit = null;

					String courseUnitCode = tokens[0];
					String courseUnitName = tokens[1];
					String courseUnitType = tokens[2];
					Integer maxNumOfStudents = null;
					if (null != tokens[3] && !"".equals(tokens[3]))
						maxNumOfStudents = Integer.parseInt(tokens[3]);

					if (CourseUnitType.TAUGHT.getType().equalsIgnoreCase(
							courseUnitType)) {
						Integer semester = null;
						if (null != tokens[4] && !"".equals(tokens[4]))
							semester = Integer.parseInt(tokens[4]);

						courseunit = CourseUnitCreationFactory.getInstance()
								.createCourseUnit(courseUnitCode,
										courseUnitName, courseUnitType,
										maxNumOfStudents, semester, null, null,
										null);

					} else if (CourseUnitType.RESEARCH.getType()
							.equalsIgnoreCase(courseUnitType)) {
						Integer duration = null;
						if (null != tokens[4] && !"".equals(tokens[4]))
							duration = Integer.parseInt(tokens[4]);

						String supervisor = tokens[5];

						courseunit = CourseUnitCreationFactory.getInstance()
								.createCourseUnit(courseUnitCode,
										courseUnitName, courseUnitType,
										maxNumOfStudents, null, duration,
										supervisor, null);

					} else if (CourseUnitType.INTERNSHIP.getType()
							.equalsIgnoreCase(courseUnitType)) {
						Integer duration = null;
						if (null != tokens[4] && !"".equals(tokens[4]))
							duration = Integer.parseInt(tokens[4]);

						String organization = tokens[5];

						courseunit = CourseUnitCreationFactory.getInstance()
								.createCourseUnit(courseUnitCode,
										courseUnitName, courseUnitType,
										maxNumOfStudents, null, duration, null,
										organization);
					}

					listOfCourseUnits.add(courseunit);
				}
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException("[ERROR WHEN READING FROM FILE] ::"
					+ e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("[ERROR WHEN READING FROM FILE] ::"
					+ e.getMessage());
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException("[ERROR WHEN READING FROM FILE] ::"
						+ e.getMessage());
			}
		}

		return listOfCourseUnits;
	}
}
