package hr.fer.zemris.java.hw06.shell.massrename;

import java.util.List;

/**
 * This is composite name builder. It receives list of  namebuilders and builds name
 * in string builder
 * 
 * @author af
 *
 */
public class NameBuilderComposit implements NameBuilder {
	/**
	 * List of namebuilders
	 */
	private List<NameBuilder> nameBuilders;

	/**
	 * Public constructor for composit namebuilder
	 * 
	 * @param nameBuilders
	 */
	public NameBuilderComposit(List<NameBuilder> nameBuilders) {
		this.nameBuilders = nameBuilders;

	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for (NameBuilder nameBuilder : nameBuilders) {
			nameBuilder.execute(result, sb);
		}

	}

}
