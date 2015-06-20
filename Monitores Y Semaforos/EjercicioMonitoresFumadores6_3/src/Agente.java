import java.util.*;

public class Agente extends Thread {

	private Random r = new Random();
	private Mesa mesa;

	public Agente(Mesa mesa) {
		this.mesa = mesa;
	}

	public void run() {
		while (true) {
			int ingrediente = r.nextInt(3) + 1;
			try {
				mesa.ingInverso(ingrediente);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
