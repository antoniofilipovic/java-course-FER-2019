package hr.fer.zemris.java.hw01;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class FactorialTest{
	@Test
	public void correctFactorial() {
		long n=Factorial.calculateFactorial(3);
		assertEquals(6, n);
	}
	
	@Test
	public void throwsExceptionForString() {
		 try {
			 long n=Factorial.calculateFactorial(21);
			 fail("Nije trebao izračunati");
		 }catch(IllegalArgumentException e) {
			 assertTrue(true);
		 }catch(Exception e) {
			 assertTrue(false);
		 }
	}
	@Test
	public void throwsExceptionForDecimalNum() {
		 try {
			 long n=Factorial.calculateFactorial(-2);
			 fail("Nije trebao izračunati");
		 }catch(IllegalArgumentException e) {
			 assertTrue(true);
		 }catch(Exception e) {
			 assertTrue(false);
		 }
	}
	
	@Test
	public void calculatesOutOfRange() {
		try {
			 long n=Factorial.calculateFactorial(2);
			 fail("Nije trebao izračunati");
		 }catch(IllegalArgumentException e) {
			 assertTrue(true);
		 }catch(Exception e) {
			 assertTrue(false);
		 }
	}

	
}