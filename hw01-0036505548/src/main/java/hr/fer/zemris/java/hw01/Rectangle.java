package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Razred koji za visinu i širinu pravokutnika vraća površinu i opseg pravokutnika.
 * Način uporabe je unos točno dva argumenta ili unos preko naredbene linije jedan po jedan.
 * @author Antonio Filipovic
 * @version 1.0
 *
 */
public class Rectangle {
	/**
	 * Metoda od koje kreće izvođenje programa.
	 * @param args argumenti zadani preko naredbenog retka
	 * 
	 *
	 */
	public static void main(String[] args) {
		double width=0,height=0;
		Scanner sc=new Scanner(System.in);
		int argsNumber=args.length;
		if(argsNumber==0) {
			height=validNumber("Unesite širinu > ",sc);
			width=validNumber("Unesite visinu > ",sc);
			output(height,width);
		}else {
			if(argsNumber!=2) {
				System.out.printf("Broj argumenata koji ste unijeli (%d) nije dobar.",argsNumber);
			}else {
				try {
					width=Double.parseDouble(args[0]);
					height=Double.parseDouble(args[1]);
					if(height<=0 || width<=0 ) {
						System.out.println("Oba broja moraju biti pozitivni.");
					}else {
						output(width,height);
					}
					
				}catch(NumberFormatException  IllegalArgumentException ) {
					System.out.println("Niste unijeli dobre argumente.");
				}
			}
		}sc.close();
	}
	/**
	 * Metoda koja ispisuje zadani String  {@link s} sve dok ne dobije String 
	 * za koji je moguće vratiti  decimalni broj. Broj treba biti pozitivan.
	 * 
	 * @param s poruka koja se ispisuje na ekran
	 * @return vraća decimalni pozitivni broj
	 *
	 */
	public static double validNumber(String s,Scanner sc) {
		double number=0.0;
		while(true) {
			boolean error=false;
			System.out.printf(s);
			String input=sc.next();
			try {
				number=Double.parseDouble(input);
			}catch(NumberFormatException e) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n",input);
				error=true;
			}
			if(!error) {
				if(checkNumber(number)) {
					break;
				}
			}
			
		}
		return number;
		
	}
	/**
	 * Metoda koja ispisuje na ekran površinu i opseg pravokutnika za zadanu visinu i 
	 * širinu
	 * @param width širina pravokutnika 
	 * @param height visina pravokutnika
	 */
	
	public static void output(double width,double height) {
		System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.", 
				Double.toString(width),
				Double.toString(height),
				Double.toString(width*height),
				Double.toString(2*(width+height)));
	}
	/**
	 * Metoda koja provjerava je li dobiveni broj pozitivan. Ispisuje poruku u suprotnom.
	 * @param number broj za koji se provjerava je li pozitivan
	 * @return vraća istinu ako je broj pozitivan
	 */
	public static boolean checkNumber(double number) {
		if(number>0) {
			return true;
		}else if(number==0) {
			System.out.println("Unijeli ste nulu. Broj treba biti pozitivan.");
			return false;
		}else {
			System.out.println("Unijeli ste negativnu vrijednost.");
			return false;
		}
	}

}
