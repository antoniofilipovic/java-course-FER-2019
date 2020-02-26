package hr.fer.zemris.java.custom.scripting.lexer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This is my class created for testing lots of things
 * @author af
 *
 */
public class ProbaSmartScriptLexer {
	public static void main(String[] args) {
		int i = 0;
		char[] data = "{$ FOR @decfmt @sin+10*^+1.23iPer_20jg-1.35bbb\"1\" \"prciguz\r\n \\\\ \\\" \" $} "
				.toCharArray();
		for (char c : data) {
			System.out.println("index:" + i + ":" + c);
			i++;
		}
		String s="Example { bla } blu \\{$=1$}. Nothing interesting {=here}.\r\n" + 
				"\r\n" + 
				"{$ FOR bla 1 -1.35 $} \\\\ ovo bi trebalo radit \\{\r\n" + 
				" .{$ END $} a ne the end.";
		String s2="Example { bla } blu \\{$=1$}. Nothing interesting {=here}.\r\n" + 
				"\r\n" + 
				"{$ FOR bla 1 -1.35 \\\\ ovo bi trebalo radit \\{\r\n" + 
				" .{$END$} a ne the end.";
		System.out.println(s.equals(s2));
	}

}
