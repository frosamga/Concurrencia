public class Principal {

	/**
	 * Examen de concurrencia de semaforos.
	 * 
	 * Sean n abejas de miel y un oso goloso
	 * que comparte un tarro de miel. El tarro est� inicialmente vac�o: tiene
	 * capacidad para H porciones de miel. El oso s�lo come del tarro cuando
	 * est� lleno y, en ese caso, se traga de una sentada todas las porciones de
	 * una vez. El resto del tiempo el oso est� descansando. Cada abeja
	 * repetidamente recoge una porci�n de miel y la pone en el tarro; si una
	 * abeja llega al tarro y pone la �ltima raci�n, despierta al oso para que
	 * se tome toda la miel del tarro. Implementa este sistema utilizando
	 * sem�foros binarios, suponiendo que hay una cantidad indefinida de miel
	 * que las abejas pueden recoger, y que el oso est� descansando mientras no
	 * puede comer. Para ello debes implementar: - Una clase Tarro que
	 * proporciona los m�todos void nuevaPorcion(int), void comoMiel(), usados,
	 * respectivamente, por las abejas y el oso para dejar porciones de miel, y
	 * para beberse la miel del tarro - Dos clases Oso, y Abeja que implementan
	 * el comportamiento del oso y las abejas. - Una case Principal que pone en
	 * marcha todo el sistema. b) Implementa el sistema para que termine. Sup�n
	 * que cada abeja deposita un n�mero finito de porciones de miel en el
	 * tarro. Para simplificar el problema, sup�n que la capacidad del tarro H
	 * es m�ltiplo del n�mero total de porciones depositadas por las abejas.
	 * 
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		int H = 5;
		int N = 10;
		Tarro tarro = new Tarro(H);
		Oso oso = new Oso(tarro);
		Abeja[] abeja = new Abeja[N];
		for (int i = 0; i < N; i++) {
			abeja[i] = new Abeja(tarro, i);
		}
		oso.start();
		for (int i = 0; i < N; i++) {
			abeja[i].start();
		}
		try {
			for (int i = 0; i < N; i++) {
				abeja[i].join();
			}
		} catch (InterruptedException ie) {
		}
		oso.interrupt();
	}

}