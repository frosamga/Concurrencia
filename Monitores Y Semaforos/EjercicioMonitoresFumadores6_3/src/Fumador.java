import java.util.*;

public class Fumador extends Thread {
	private int id;
	private Mesa mesa;
	private Random r = new Random();

	public Fumador(int id, Mesa mesa) {
		this.id = id;
		this.mesa = mesa;
	}

	public void run() {
		while (true) {
			try {
				mesa.fumar(id);
				if (id == 1) {
					System.out
							.println("El fumador con tabaco, se lia el cigarro y se lo fuma");
				} else if (id == 2) {
					System.out
							.println("El fumador con papel, se lia el cigarro y se lo fuma"+"");
				} else if (id == 3) {
					System.out
							.println("El fumador con cerillas, se enciende el cigarro y se lo fuma");
				}
				System.out.println("El fumador termina de fumar\n");
				Thread.sleep(r.nextInt(2000)+200);
				mesa.finFumar();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
