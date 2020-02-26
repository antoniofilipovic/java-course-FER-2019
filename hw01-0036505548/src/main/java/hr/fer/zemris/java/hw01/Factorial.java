package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji za unesenu cijelobrojnu vrijednost računa faktorijalnu
 * vrijednost. Unesena vrijednost treba biti u intervalu [3,20]
 * 
 * @author Antonio Filipovic
 * @version 1.0
 *
 */
public class Factorial {
	public static final int MIN = 3;
	public static final int MAX = 20;

	/**
	 * Metoda od koje kreće izvođenje programa. Unosi se broj i računa fakotrijal
	 * sve dok se ne učita 'kraj'
	 * 
	 * @param args argumenti zadani preko naredbenog retka.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("Unesite broj > ");
			String input = sc.next().trim();
			if (input.equals("kraj")) {
				break;
			}
			try {
				int n = Integer.parseInt(input);
				if (n < MIN || n > MAX) {
					System.out.format("'%s' nije broj u dozvoljenom rasponu.%n", n);
					continue;
				}
				long factorial = calculateFactorial(n);
				System.out.printf("%d! = %d%n", Integer.parseInt(input), factorial);
			} catch (NumberFormatException e) {
				System.out.format("'%s' nije cijeli broj.%n", input);
			}

		}
		sc.close();
		System.out.println("Doviđenja.");

	}

	/**
	 * Metoda koja za dobiveni String {@link s} provjerava može li se pretvoriti u
	 * cijeli broj unutar intervala [3,20] i vraća faktorijal.
	 * 
	 * @param s String za koji se provjerava odgovara li uvjetima
	 * @return vraća izračunatu vrijednost fakotrijala ako je ulazni argument dobar
	 * @throws IllegalArgumentException ako ulazni argument nije prirodni broj iz
	 *                                  intervala
	 *
	 */
	public static long calculateFactorial(int n) {
		long value = 1;
		if (n < 0 || n > 20) {
			System.out.println("Broj je prevelik ili premali!");
			throw new IllegalArgumentException();
		}
		for (int i = 1; i <= n; i++) {
			value *= i;
		}
		return value;
	}
}
