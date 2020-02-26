package searching.algorithms;

public class Main {
	public static void main(String[] args) {
		int[] polje= new int[]{0,1,2,3,4,5,6,7,8};
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (polje[i * 3 + j] == 0) {
					sb.append("*");
				} else {
					sb.append(String.valueOf(polje[i * 3 + j]));
				}
			}
			sb.append("\r\n");
		}System.out.println(sb.toString());
	}
}
