package hr.fer.zemris.java.hw06.shell.massrename;

/**
 * This interface represents namebuilder
 * 
 * @author af
 *
 */
public interface NameBuilder {
	/**
	 * This method builds name in stringbuilder received from filterresult
	 * 
	 * @param result filterresult
	 * @param sb     stringbuilder
	 */
	void execute(FilterResult result, StringBuilder sb);

}
