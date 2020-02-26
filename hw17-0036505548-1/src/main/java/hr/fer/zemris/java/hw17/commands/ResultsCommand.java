package hr.fer.zemris.java.hw17.commands;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw17.model.Document;
import hr.fer.zemris.java.hw17.shell.Command;
import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
/**
 * This class represents results command
 * @author af
 *
 */
public class ResultsCommand implements Command{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<Double,Document> results=env.getResults();
		
		if(results==null) {
			env.writeln("Query command must be called first.");
			return ShellStatus.CONTINUE;
		}
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		Set<Entry<Double, Document>> resultsSet=results.entrySet();
		int i=0;
		for(Entry<Double,Document> v1:resultsSet) {
			if(i>=10) {
				break;
			}
			env.writeln("["+i+"]"+"("+df.format(v1.getKey())+") "+v1.getValue().getFilePath());
			i++;
		}
		
		env.setResults(results);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "results";
	}

}
