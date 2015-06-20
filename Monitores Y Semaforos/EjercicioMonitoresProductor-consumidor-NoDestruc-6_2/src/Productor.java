public class Productor extends Thread {
	BufferNoDestructivo buffer;

	public Productor(BufferNoDestructivo buf) {
		buffer = buf;
	}

	public void run() {
		while (true) {
			try {
				buffer.poner();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
