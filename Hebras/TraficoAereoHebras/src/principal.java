import java.util.Random;
import java.util.concurrent.Semaphore;

public class principal {

	static protected Semaphore aterrizareNorte = new Semaphore(0, true);
	static protected Semaphore puedeAterrizarNorte = new Semaphore(0, true);
	static protected Semaphore aterrizareSur = new Semaphore(0, true);
	static protected Semaphore puedeAterrizarSur = new Semaphore(0, true);
	static protected Semaphore finNorte = new Semaphore(0, true);
	static protected Semaphore finSur = new Semaphore(0, true);
	static protected Semaphore cogerPista = new Semaphore(0, true);

	public static void main(String[] args) {

		Random rnd = new Random();
		Pista pista = new Pista();
		ControladorNorte controlNorte = new ControladorNorte(1);
		ControladorSur controlSur = new ControladorSur(1);
		Avion[] avion = new Avion[10];
		for (int i = 0; i < 10; i++) {
			avion[i] = new Avion(i, rnd.nextInt(2));
			avion[i].start();
		}
		controlNorte.start();
		controlSur.start();
		pista.start();
	}

}
