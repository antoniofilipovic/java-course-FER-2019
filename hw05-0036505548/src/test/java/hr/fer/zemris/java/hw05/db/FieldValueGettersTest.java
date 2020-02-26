package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	
	private StudentRecord getRecord() {
		return new StudentRecord("0036505549", "Pavlović", "Mate", 5);
	}
  

    @Test
    public void firstNameTest() {
    	StudentRecord record =getRecord();
        assertEquals("Mate", FieldValueGetters.FIRST_NAME.get(record));
    }

    @Test
    public void lastNameTest() {
    	StudentRecord record =getRecord();
        assertEquals("Pavlović", FieldValueGetters.LAST_NAME.get(record));
    }

    @Test
    public void jmbagTest() {
    	StudentRecord record =getRecord();
        assertEquals("0036505549", FieldValueGetters.JMBAG.get(record));
    }
}
