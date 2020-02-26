package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class represents implementation of algorithms for coloring. Every
 * algorithm gets through list of all possible neighbours and then collors them
 * if they pass test from {@link acceptable}
 * 
 * @author af
 *
 */
public class SubspaceExploreUtil {
	/**
	 * Bfs algorithm.
	 * 
	 * @param s0         supplier
	 * @param process    process
	 * @param succ       function
	 * @param acceptable acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		while (!toExplore.isEmpty()) {
			S s1 = toExplore.remove(0);
			if (!acceptable.test(s1)) {
				continue;
			}
			List<S> list = succ.apply(s1);
			process.accept(s1);
			toExplore.addAll(list);
		}
	}

	/**
	 * Dfs algorithm. It changes color for pixels that pass test.
	 * 
	 * @param s0         supplier
	 * @param process    process
	 * @param succ       function
	 * @param acceptable acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		while (!toExplore.isEmpty()) {
			S s1 = toExplore.remove(0);
			if (!acceptable.test(s1)) {
				continue;
			}
			List<S> list = succ.apply(s1);
			process.accept(s1);
			toExplore.addAll(0, list);
		}
	}

	/**
	 * Bfs algorithm with visited states. It gets all neighbours from current state
	 * and processes them.
	 * 
	 * @param s0         supplier
	 * @param process    process
	 * @param succ       function
	 * @param acceptable acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toExplore = new LinkedList<>();
		Set<S> visited = new HashSet<>();

		toExplore.add(s0.get());
		visited.add(s0.get());
		while (!toExplore.isEmpty()) {
			S s1 = toExplore.remove(0);
			if (!acceptable.test(s1)) {
				continue;
			}
			List<S> kids = succ.apply(s1);
			kids.removeAll(visited);
			process.accept(s1);
			toExplore.addAll(kids);
			visited.addAll(kids);
		}
	}

}
