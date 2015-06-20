import java.util.concurrent.*;

public class Tarro {

	private int tarro;
	private int capacidad;
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore lleno = new Semaphore(0, true);
	private Semaphore vacio = new Semaphore(0, true);

	public Tarro(int h) {
		capacidad = h;
	}

	public void nuevaPorcion(int id) throws InterruptedException {
		mutex.acquire();
		tarro++;
		System.out.println("La abeja " + id + " agrega miel al tarro :[ " + tarro+"]");
		if (tarro == capacidad) {
			System.out.println("La abeja " + id + " ve el tarro lleno y espera.");
			lleno.release();
			vacio.acquire();
		}

		mutex.release();
	}

	public void comeMiel() throws InterruptedException {
		lleno.acquire();
		tarro = 0;
		System.out.println("El oso se come toda la miel " + tarro);
		vacio.release();
	}

}