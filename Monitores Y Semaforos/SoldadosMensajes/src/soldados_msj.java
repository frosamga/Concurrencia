// javac -cp ".:../mensajes.jar" soldados_msj.java
// java -cp ".:../mensajes.jar" soldados_msj

import java.util.Random;
import java.util.ArrayList;

import mensajes.Channel;

/**
 * Un grupo de M soldados y un general está rodeado por el enemigo y no hay
 * posibilidad de victoria sin refuerzos, pero hay sólo un caballo para escapar
 * y pedir dichos refuerzos. Los soldados llegan a un acuerdo para determinar
 * quién va a escapar y pedir ayuda. El general elige un número N al azar y a
 * uno de sus soldados. Empezando por el soldado elegido por el general, cuentan
 * en el sentido de las agujas del reloj, N soldados, quedando descartado el
 * soldado encontrado en el lugar N-ésimo. La cuenta empieza de nuevo con el
 * siguiente soldado (el que sigue al eliminado según el sentido de las agujas
 * del reloj). El proceso continúa de forma que cada vez que la cuenta llega a N
 * se descarta a un soldado para escapar. Una vez que un soldado se saca del
 * círculo ya no se vuelve a contar. El soldado que queda al final es el que
 * coge el caballo y escapa. Utilizando paso de mensajes realizar el modelado
 * del sistema propuesto de manera que al final se indique cuál es el soldado
 * que irá a pedir refuerzos
 * 
 * 
 * @author alan
 * 
 */

public class soldados_msj {
	private static final int NHEBRAS = 10;

	// ----------------------------------------------------------------------
	static class Mensaje {
		private final int ini; // id soldado inicial // número soldados activos
		private final int max; // número seleccionado al azar
		private final int cnt; // cuenta decreciente

		private Mensaje(int i, int m, int c) {
			ini = i;
			max = m;
			cnt = c;
		}

		// ----------------------------
		public int getIni() {
			return ini;
		}

		public int getMax() {
			return max;
		}

		public int getCnt() {
			return cnt;
		}

		// ----------------------------
		public boolean esTerminacion() {
			return ((ini == 0) && (max == 0) && (cnt == 0));
		}

		public boolean esMsjInicial() {
			return ((max > 0) && (max == cnt));
		}

		public boolean esMsjSeleccion() {
			return ((ini == 1) && (max > 0) && (max == cnt + 1));
		}

		public boolean esFinalCuentaAtras() {
			return ((ini > 1) && (max > 0) && (cnt == 0));
		}

		// ----------------------------
		static public Mensaje crearTerminacion() {
			return new Mensaje(0, 0, 0);
		}

		static public Mensaje crearInicial(int sol_id, int max) {
			return new Mensaje(sol_id, max, max);
		}

		static public Mensaje crear(int n_sol, int max, int cnt) {
			return new Mensaje(n_sol, max, cnt);
		}
	}

	// ----------------------------------------------------------------------
	static class General implements Runnable {
		private Random rnd = new Random();
		private int nhebras;
		private Channel<Mensaje> dch;

		public General(int nhebras, Channel<Mensaje> d) {
			this.nhebras = nhebras;
			this.dch = d;
		}

		public void run() {
			try {
				int soldado_inicial = rnd.nextInt(nhebras);
				int numero_aleatorio = rnd.nextInt(50) + 1;
				dch.send(Mensaje
						.crearInicial(soldado_inicial, numero_aleatorio));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// ----------------------------------------------------------------------
	static class Soldado implements Runnable {
		private int nhebras;
		private int id;
		private boolean eliminado;
		private boolean seleccionado;
		private Channel<Mensaje> izq;
		private Channel<Mensaje> dch;
		private Channel<Mensaje> gen;

		public Soldado(int nhebras, int id, Channel<Mensaje> i,
				Channel<Mensaje> d, Channel<Mensaje> g) {
			this.nhebras = nhebras;
			this.id = id;
			this.izq = i;
			this.dch = d;
			this.gen = g;
			this.eliminado = false;
			this.seleccionado = false;
		}

		public void run() {
			boolean no_fin = true;
			Mensaje msj = null;
			try {
				// --------------------
				if (gen != null) {
					msj = gen.receive();
					if (msj.getIni() == id) {
						// Comenzar la cuenta atrás
						msj = Mensaje.crear(nhebras, msj.getMax(),
								msj.getMax() - 1);
					} // else pasar el mensaje
					dch.send(msj);
				}
				// --------------------
				while (no_fin) {
					msj = izq.receive();
					/* TRAZA */System.out.println("Recv[" + id + "] "
							+ msj.getIni() + " " + msj.getMax() + " "
							+ msj.getCnt());
					if (msj.esTerminacion()) {
						// Mensaje de Terminación
						no_fin = false;
						if (!seleccionado) {
							// Pasar el mensaje de terminación al siguiente
							dch.send(msj);
						}
					} else if (msj.esMsjInicial()) {
						// Mensaje Inicial
						if (msj.getIni() == id) {
							// Comenzar la cuenta atrás
							msj = Mensaje.crear(nhebras, msj.getMax(),
									msj.getMax() - 1);
						} // else Pasar el mensaje inicial
						dch.send(msj);
					} else if (eliminado) {
						// Si ya está eliminado, pasar el mensaje
						dch.send(msj);
					} else if (msj.esMsjSeleccion()) {
						// Este soldado es el último y el seleccionado
						seleccionado = true;
						// Iniciar mensaje de terminación
						dch.send(Mensaje.crearTerminacion());
					} else if (msj.esFinalCuentaAtras()) {
						// Final de la cuenta atrás
						eliminado = true;
						// Comenzar la cuenta atrás cun un soldado menos
						dch.send(Mensaje.crear(msj.getIni() - 1, msj.getMax(),
								msj.getMax() - 1));
					} else {
						// Continuar la cuenta atrás
						dch.send(Mensaje.crear(msj.getIni(), msj.getMax(),
								msj.getCnt() - 1));
					}
				}
				// --------------------
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (seleccionado) {
				System.out.println("Soldado seleccionado: " + id);
			}
		}
	}

	// ----------------------------------------------------------------------
	public static void main(String[] args) {
		// G --n--> H0 --0--> H1 --1--> H2 --2--> H3 ... Hn-1 --n-1--> H0
		try {
			ArrayList<Channel<Mensaje>> canal = new ArrayList<Channel<Mensaje>>();
			for (int i = 0; i < NHEBRAS + 1; ++i) {
				canal.add(new Channel<Mensaje>());
			}
			Thread gen = new Thread(new General(NHEBRAS, canal.get(NHEBRAS)));
			gen.start();
			Thread[] hebra = new Thread[NHEBRAS];
			for (int i = 0; i < hebra.length; ++i) {
				Channel<Mensaje> izq = ((i == 0) ? canal.get(NHEBRAS - 1)
						: canal.get(i - 1));
				Channel<Mensaje> dch = canal.get(i);
				Channel<Mensaje> gc = ((i == 0) ? canal.get(NHEBRAS) : null);
				hebra[i] = new Thread(new Soldado(NHEBRAS, i, izq, dch, gc));
				hebra[i].start();
			}
			gen.join();
			for (int i = 0; i < hebra.length; ++i) {
				hebra[i].join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ----------------------------------------------------------------------
}
