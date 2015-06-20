import java.util.Random;
import java.util.concurrent.Semaphore;

public class Avion extends Thread {

	int id, direccion;
	private Random rnd = new Random();

	// 0 norte, 1 sur
	public Avion(int i, int direccion) {
		id = i;
		this.direccion = direccion;
	}

	private void vuela() throws InterruptedException {
		Thread.sleep(rnd.nextInt(3000));
	}

	private void aterriza() throws InterruptedException {
		int ladoElegido = direccion;
		principal.cogerPista.acquire();
		if (ladoElegido == 0) {
			principal.aterrizareSur.release();
			principal.puedeAterrizarSur.release();
			System.out.println("Aterrizando en el sur");
			principal.aterrizareSur.acquire();
			principal.puedeAterrizarSur.acquire();
			System.out.println("avion número:" + id);
			principal.finSur.release();

		} else {
			principal.aterrizareNorte.release();
			principal.puedeAterrizarNorte.release();
			System.out.println("Aterrizando en el norte");
			principal.aterrizareNorte.acquire();
			principal.puedeAterrizarNorte.acquire();
			System.out.println("avion número:" + id);
			principal.finNorte.release();
		}
	}

	public void run() {
		while (true) {
			try {
				vuela();
				aterriza();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}