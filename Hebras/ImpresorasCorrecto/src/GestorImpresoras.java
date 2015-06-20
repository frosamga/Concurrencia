
import java.util.concurrent.*;

public class GestorImpresoras {

	private int n = 2;
	private boolean libre1 = true, libre2 = true;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore espera = new Semaphore(0);

	public int necesito(int id) throws InterruptedException {
		int imp;
		mutex.acquire();
		n--;
		if (n < 0) {
			mutex.release();
			espera.acquire();
			mutex.acquire();
		}
		System.out.println("Usuario " + id
				+ " coge impresora. Estado: libre1-> " + libre1 + " libre2-> "
				+ libre2);
		if (libre1) {
			libre1 = false;
			imp = 1;
		} else {
			libre2 = false;
			imp = 2;
		}
		System.out.println("Usuario " + id + " tiene la impresora " + imp);
		mutex.release();
		return imp;
	}

	public void libero(int id, int imp) throws InterruptedException {
		mutex.acquire();
		n++;
		if (imp == 1)
			libre1 = true;
		else
			libre2 = true;
		System.out.println("Usuario " + id + " devuelve la impresora " + imp);
		if (n <= 0) {
			espera.release();
		}
		mutex.release();
	}

}
