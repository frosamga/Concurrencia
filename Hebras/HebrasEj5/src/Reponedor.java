public class Reponedor extends Thread {

	private Bandeja b;

	public Reponedor(Bandeja b) {
		this.b = b;
	}

	public void run() {
		try {
			while (true) {
				b.reponer();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
