package uk.ac.manchester.comp61532.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * This is the strategy class in charge of handling serializable files.
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
public class SerializableFileHandlingStrategy implements FileHandlingStrategy {

	private final static String FILE_NAME = "data/data.ser";

	@Override
	public void writeToFile(List<CourseUnit> listOfCourseUnits) {

		File fileName = new File(FILE_NAME);

		if (!fileName.exists()) {
			(new File("data/")).mkdir();
		}

		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;
		try {
			file = new FileOutputStream(FILE_NAME);

			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(listOfCourseUnits);

		} catch (Exception e) {
			throw new RuntimeException("[ERROR WHEN WRITING TO FILE] ::"
					+ e.getMessage());
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				throw new RuntimeException("[ERROR WHEN WRITING TO FILE] ::"
						+ e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseUnit> readFromFile() {

		File fileName = new File(FILE_NAME);

		if (!fileName.exists()) {
			(new File("data/")).mkdir();
			return new ArrayList<CourseUnit>();
		}

		InputStream inputStream;
		InputStream buffer;
		ObjectInput input = null;
		List<CourseUnit> listOfCourseUnits;

		try {
			inputStream = new FileInputStream(fileName);
			buffer = new BufferedInputStream(inputStream);
			input = new ObjectInputStream(buffer);

			listOfCourseUnits = (List<CourseUnit>) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("[ERROR WHEN READING FROM FILE] ::"
					+ e.getMessage());
		} finally {
			try {
				input.close();
			} catch (Exception e) {
				throw new RuntimeException("ERROR WHEN READING FROM FILE] ::"
						+ e.getMessage());
			}
		}
		return listOfCourseUnits;
	}
}
