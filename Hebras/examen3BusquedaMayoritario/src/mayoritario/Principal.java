package mayoritario;

import java.util.*;

public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random r = new Random();
		int l = r.nextInt(10) + 4;
		List<Integer> lista = new ArrayList<Integer>();
		for (int i = 0; i < l; i++) {
			lista.add(r.nextInt(3));
		}
		// lista.add(0);lista.add(0);lista.add(1);
		System.out.println(lista + " Tamaño: " + lista.size());
		// completar
		BuscaMayoritario busca = new BuscaMayoritario(lista);
		busca.start();
		

	}

}
