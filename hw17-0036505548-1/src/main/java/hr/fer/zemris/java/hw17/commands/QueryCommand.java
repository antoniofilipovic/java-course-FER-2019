package hr.fer.zemris.java.hw17.commands;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw17.model.Document;
import hr.fer.zemris.java.hw17.model.Vector;
import hr.fer.zemris.java.hw17.shell.Command;
import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellLineParser;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
/**
 * This class represents query command
 * @author af
 *
 */
public class QueryCommand implements Command {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> words=ShellLineParser.parseLine(arguments);
		StringBuilder sb=new StringBuilder();
		sb.append("Query is:");
		for(int i=0;i<words.size();i++) {
			if(i==0) {
				sb.append("[");
			}sb.append(words.get(i)+",");
			if(i+1==words.size()) {
				sb.setLength(sb.length()-1);
				sb.append("]");
			}
		}
		env.writeln(sb.toString());
		env.writeln("Najboljih 10 rezultata:");
		//creating tfVector for query
		Vector tf=env.createVectorFromVocabulary();
		Map<String,Integer> tfMap=tf.getValues();
		Map<String,Integer> vocabulary=env.getVocabulary();
		for(String word:words) {
			if(!vocabulary.containsKey(word)) {
				continue;
			}
			tfMap.merge(word, 1, Integer::sum);
		}
		//creating tfidf vector for query
		
		int numberOfDocuments=env.getNumberOfDocuments();
		Vector idfVector=env.getIdfVector();
		Map<String,Integer> idfMap=idfVector.getValues();
		Map<String,Double> tfidfMap=new HashMap<>();
		
		idfMap.forEach((k,v)->tfidfMap.put(k, tfMap.get(k)*Math.log(numberOfDocuments*1.0/v)));
		
		//calculating similarity
		
		List<Document> documents=env.getDocuments();
		
		Map<Double,Document> results=new TreeMap<>(Collections.reverseOrder());
		
		for(Document d:documents) {
			Map<String,Double> tfidfDoc=d.getTfidfMap();
			
			double sim=Vector.getVectorProduct(tfidfMap, tfidfDoc);
			double a=Math.sqrt(Vector.getVectorProduct(tfidfMap, tfidfMap));
			double b=Math.sqrt(Vector.getVectorProduct(tfidfDoc, tfidfDoc));
			sim=sim/(a*b);
			if(sim>0) {
				results.put(sim, d);
			}
			
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
		return "query";
	}

}
