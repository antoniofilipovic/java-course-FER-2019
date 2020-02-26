package searching.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.*;

/**
 * This class represents demo program
 * 
 * @author af
 *
 */

public class SlagalicaMain {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments.");
			System.exit(-1);
		}
		int[] field = getField(args[0]);
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(field));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});

			SlagalicaViewer.display(rješenje);

		}

	}

	/**
	 * This method returns field of int values
	 * 
	 * @param string from which it returns field of int values
	 * @return int values
	 */
	private static int[] getField(String string) {
		char[] data = string.toCharArray();
		List<Integer> fieldList = new ArrayList<>();
		if (data.length != 9) {
			errorMessage();
		}
		int[] field = new int[9];
		for (int i = 0; i < 9; i++) {
			try {
				field[i] = Integer.parseInt(String.valueOf(data[i]));
				fieldList.add(field[i]);
			} catch (NumberFormatException e) {
				errorMessage();
			}

		}
		List<Integer> lst = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
		if (fieldList.containsAll(lst)) {
			return field;
		}
		errorMessage();
		return null;
	}

	/**
	 * This method represents error message
	 */
	private static void errorMessage() {
		System.out.println("Expected 9 integer numbers from zero to eight included.");
		System.exit(-1);
	}

}
