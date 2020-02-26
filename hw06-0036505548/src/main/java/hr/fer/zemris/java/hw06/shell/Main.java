package hr.fer.zemris.java.hw06.shell;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		ShellArgumentsParser parser=new ShellArgumentsParser("cat \"C:\\EclipseWorkspaces\\\\javaTecaj\\\"javaZadace\\database.txt\"");
		List<String> arguments=parser.getArgumentsSplitted();
		for(String s:arguments) {
			System.out.println(s);
		}
	}

}
