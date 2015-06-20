public class ControladorNorte extends Thread {
	int id;

	public ControladorNorte(int i) {
		id = i;
	}

	public void run() {
		while (true) {
			try {
				principal.aterrizareNorte.acquire();
				principal.cogerPista.acquire();
				principal.puedeAterrizarNorte.release();
				principal.finNorte.acquire();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
