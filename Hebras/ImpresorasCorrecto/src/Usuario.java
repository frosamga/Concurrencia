import java.util.*;

public class Usuario extends Thread {

	private static Random r = new Random();
	private int id;
	private GestorImpresoras g;

	public Usuario(int id, GestorImpresoras g) {
		this.g = g;
		this.id = id;
	}

	public void run() {
		try {
			while (true) {
				int imp = g.necesito(id);
				// Thread.sleep(r.nextInt(500));
				g.libero(id, imp);
				// Thread.sleep(r.nextInt(1000));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
