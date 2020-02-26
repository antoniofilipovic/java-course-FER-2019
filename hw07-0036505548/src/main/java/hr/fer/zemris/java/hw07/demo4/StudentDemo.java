package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * This class is used for basic filtering of studens using only streams
 * 
 * @author Antonio Filipović
 *
 */

public class StudentDemo {
	/**
	 * minimum of number of arguments for student
	 */
	private static final int NUMBER_OF_ARGUMENTS = 7;
	/**
	 * Minimum grade
	 */
	private static final int MIN_GRADE = 1;
	/**
	 * Max grade
	 */
	private static final int MAX_GRADE = 5;

	/**
	 * Next follows arguments for task output
	 */
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	/**
	 * Points needed in first task to sort to
	 */
	private static final int FIRST_TAKS_POINTS = 25;

	/**
	 * This method starts when main program starts
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Exception was thrown while reading from file.");
			System.exit(0);
		}
		List<StudentRecord> records = convert(lines);

		output(ONE);
		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);

		output(TWO);
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5);

		output(THREE);
		List<StudentRecord> odlikasi = odlikasi(records);
		odlikasi.forEach(r -> System.out.printf(r.toString()));

		output(FOUR);
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.forEach(r -> System.out.printf(r.toString()));

		output(FIVE);
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		nepolozeniJMBAGovi.forEach(r -> System.out.println(r.toString()));

		output(SIX);
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		mapaPoOcjenama.forEach((k, v) -> System.out.println(k + "->" + v));

		output(SEVEN);
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		mapaPoOcjenama2.forEach((k, v) -> System.out.println(k + "->" + v));

		output(EIGHT);
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		prolazNeprolaz.forEach((k, v) -> System.out.println(k + "->" + v));
	}

	/**
	 * This method returns number of students that have more than 25 points combined
	 * on midterm, finalterm and laboratory exercise
	 * 
	 * @param records list of student records
	 * @return number of students filtered
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(r -> (r.getFinalTermPoints() + r.getMidTermPoints()
				+ r.getLaboratoryExercisePoints() > FIRST_TAKS_POINTS)).count();
	}

	/**
	 * This method returns number of students whose final grade is 5
	 * 
	 * @param records list of student records
	 * @return number of students filtered
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getFinalGrade() == FIVE).count();
	}

	/**
	 * This method returns list of students whose final grade is five
	 * 
	 * @param records student records
	 * @return list of records filtered
	 */
	private static List<StudentRecord> odlikasi(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getFinalGrade() == FIVE).collect(Collectors.toList());
	}

	/**
	 * This method returns list of students whose final grade is five but sorted by
	 * number of total points
	 * 
	 * @param records student records
	 * @return list of records filtered
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {

		return records.stream().filter(r -> r.getFinalGrade() == FIVE)
				.sorted((r1, r2) -> Double.compare(
						(r2.getMidTermPoints() + r2.getFinalTermPoints() + r2.getLaboratoryExercisePoints()),
						(r1.getMidTermPoints() + r1.getFinalTermPoints() + r1.getLaboratoryExercisePoints())))
				.collect(Collectors.toList());
	}

	/**
	 * This method returns list of students that didnt pass course
	 * 
	 * @param records list of student records
	 * @return List of jmbags from student that didnt pass
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(r1 -> r1.getFinalGrade() == ONE).map(r -> r.getJmbag())
				.sorted((r1, r2) -> r1.compareTo(r2)).collect(Collectors.toList());

	}

	/**
	 * This method groups students by final grade
	 * 
	 * @param records list of student records
	 * @return map of all student where key is their final grade
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * This method groups students by final grade and returns number of students for
	 * each grade
	 * 
	 * @param records list of student records
	 * @return map of all student where key is their final grade and value is number
	 *         of students with that grade
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getFinalGrade, r -> 1, (g1, g2) -> g1 + g2));
	}

	/**
	 * This method sorts students that passed the coures and that didn't
	 * 
	 * @param records of students
	 * @return map of students that passed and those who didn't
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(r -> r.getFinalGrade() != ONE));
	}

	/**
	 * Output for every task
	 * 
	 * @param number of task
	 */
	private static void output(int number) {
		System.out.println("Zadatak " + number);
		System.out.println("=========");
	}

	/**
	 * This method produces records from lines that are read from txt file
	 * 
	 * @param lines List of lines read from file
	 * @return list of Student Records
	 * 
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for (String s : lines) {
			if (s.isEmpty()) {
				continue;
			}
			String[] parts = s.split("\\s+");
			if (parts.length != NUMBER_OF_ARGUMENTS) {
				System.out.printf("Wrong number of arguments for student:" + s);
				System.exit(-1);
			}
			try {
				double midTermPoints = Double.parseDouble(parts[3]);
				double finalTermPoints = Double.parseDouble(parts[4]);
				double laboratoryExercisePoints = Double.parseDouble(parts[5]);
				int grade = Integer.parseInt(parts[6]);

				if (grade < MIN_GRADE || grade > MAX_GRADE) {
					System.out.printf("Wrong grade for student:" + s);
					System.exit(-1);
				}

				StudentRecord r = new StudentRecord(parts[0], parts[1], parts[2], midTermPoints, finalTermPoints,
						laboratoryExercisePoints, grade);
				if (records.contains(r)) {
					System.out.println("Student " + s + " already exists in file.");
					System.exit(-1);
				}
				records.add(r);

			} catch (NullPointerException | NumberFormatException e) {
				System.out.printf("Wrong arguments for student:" + s);
				System.exit(-1);
			}
		}
		return records;
	}
}
