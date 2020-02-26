package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	void testVector2D() {
		Vector2D vector=new Vector2D(5, 4);
		assertEquals(5,vector.getX());
		assertEquals(4,vector.getY());
		
	}

	@Test
	void testGetX() {
		Vector2D vector=new Vector2D(-1.5, 4);
		assertEquals(-1.5,vector.getX());
	}

	@Test
	void testGetY() {
		Vector2D vector=new Vector2D(5, -4.3);
		assertEquals(-4.3,vector.getY());
	}

	@Test
	void testTranslated() {
		Vector2D vector =new Vector2D(3, 1.5);
		assertEquals(vector.translated(new Vector2D(4,5)),new Vector2D(7,6.5));
	}
	@Test
	void testTranslatedNull() {
		Vector2D vector =new Vector2D(3, 1.5);
		assertThrows(NullPointerException.class, () ->vector.translated(null));
	}

	@Test
	void testTranslate() {
		Vector2D vector =new Vector2D(3, 1.5);
		vector.translate(new Vector2D(4,5));
		assertEquals(vector,new Vector2D(7,6.5));
	}
	
	@Test
	void testTranslateNull() {
		Vector2D vector =new Vector2D(3, 1.5);
		assertThrows(NullPointerException.class, () ->vector.translate(null));
	}

	@Test
	void testRotated() {
		Vector2D vector =new Vector2D(1, 1);
		assertEquals(vector.rotated(Math.PI),new Vector2D(-1,-1));
	}

	@Test
	void testRotate() {
		Vector2D vector =new Vector2D(1, 1);
		vector.rotate(Math.PI);
		assertEquals(vector,new Vector2D(-1,-1));
	}

	@Test
	void testScale() {
		Vector2D vector =new Vector2D(1, 1);
		vector.scale(2);
		assertEquals(vector,new Vector2D(2,2));
	}

	@Test
	void testScaled() {
		Vector2D vector =new Vector2D(1, 1);
		assertEquals(vector.scaled(2),new Vector2D(2,2));
	}

	@Test
	void testCopy() {
		Vector2D vector =new Vector2D(1, 1);
		Vector2D vector1=vector.copy();
		assertEquals(vector,vector1);
	}

}
