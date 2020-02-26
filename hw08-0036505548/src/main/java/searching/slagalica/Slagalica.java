package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * This class represents strategy for finding valid configuration. It implements
 * four interfaces used for giving as list of all neighbours for given
 * configuration(Function) -> it returs list of all possible configurations from
 * current one by moving numbers, predicate-> it tests whether given
 * configuration is equal to {@link finalState} ,supplier-> it returns
 * referenced state
 * 
 * @author af
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {
	/**
	 * This is initial state
	 */
	private KonfiguracijaSlagalice initialState;
	/**
	 * Final state
	 */
	private KonfiguracijaSlagalice finalState = new KonfiguracijaSlagalice(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 });

	/**
	 * Moves in int field
	 */
	private static final int[] MOVE = new int[] { -3, -1, 1, 3 };

	/**
	 * Public constructor for Slagalica. It takes initialState
	 * 
	 * @param initialState
	 */
	public Slagalica(KonfiguracijaSlagalice initialState) {
		this.initialState = initialState;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return initialState;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> newPositions = new ArrayList<>();
		int spacePosition = t.indexOfSpace();
		int[] polje = t.getPolje();
		for (int i = 0; i < MOVE.length; i++) {
			if (spacePosition + MOVE[i] >= 0 && spacePosition + MOVE[i] < polje.length) {
				if ((i == 1 || i == 2) && (spacePosition / 3 != (spacePosition + MOVE[i]) / 3)) {
					continue;
				}
				int[] currentState = Arrays.copyOf(polje, polje.length);
				currentState[spacePosition] = polje[spacePosition + MOVE[i]];
				currentState[spacePosition + MOVE[i]] = 0;
				newPositions.add(new Transition<>(new KonfiguracijaSlagalice(currentState), 1));
			}
		}
		return newPositions;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(t.getPolje(), finalState.getPolje());
	}

}
