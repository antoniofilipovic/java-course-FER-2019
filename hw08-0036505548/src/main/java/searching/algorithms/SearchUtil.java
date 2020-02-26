package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class represents implementations of searching algorithms
 * 
 * @author af
 *
 */
public class SearchUtil {
	/**
	 * This method represents implementation of bfs algorithm
	 * 
	 * @param s0   supplier
	 * @param succ function
	 * @param goal goal
	 * @return solution
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toExplore = new LinkedList<>();
		toExplore.add(new Node<S>(null, s0.get(), 0));
		while (toExplore.size() > 0) {
			Node<S> ni = toExplore.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> list = succ.apply(ni.getState());
			for (Transition<S> t : list) {
				toExplore.add(new Node<S>(ni, t.getState(), ni.getCost() + t.getCost()));
			}

		}
		return null;

	}

	/**
	 * This method represents implementation bfs algorithm with visited states
	 * 
	 * @param s0   supplier
	 * @param succ function
	 * @param goal goal
	 * @return solution
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toExplore = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		toExplore.add(new Node<S>(null, s0.get(), 0));
		visited.add(s0.get());
		while (toExplore.size() > 0) {
			Node<S> ni = toExplore.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> list = succ.apply(ni.getState());
			for (Transition<S> t : list) {
				if (!visited.contains(t.getState())) {
					toExplore.add(new Node<S>(ni, t.getState(), ni.getCost() + t.getCost()));
					visited.add(t.getState());
				}

			}

		}
		return null;

	}

}
