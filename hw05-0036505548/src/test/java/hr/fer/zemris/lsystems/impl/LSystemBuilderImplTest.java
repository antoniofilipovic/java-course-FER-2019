package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

class LSystemBuilderImplTest {

	@Test
	void testBuild() {
		LSystem system= new LSystemBuilderImpl()
				.configureFromText(new String[] {"production F F+F--F+F","axiom F"})
				.build();
		String generatedString0=system.generate(0);
		String generatedString1=system.generate(1);
		String generatedString2=system.generate(2);
		assertEquals("F", generatedString0);
		assertEquals("F+F--F+F", generatedString1);
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", generatedString2);
	}

}
