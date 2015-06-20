import java.util.Random;
import java.util.concurrent.*;

/**
 * En una cadena de montaje existe un robot encargado de colocar productos de 3
 * tipos diferentes (1, 2 o 3) en la cadena de montaje. Otros robots, retiran
 * los productos de la cadena de montaje para realizar su empaquetado, teniendo
 * en cuenta que están especializados en un solo tipo de producto (1, 2 o 3),
 * ignorando los que no son de su tipo. Finalmente, se quiere llevar un control
 * del total de productos empaquetados (independientemente de su tipo). Modelar
 * utilizando semáforos el sistema descrito con las siguientes indicaciones:
 * -Modelar cada robot como una hebra (1 colocador y 3 empaquetadores, uno para
 * cada tipo de producto). -Los productos son colocados de uno en uno en la
 * cadena, y solamente en posiciones libres (se puede considerar que en la
 * cadena de montaje caben un máximo N de elementos). Si no hay posiciones
 * libres el robot colocador tendrá que esperar hasta que algún producto sea
 * retirado de la cadena. -Los robots empaquetadores se especializan en un tipo
 * de producto (1, 2 o 3) en tiempo de inicialización. -Los robots
 * empaquetadores comprueban si hay algún elemento de su tipo en la cadena
 * ignorando los productos que no sean de su tipo. Si hay algún producto de su
 * tipo lo retiran de la cadena (sólo 1 producto cada vez) y la posición queda
 * libre para colocar nuevos productos, en caso contrario se quedan a la espera
 * de que haya nuevos productos. -Los robots empaquetadores de distinto tipo
 * pueden funcionar a la vez. -Tanto el colocador como los empaquetadores nunca
 * acaban. -Cada vez que un robot empaquetador procesa un producto, la cuenta
 * total de productos empaquetados debe aumentar y mostrarse un mensaje por
 * pantalla
 * 
 * @author alan
 * 
 */

public class Robots {
	static final int TAM_CINTA = 10;
	static int numEmpaquetador = 0;
	static int[] cinta = new int[TAM_CINTA];
	static Semaphore semaforoDatos = new Semaphore(TAM_CINTA);
	static Semaphore[] semaforoProduccion = { new Semaphore(0),
			new Semaphore(0), new Semaphore(0) };
	static Semaphore mutexNumEmpaquetador = new Semaphore(1);
	static Semaphore mutexCinta = new Semaphore(1);
	static Random rnd = new Random();

	static class Colocador extends Thread {
		public void run() {
			int tipo, i;
			try {
				while (true) {
					tipo = rnd.nextInt(2) + 1;
					System.out.println("colocando producto " + tipo);
					Thread.sleep(rnd.nextInt(2000));
					semaforoDatos.acquire();

					i = 0;
					while (cinta[i] != 0) {
						i++;
					}
					cinta[i] = tipo;
					semaforoProduccion[tipo].release();
				}
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	static class Empaquetador extends Thread {
		private int tipo;

		Empaquetador(int tipo) {
			this.tipo = tipo;
		}

		public void run() {
			int cont = 0;
			try {
				while (true) {
					semaforoProduccion[tipo].acquire();

					for (int i = 0; i < cinta.length; i++) {
						if (cinta[i] == tipo) {
							System.out.println("Empaquetando: tipo[" + tipo
									+ "],posición[" + i + "]");
							cont = i;
						}
					}
					cinta[cont] = 0;
					semaforoDatos.release();
					Thread.sleep(rnd.nextInt(2000));
					mutexNumEmpaquetador.acquire();
					numEmpaquetador++;
					System.out.println(numEmpaquetador + " empaquetados");
					mutexNumEmpaquetador.release();
				}
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		Colocador prod = new Colocador();
		Empaquetador[] empaquetador = { new Empaquetador(0),
				new Empaquetador(1), new Empaquetador(2) };
		prod.start();
		for (Empaquetador x : empaquetador)
			x.start();
	}
}
