public class Mesa {
	// 0-vacio 1-tabaco, 2-papel, 3-cerillas
	private int ingrediente = 0;
	private boolean fumando = false;

	// como pone dos ingredientes, hago el contrario y digo cual no pone
	public synchronized void ingInverso(int ingr) throws InterruptedException {

		while (this.ingrediente != 0 || fumando) {
			wait();
		}
		this.ingrediente = ingr;
		switch (ingr) {
		case 1:
			System.out.println("En la mesa hay papel y cerillas, pero falta: " + "tabaco");
			break;
		case 2:
			System.out.println("En la mesa hay tabaco y cerillas, pero falta: " + "papel");
			break;
		case 3:
			System.out.println("En la mesa hay papel y tabaco, pero falta: " + "cerillas");
			break;
		default:
			System.out.println("La mesa esta vacia");
		}

		notifyAll();
	}

	public synchronized void fumar(int id) throws InterruptedException {
		while (ingrediente != id || fumando) {
			wait();
		}
		ingrediente = 0;
		fumando = true;
	}

	public synchronized void finFumar() {
		fumando = false;
		notifyAll();
	}
}
