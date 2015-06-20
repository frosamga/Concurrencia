public class Consumidor extends Thread {
	BufferNoDestructivo buffer;
	int id;

	public Consumidor(BufferNoDestructivo buf, int id) {
		buffer = buf;
		this.id = id;
	}

	public void run() {
		int elemento;

		while (true) {
			try {
				elemento = buffer.coger(id);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
