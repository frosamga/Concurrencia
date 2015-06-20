package mayoritario;

import java.util.*;

//Alan Nicolas Martellotti

public class BuscaMayoritario extends Thread {

	private List<Integer> lista; // vector sobre el que se realiza la búsqueda
	private int mayoritario; // el valor mayoritario, si existe, una vez
								// encontrado
	private int veces = 0; // número de veces que se repite el
							// valor mayoritario, si existe, una vez encontrado
	private boolean hayMayoritario = false; // al final de la búsqueda
											// hayMayoritario es true, sii
											// vector tiene un valor mayoritario

	public BuscaMayoritario(List<Integer> lista) {
		this.lista = lista;
	}

	private static int numVeces(int a, List<Integer> lista) {
		int num = 0;
		for (int v : lista) {
			if (v == a)
				num++;
		}
		return num;
	}

	public boolean hayMayoritario() {
		return hayMayoritario;
	}

	public int veces() {
		return veces;
	}

	public int mayoritario() {
		return mayoritario;
	}

	/**
	 * 
	 * @param busca1
	 *            : hebra que busca el mayoritario, si existe, en la primera
	 *            mitad de vector
	 * @param busca2
	 *            : hebra que busca el mayoritario, si existe, en la segunda
	 *            mitad de vector
	 * 
	 *            El método calcula el valor mayoritario de vector, si existe,
	 *            teniendo en cuenta los que han calculado busca1 y busca2
	 * 
	 */
	private void calculaMayoritario(BuscaMayoritario busca1,
			BuscaMayoritario busca2) {
		if (busca1.hayMayoritario && busca2.hayMayoritario
				&& busca1.mayoritario == busca2.mayoritario) {
			mayoritario = busca1.mayoritario;
			veces = busca1.veces + busca2.veces;
			hayMayoritario = true;
		} else {
			if (busca1.hayMayoritario) {
				int num = numVeces(busca1.mayoritario,
						lista.subList(lista.size() / 2, lista.size()));
				if ((busca1.veces + num) > lista.size() / 2) {
					mayoritario = busca1.mayoritario;
					veces = busca1.veces + num;
					hayMayoritario = true;
				}
			}
			if (!hayMayoritario && busca2.hayMayoritario) {
				int num = numVeces(busca2.mayoritario,
						lista.subList(0, lista.size() / 2));
				if ((busca2.veces + num) > lista.size() / 2) {
					mayoritario = busca2.mayoritario;
					veces = busca2.veces + num;
					hayMayoritario = true;
				}
			}
		}
	}

	public String toString() {
		return lista.toString();
	}

	public void run() {
		if (lista.size() == 1) {
			System.out
					.println("El elemento mayoritario es " + lista.toString());
		} else {
			int mitad = lista.size() / 2;
			if (mitad > 1) {
				List<Integer> lista1 = lista.subList(0, mitad - 1);
				List<Integer> lista2 = lista.subList(mitad, lista.size() - 1);
				BuscaMayoritario b1 = new BuscaMayoritario(lista1);
				BuscaMayoritario b2 = new BuscaMayoritario(lista2);
				b1.start();
				b2.start();
				this.calculaMayoritario(b1, b2);
			}
		}
	}

}
