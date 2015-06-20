import java.util.HashSet;
import java.util.Set;

public class Bandeja {

	private int pastelesNormales;
	private int pastelesPremium;
	private boolean hayPastelesNormales = true;
	private boolean hayPastelesPremium = true;

	public Bandeja() {
		// se crea la bandeja llena
		pastelesNormales = 4;
		pastelesPremium = 4;

	}

	/**
	 * el reponedor espera hasta que se termina de un tipo de pasteles. Rellena
	 * pastelitos en la bandeja y *los pone todos de una vez
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void reponer() throws InterruptedException {
		while (hayPastelesNormales || hayPastelesPremium) {
			wait();
		}
		if (!hayPastelesNormales) {
			pastelesNormales = 4;
			hayPastelesNormales = true;
			System.out.println("se reponen todos los pasteles normales");
		}
		if (!hayPastelesPremium) {
			pastelesPremium = 4;
			hayPastelesPremium = true;
			System.out.println("se reponen todos los pasteles premium");
		}
		notifyAll();
	}

	/**
	 * el cliente premium quiere su pastel
	 * 
	 * @throws InterruptedException
	 */

	public synchronized void qPremium(int id) throws InterruptedException {

		while (!hayPastelesPremium) {
			wait();
		}
		if (pastelesPremium == 0) {
			hayPastelesPremium = false;
			System.out
					.println("No hay mas pasteles premium, tendra que esperar");
		} else {
			pastelesPremium--;
			System.out
					.println("El consumidor premium "
							+ id
							+ " consume un pastel premium(se va a dormir y luego vuelve), quedan        ->"
							+ pastelesPremium + " (p)" + pastelesNormales
							+ " (n)");
		}
		notifyAll();
	}

	/**
	 * el cliente normal quiere su pastel
	 * 
	 * @throws InterruptedException
	 */

	public synchronized void qNormal(int id) throws InterruptedException {
		while (!hayPastelesNormales) {
			wait();
		}
		if (pastelesNormales == 0) {
			hayPastelesNormales = false;
			System.out
					.println("No hay mas pasteles normales, tendra que esperar");
		} else {
			pastelesNormales--;
			System.out
					.println("El consumidor de normales "
							+ id
							+ " consume un pastel normal(se va a dormir y luego vuelve), quedan        ->"
							+ pastelesPremium + " (p)" + pastelesNormales
							+ " (n)");

		}		
		notifyAll();
	}
}
