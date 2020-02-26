package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

/**
 * This class is used for interaction with user. It creates database from text
 * input, and offers user to get data that he wants using querys
 * 
 * @author Antonio Filipovic
 *
 */
public class StudentDB {
	/**
	 * Constant for zero
	 */
	private static final int ZERO = 0;

	/**
	 * This method starts when main program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StudentDatabase db;
		List<String> lines = null;
		System.out.printf("> ");
		try {
			lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Exception was thrown while reading from file.");
			System.exit(0);
		}
		db = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		while (true) {
			String query = sc.nextLine();
			if (query.trim().equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			if (!goodQueryInput(query)) {
				System.out.println("Query input must start with \"query\" keyword");

			} else {
				String queryParserString = query.replaceFirst("query", "");
				if (queryParserString.isBlank()) {
					System.out.printf("Invalid query.%n>");
					continue;
				}
				filterAndOutputElements(queryParserString, db);

			}

			System.out.printf("%n> ");
		}
		sc.close();

	}

	/**
	 * This method filters and outputs all elements.
	 * 
	 * @param queryParserString
	 * @param db
	 */
	private static void filterAndOutputElements(String queryParserString, StudentDatabase db) {
		try {
			QueryParser qp = new QueryParser(queryParserString);
			List<StudentRecord> filteredRecords = new ArrayList<>();
			if (qp.isDirectQuery()) {
				System.out.println("Using index for query retrieval.");
				StudentRecord r = db.forJMBAG(qp.getQueriedJMBAG());
				if (r != null) {
					filteredRecords.add(r);
				}

			} else {
				filteredRecords = filterStudents(qp.getQuery(), db);
			}

			formatOutput(filteredRecords);

		} catch (QueryParserException e) {
			System.out.println("Wrong query input.");
		}
	}

	/**
	 * This method returns true if query input starts with "query"
	 * 
	 * @param s string that is tested
	 * @return
	 */
	private static boolean goodQueryInput(String s) {
		s = s.trim();
		String[] parts = s.split("\\s+");
		if (parts[0].trim().equals("query")) {
			return true;
		}
		return false;
	}

	/**
	 * This method filters students using QueryFilter with conditions from parsers
	 * 
	 * @param conditions from parser
	 * @param db         database
	 * @return List of filtered students
	 */
	private static List<StudentRecord> filterStudents(List<ConditionalExpression> conditions, StudentDatabase db) {
		List<StudentRecord> filteredRecords;
		QueryFilter qFilter = new QueryFilter(conditions);
		filteredRecords = db.filter(qFilter);
		return filteredRecords;

	}
	//novo elegantnije rješenje lista.forEach(t -> System.out.println(formatIspisa(t, maxJmbag, maxFirstName, maxLastName))); popravi kad stigneš

	/**
	 * This method is used to create valid output
	 * 
	 * @param records that will be printed
	 */
	private static void formatOutput(List<StudentRecord> records) {
		if (records.size() == ZERO) {
			System.out.println("Records selected: 0");
			return;
		}
		int firstNameSize = 0, lastNameSize = 0, jmbagSize = 0;
		OptionalInt maxFirstNameSize = records.stream().mapToInt(r -> r.getFirstName().length()).max();
		if (maxFirstNameSize.isPresent()) {
			firstNameSize = maxFirstNameSize.getAsInt();
		}
		OptionalInt maxLastNameSize = records.stream().mapToInt(r -> r.getLastName().length()).max();

		if (maxLastNameSize.isPresent()) {
			lastNameSize = maxLastNameSize.getAsInt();
		}

		OptionalInt maxJmbagSize = records.stream().mapToInt(r -> r.getJmbag().length()).max();
		if (maxJmbagSize.isPresent()) {
			jmbagSize = maxJmbagSize.getAsInt();
		}

		int indFirst = 0;
		int indSecond = jmbagSize + 3;
		int indThird = indSecond + lastNameSize + 3;
		int indFourth = indThird + firstNameSize + 3;
		int indLast = indFourth + 4;
		int maxLength = indLast + 1;

		StringBuilder sb = new StringBuilder();
		helpMethodForOutput2(indFirst, indSecond, indThird, indFourth, indLast, maxLength, sb);
		System.out.println(sb.toString());

		for (StudentRecord r : records) {
			int currentIndex = 0;
			sb = new StringBuilder();

			currentIndex = helpMethodForOutput(sb, currentIndex, indSecond, r.getJmbag());

			currentIndex = helpMethodForOutput(sb, currentIndex, indThird, r.getLastName());

			currentIndex = helpMethodForOutput(sb, currentIndex, indFourth, r.getFirstName());

			currentIndex = helpMethodForOutput(sb, currentIndex, indFourth, String.valueOf(r.getFinalGrade()));

			sb.append(" |");
			System.out.println(sb.toString());
		}

		sb = new StringBuilder();
		helpMethodForOutput2(indFirst, indSecond, indThird, indFourth, indLast, maxLength, sb);

		System.out.println(sb.toString());

		System.out.println("Records selected: " + records.size());
		;

	}

	/**
	 * This method is used to create output for values from student record
	 * 
	 * @param sb           stringbuilder
	 * @param currentIndex current index
	 * @param endIndex     end index
	 * @param value        value
	 * @return current index
	 */
	private static int helpMethodForOutput(StringBuilder sb, int currentIndex, int endIndex, String value) {
		sb.append("|").append(" ").append(value);
		currentIndex += 2 + value.length();
		for (int i = currentIndex; i < endIndex; i++) {
			sb.append(" ");
			currentIndex++;
		}
		return currentIndex;
	}

	/**
	 * This method is used to create output for line at beggining and at the end
	 * 
	 * @param indFirst  first index for plus sign
	 * @param indSecond second index for plus sign
	 * @param indThird  third index for plus sign
	 * @param indFourth fourth index for plus sign
	 * @param indLast   last index for plus sign
	 * @param maxLength length of output
	 * @param sb        stringbuilder
	 */
	private static void helpMethodForOutput2(int indFirst, int indSecond, int indThird, int indFourth, int indLast,
			int maxLength, StringBuilder sb) {
		for (int i = 0; i < maxLength; i++) {
			if (i == indFirst || i == indSecond || i == indThird || i == indFourth || i == indLast) {
				sb.append("+");
			} else {
				sb.append("=");
			}
		}

	}

}
