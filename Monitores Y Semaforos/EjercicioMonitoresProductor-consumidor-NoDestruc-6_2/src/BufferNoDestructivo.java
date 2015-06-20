import java.util.Random;

public class BufferNoDestructivo {
	private int tamBuffer;
	private int[] ElementosBuffer;
	private int[] NumConsumicion;
	private int[] numDisponibles;
	private int[] posConsumicion;
	private int frente;
	private int numElem;
	private int numCons;
	private int produciendo;

	public BufferNoDestructivo(int tam, int numConsumidores) {
		this.tamBuffer = tam;
		this.numCons = numConsumidores;
		frente = 0;
		numElem = 0;
		ElementosBuffer = new int[tam];
		NumConsumicion = new int[tam];
		posConsumicion = new int[numConsumidores];
		numDisponibles = new int[numConsumidores];
		for (int i = 0; i < numConsumidores; i++) {
			posConsumicion[i] = 0;
			numDisponibles[i] = 0;
		}
		produciendo = 0;
	}

	public synchronized void poner() throws InterruptedException {
		while (numElem == tamBuffer) {
			wait();
		}
		ElementosBuffer[frente] = ++produciendo;
		NumConsumicion[frente] = numCons;
		frente = (frente + 1) % tamBuffer;
		numElem++;
		for (int i = 0; i < numCons; i++) {
			numDisponibles[i]++;
		}
		notifyAll();
	}

	public synchronized int coger(int id) throws InterruptedException {
		Random rnd = new Random();
		int resul;

		while (numDisponibles[id] == 0) {
			wait();
		}

		resul = ElementosBuffer[posConsumicion[id]];
		NumConsumicion[posConsumicion[id]]--;

		Thread.sleep(rnd.nextInt(2000) + 200);
		System.out.println("C: " + resul + " - veces restantes: "
				+ NumConsumicion[posConsumicion[id]]);

		if (NumConsumicion[posConsumicion[id]] == 0) {
			System.out.println("Todos los elementos consumidos.");
			numElem--;
			notifyAll();
		}
		posConsumicion[id] = (posConsumicion[id] + 1) % tamBuffer;
		numDisponibles[id]--;
		return resul;
	}
}
