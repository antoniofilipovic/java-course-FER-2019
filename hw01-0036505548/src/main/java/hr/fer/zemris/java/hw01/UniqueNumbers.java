package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Razred koji unosi vrijednosti u stablo tako da je lijevo manja, desno veća 
 * osim ako ta vrijednost već ne postoji.
 * Pokreće se bez argumenata.
 * Sastoji se od strukture TreeNode koja ima lijevo i desno podstablo te vrijednost čvora.
 * 
 * 
 * @author Antonio Filipovic
 * @version 1.0
 *
 */

public class UniqueNumbers {
	static class TreeNode {
		TreeNode left=null;
		TreeNode right=null;
		int value=0;
	}
	/**
	 * Metoda od koje kreće izvođenje programa.
	 * Unose se brojevi u stablo osim ako već ne postoje.
	 * @param args argumenti za unos preko naredbene linije
	 */
	
	public static void main(String args[]) {
		TreeNode glava=null;
		Scanner sc=new Scanner(System.in);
		while (true) {
			int value=0;
			System.out.printf("Unesite broj > ");
			String s=sc.next().trim();
			if(s.equals("kraj")) {
				break;
			}
			try {
				value=Integer.parseInt(s);
				if(containsValue(glava,value)) {
					System.out.println("Broj već postoji. Preskačem.");
					continue;
				}
				glava=addNode(glava,value);
				System.out.println("Dodano.");
			}catch(NumberFormatException e ) {
				System.out.printf("'%s' nije cijeli broj.%n", s);
			}
			
		}
		sc.close();
		//System.out.println(treeSize(glava));
		System.out.printf("Ispis od najmanjeg: ");
		outputIncrease(glava);
		System.out.println();
		System.out.printf("Ispis od najveceg: ");
		outputDecrease(glava);

	}
	/**
	 * Metoda koja ispituje postoji li već ta vrijednost u čvoru.
	 * @param glava glavni čvor stabla, vrh stabla
	 * @param value vrijednost za koju se provjerava postoji li u stablu
	 * @return vraća <code>true</code> ako postoji, <code>false</code> u suprotnom
	 */

	public static boolean containsValue(TreeNode glava, int value) {
		boolean contains=false;
		if(glava==null) {
			return false;
		}if(glava.value==value) {
			return true;
		}if(glava.value>value) {
			contains=containsValue(glava.left,value);
		}
		if(glava.value<value) {
			contains=containsValue(glava.right,value);
		}
		return contains;
	}
	/**
	 * Metoda koja dodaje čvor u stablo tako da je lijevo uvijek manja, a desno
	 * uvijek veća vrijednost.
	 * @param glava vrh stabla u kojeg se unosi nova vrijednost
	 * @param value vrijednost koja se unosi u stablo
	 * @return vraća <code>glava</code> nakon što se unese
	 */
	public static TreeNode addNode(TreeNode glava, int value) {
		if(glava==null) {
			glava=new TreeNode();
			glava.value=value;
			return glava;
		}if(glava.value>value) {
			glava.left=addNode(glava.left,value);
		}else if(glava.value<value) {
			glava.right=addNode(glava.right,value);
		}
		return glava;
	}
	/**
	 * Metoda koja ispisuje vrijednosti stabla od manje prema većoj
	 * @param glava čvor stabla za koje se ispisuju vrijednosti
	 */
	public static void outputIncrease(TreeNode glava) {
		if(glava==null) {
			return;
		}
		outputIncrease(glava.left);
		System.out.printf("%d ", glava.value);
		outputIncrease(glava.right);
		
	}
	/**
	 * Metoda koja ispisuje vrijednosti stabla od veće prema manjoj
	 * @param glava čvor stabla za koje se ispisuju vrijednosti
	 */
	public static void outputDecrease(TreeNode glava) {
		if(glava==null) {
			return;
		}
		
		outputDecrease(glava.right);
		
		System.out.printf("%d ", glava.value);
		outputDecrease(glava.left);
		
	}
	/**
	 * Metoda koja vraća veličinu stabla
	 * @param glava vrh stabla za koje se računa veličina
	 * @return broj kao vrijednost velične stabla
	 */
	public static int treeSize(TreeNode glava) {
		if(glava==null) {
			return 0;
		}
		return treeSize(glava.left)+treeSize(glava.right) +1;
	}
	
}
