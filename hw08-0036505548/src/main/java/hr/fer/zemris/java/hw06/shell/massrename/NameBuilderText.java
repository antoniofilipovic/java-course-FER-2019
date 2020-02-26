package hr.fer.zemris.java.hw06.shell.massrename;

/**
 * This class represents namebuilder implementation for text.
 * 
 * @author af
 *
 */
public class NameBuilderText implements NameBuilder {
	/**
	 * String text from which name will be built
	 */
	private String text;

	/**
	 * Public constructor for namebuilder text
	 * 
	 * @param text
	 */
	public NameBuilderText(String text) {
		this.text = text;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(text);

	}

}
