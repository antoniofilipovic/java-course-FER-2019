package hr.fer.zemris.java.hw06.shell.massrename;

import com.sun.net.httpserver.Authenticator.Result;

/**
 * This class represents namebuilder that builds name from {@link Result} group
 * with padding. Padding can be zeros are spaces.
 * 
 * @author af
 *
 */
public class NameBuilderGroupPadding implements NameBuilder {
	/**
	 * Index of group
	 */
	private int index;
	/**
	 * Min widht
	 */
	private int minWidth;
	/**
	 * Padding
	 */
	private char padding;

	/**
	 * Public constructor for namebuilder
	 * 
	 * @param index    of group
	 * @param padding  padding type(char)
	 * @param minWidth minimum widht
	 */
	public NameBuilderGroupPadding(int index, char padding, int minWidth) {
		this.index = index;
		this.minWidth = minWidth;
		this.padding = padding;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		String output = result.group(index);

		if (output.length() < minWidth)
			sb.append(String.valueOf(padding).repeat(minWidth - output.length()));
		sb.append(output);

	}

}
