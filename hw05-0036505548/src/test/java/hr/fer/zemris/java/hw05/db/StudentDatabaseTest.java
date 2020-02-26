package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	public static StudentDatabase getDatabase() {
		StudentDatabase database;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Exception");
		}
		database = new StudentDatabase(lines);
		return database;
	}

	@Test
	public void forJMBAG() {
		StudentDatabase db=getDatabase();
		StudentRecord r=db.forJMBAG("0000000044");
		StudentRecord exp=new StudentRecord("0000000044","Pilat","Ivan",5);
		assertEquals(exp,r);
	}

	@Test
	public void filterAllTrueTest() {
		StudentDatabase db=getDatabase();
		List<StudentRecord> filtered = db.filter(record -> true);
		assertEquals(63, filtered.size());
	}

	@Test
	public void filterAllFalseTest() {
		StudentDatabase db=getDatabase();
		List<StudentRecord> filtered = db.filter(record -> false);
		assertEquals(0, filtered.size());
	}

}
