
package bgu.spl.net.srv;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private ConcurrentHashMap<Short, Course> courses;
	private ConcurrentHashMap<String, User> users;
	private int order;//tells us the order the course have been initialize to the database

	//to prevent user from creating new Database
	private Database() {
		// TODO: implement
		courses = new ConcurrentHashMap<>();
		users = new ConcurrentHashMap<>();
		order=1;
	}

	public ConcurrentHashMap<Short, Course> getCourses() {
		return courses;
	}

	public ConcurrentHashMap<String, User> getUsers() {
		return users;
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static class singleton {//to make only one appearance of the message bus
		private static Database instance = new Database();
	}

	public static Database getInstance() {
		return singleton.instance;
	}

	/**
	 * loades the courses from the file path specified
	 * into the Database, returns true if successful.
	 */
	public boolean initialize(String coursesFilePath) {
		// TODO: implement
		try {
			List<String> st = Files.readAllLines(Paths.get(coursesFilePath));
			for (String l : st) {
				String[] split = l.split("\\|");
				short courseNum = Short.parseShort(split[0]);
				String courseName = split[1];
				int[] kdamCourse;
				if (split[2].equals(("[]"))) {
					kdamCourse = new int[0];
				} else
					kdamCourse = Stream.of(split[2].substring(1, split[2].length() - 1).split(",")).mapToInt(Integer::parseInt).toArray();
				List<Short> courseKdam = new ArrayList<>();
				for (int i = 0; i < kdamCourse.length; i++) {
					courseKdam.add((short) kdamCourse[i]);
				}
				int numMaxStudent = Integer.parseInt(split[3]);
				Course c = new Course(courseNum, courseName, courseKdam, numMaxStudent,order);
				order++;
				courses.put(courseNum, c);
			}
		} catch (IOException e) { };
		return true;
	}
}

