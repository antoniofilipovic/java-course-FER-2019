package hr.fer.zemris.java.hw06.shell.massrename;

import com.sun.net.httpserver.Authenticator.Result;

/**
 * This class represents implementation of  namebuilder that builds name depending on group
 * returned from {@link Result}
 * 
 * @author af
 *
 */
public class NameBuilderGroup implements NameBuilder {
	/**
	 * Index of group
	 */
	private int index;
	/**
	 * Public constructor
	 * @param index of group that should be returned
	 */
	public NameBuilderGroup(int index) {
		this.index = index;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(result.group(index));

	}

}
