package uk.ac.manchester.comp61532.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.ac.manchester.comp61532.controller.CourseUnitCreationController;
import uk.ac.manchester.comp61532.model.CSVFileHandlingStrategy;
import uk.ac.manchester.comp61532.model.CourseUnit;
import uk.ac.manchester.comp61532.model.FileHandlingStrategy;
import uk.ac.manchester.comp61532.model.SerializableFileHandlingStrategy;
import uk.ac.manchester.comp61532.view.MainFrame;

/*
 * This is the main class of the application.
 * 
 * author: Sia Wai Suan | mbaxkws3 | 9378166
 */
public class Whiteboard {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// When the application starts up, it reads data from the latest
		// modified file from the specified directory.
		File latestFile = getLatestFilefromDir();

		List<CourseUnit> model = new ArrayList<CourseUnit>();

		FileHandlingStrategy strategy = null;
		if (null != latestFile) {
			if (latestFile.getName()
					.substring(latestFile.getName().lastIndexOf(".") + 1)
					.equals("ser")) {
				strategy = new SerializableFileHandlingStrategy();
			} else if (latestFile.getName()
					.substring(latestFile.getName().lastIndexOf(".") + 1)
					.equals("csv")) {
				strategy = new CSVFileHandlingStrategy();
			}

			model = strategy.readFromFile();
		}

		// The view and the controller are then constructed and initialized
		// respectively.
		MainFrame view = new MainFrame();
		CourseUnitCreationController controller = new CourseUnitCreationController(
				model, view);
	}

	private static File getLatestFilefromDir() {
		File dir = new File("data/");
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}
}
