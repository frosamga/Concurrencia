import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * En un sistema industrial existen tres sensores que realizan mediciones del
 * nivel de temperatura, humedad y luz respectivamente. Cuando se han recogido
 * mediciones de los tres sensores, existe un dispositivo “trabajador” encargado
 * de realizar ciertas tareas según las mediciones realizadas. El dispositivo no
 * puede comenzar a realizar sus tareas hasta que se han recogido mediciones de
 * los tres sensores, y los sensores no pueden volver a realizar mediciones
 * hasta que el dispositivo finaliza sus tareas. El proceso se repite de forma
 * indefinida de manera que cuando el dispositivo finaliza sus tareas, volverá a
 * esperar a que haya mediciones de los tres sensores. Realizar utilizando
 * semáforos el modelado de dicho sistema. Modelar el dispositivo trabajador y
 * cada sensor como una hebra (con lo cual habrá un total de 4 hebras). Modelar
 * el proceso de realizar mediciones y las tareas del dispositivo con retrasos
 * aleatorios y valores de tipo entero. Inicialmente puede suponerse que los
 * sensores pueden comenzar haciendo peticiones
 * 
 * 
 * @author alan
 * 
 */

public class SistemaIndustrial {

	static Semaphore trabajando = new Semaphore(0, true);
	static Semaphore[] sensores = new Semaphore[3];
	static int[] valores = new int[3];
	static Random rnd = new Random();

	public static class Sensorer extends Thread {

		private int numSensor;

		public Sensorer(int sensor) {
			numSensor = sensor;
		}

		public void run() {
			while (true) {
				try {
					// un tiempo para que descanse el trabajador
					new Thread().sleep(rnd.nextInt(2000));
					sensores[numSensor].acquire();
					// temperatura, humedad y luz
					if (numSensor == 0) {
						valores[numSensor] = rnd.nextInt(50);
					} else if (numSensor == 1) {
						valores[numSensor] = rnd.nextInt(100);
					} else {
						valores[numSensor] = rnd.nextInt(10);
					}

					trabajando.release();
				} catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}

	}

	public static class Trabajador extends Thread {
		public void run() {
			while (true) {
				try {
					// espera a las mediciones de los sensores
					trabajando.acquire();
					trabajando.acquire();
					trabajando.acquire();
					System.out.println("Temperatura:" + valores[0]
							+ ", Humedad: " + valores[1] + ", Luz:"
							+ valores[2] + "]");
					sensores[0].release();
					sensores[1].release();
					sensores[2].release();
				} catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}

	}

	public static void main(String[] args) {

		// definimos los 3 sensores activos
		sensores[0] = new Semaphore(1, true);
		sensores[1] = new Semaphore(1, true);
		sensores[2] = new Semaphore(1, true);

		Trabajador trabajador = new Trabajador();
		Sensorer sensor0 = new Sensorer(0);
		Sensorer sensor1 = new Sensorer(1);
		Sensorer sensor2 = new Sensorer(2);

		trabajador.start();
		sensor0.start();
		sensor1.start();
		sensor2.start();

	}

}
