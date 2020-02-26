package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class PrimListModelTest {
	public PrimDemo iterator(){
		return new PrimDemo();
		
	}
	@Test
	public void getSizeTest() {
		PrimListModel model = new PrimListModel(iterator());
		assertEquals(1, model.getSize());
		model.next();
		assertEquals(2, model.getSize());

		model.next();
		assertEquals(3, model.getSize());
	}

	@Test
	public void getElementTest() {
		PrimListModel model = new PrimListModel(iterator());
		assertEquals(1,  model.getElementAt(0));
		model.next();
		assertEquals(2, model.getElementAt(1));
		model.next();
		assertEquals(3, model.getElementAt(2));

	}

	@Test
	public void next() {
		PrimListModel model = new PrimListModel(iterator());
		model.next();
		model.next();
		model.next();
		assertEquals(5,  model.getElementAt(3));
	}

}
