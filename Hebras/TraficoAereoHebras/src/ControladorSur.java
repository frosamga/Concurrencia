public class ControladorSur extends Thread {

	int id;

	public ControladorSur(int i) {
		id = i;
	}

	public void run() {

		while (true) {
			try {
				principal.aterrizareSur.release();
				principal.cogerPista.acquire();
				principal.puedeAterrizarSur.release();
				principal.finSur.acquire();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

		}
	}

}
