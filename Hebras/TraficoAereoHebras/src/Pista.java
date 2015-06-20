public class Pista extends Thread {
	public void run() {
		while (true) {
			try {
				sleep(2000);
				principal.cogerPista.release();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
