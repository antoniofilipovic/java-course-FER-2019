package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import static hr.fer.zemris.java.hw03.SmartScriptTester.createOriginalDocumentBody;;

class SmartScriptParserTest {

	@Test
	public void testForProfessorsInput() {
		String doc1 = loader("doc1.txt");
		assertTrue(parserTextTest(doc1));
	}

	@Test
	public void testWhenThereAreManyEscapeCharacters() {
		String doc1 = loader("doc2.txt");
		assertTrue(parserTextTest(doc1));
	}


	@Test
	public void testWithEscapesAndForWithThreeVairables() {
		String doc6 = loader("doc6.txt");
		assertTrue(parserTextTest(doc6));
	}

	@Test
	public void testWithEscapesAndForWithThreeVairables1() {
		String doc6 = loader("doc5.txt");
		assertTrue(parserTextTest(doc6));
	}

	@Test
	public void testWithEscape2() {
		String doc6 = loader("doc4.txt");
		assertTrue(parserTextTest(doc6));
	}

	@Test
	public void testUnclosedForLoop() {
		String doc8 = loader("doc8.txt");
		assertThrows(SmartScriptParserException.class, () -> parserTextTest(doc8));
	}

	@Test
	public void invalideFor() {
		String doc7 = loader("doc7.txt");
		assertThrows(SmartScriptParserException.class, () -> parserTextTest(doc7));
	}
	
	@Test
	public void invalideEnd() {
		String doc7 = loader("doc9.txt");
		assertThrows(SmartScriptParserException.class, () -> parserTextTest(doc7));
	}

	@Test
	public void invalideFor1() {
		String doc7 = loader("doc10.txt");
		assertThrows(SmartScriptParserException.class, () -> parserTextTest(doc7));
	}
	private boolean parserTextTest(String textOriginal) {
		String docBody = textOriginal;
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String notOriginalDocumentBody = createOriginalDocumentBody(document2);
		// now document and document2 should be structurally identical trees

		return originalDocumentBody.equals(notOriginalDocumentBody);
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

}
