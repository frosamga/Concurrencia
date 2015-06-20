public class main {

	/**
	 * Implementar el problema del productorconsumidor utilizando varios
	 * productores y varios consumidores con la particularidad de que el buffer
	 * en esta ocasión es NO destructivo. Esto es: cada vez que un productor
	 * coloca un elemento en el buffer, el elemento tiene que ser consumido por
	 * todos los consumidores (una sola vez por cada consumidor) antes de poder
	 * liberar la posición asociada al buffer. El resto del funcionamiento es
	 * similar al productor-‐consumidor normal. Por ejemplo, si en el buffer
	 * tenemos los elementos [1,2,3,4] y 3 consumidores, se podría tener que
	 * cada consumidor ha consumido los siguientes elementos: C1: 1, 2 C2: 1 C3:
	 * 1, 2, 3 Hasta que un elemento no ha sido consumido por todos los c
	 * onsumidores, la posición no se libera. En el ejemplo anterior, el
	 * elemento "1" ha sido consumido por todos los consumidores y su posición
	 * en el buffer podría ser utilizada para colocar otro elemento. El elemento
	 * "2", por el contrario, sólo ha sido consumidor por C1 y C3 por lo que su
	 * posición todavía no puede ser liberada en el buffer.
	 * 
	 * @param args
	 */

	// Alan Nicolás Martellotti
	public static void main(String args[]) {
		int ncons = 5;
		int i;
		Productor prod;
		Consumidor[] cons;
		BufferNoDestructivo buf;

		buf = new BufferNoDestructivo(1, 4);
		prod = new Productor(buf);
		cons = new Consumidor[ncons];

		prod.start();
		for (i = 0; i < ncons; i++) {
			cons[i] = new Consumidor(buf, i);
			cons[i].start();
		}
	}
}
