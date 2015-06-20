import java.util.Random;

public class Clientes extends Thread {

	private int id;
	private Bandeja b;
	private Random r;

	public Clientes(Bandeja b,int id) {
		// la mitad de los clientes son normales y la otra premium
		this.id = id;
		this.b=b;
		r = new Random();
	}

	public void run() {
		while (true) {
			// si es par consume premium, sino consume normal, asi parto a la
			// mitad los clientes
			if (id % 2 == 0) {
				try {
					sleep(r.nextInt(1000) + 100);
					b.qPremium(id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					sleep(r.nextInt(1000) + 100);
					b.qNormal(id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
