package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHextobyte() {
		byte[] array = Util.hextobyte("01aE22");
		assertArrayEquals(new byte[] { 1, -82, 34 }, array);

	}

	@Test
	void testHextobyteToLong() {

		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE220"));

	}

	@Test
	void testHextobyteIllegalArgument() {

		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01ag22"));

	}

	@Test
	void testBytetohex() {
		String text = Util.bytetohex(new byte[] { 1, -82, 34 });
		assertEquals("01ae22", text);
	}

}
