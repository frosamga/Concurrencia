import java.util.Random;

/**
 * Ejercio de Monitores
 * 
 * @author alan
 * 
 *         Varios procesos (N) compiten por utilizar unos cuantos recursos Rec,
 *         en exclusión mutua. El uso correcto de los recursos es gestionado por
 *         una clase Control. Cada proceso pide un numero de recursos llamando
 *         al método del monitor qrecursos(id,num), donde id es el identificador
 *         del proceso que hace la petición y num es un número entero que
 *         especifica el número de recursos que pide el proceso. Después de usar
 *         los recursos, el proceso los libera ejecutando el método
 *         librecursos(id,num). Para asignar los recursos Control utiliza la
 *         técnica FCFS (First Come, First Serve) que significa que los procesos
 *         son servidos siempre en el orden en el que han realizado sus
 *         peticiones. Así un proceso que pide num recursos debe esperarse si
 *         hay otros procesos esperando aún cuando existan num recursos
 *         disponibles en el sistema. Implementa la clase Control, y varias
 *         hebras usuarios que realicen las peticiones a Control
 */

public class recursos {
	static final int NUM_TAREAS = 5;
	static final int MAX_RECURSOS = 10;

	static class Control {
		private int turno = 0;
		private int turno_actual = 0;
		private int max_recursos;
		private int numRecursos;

		public Control(int n) {
			if (n <= 0) {
				throw new IllegalArgumentException();
			}
			max_recursos = n;
			numRecursos = n;
		}

		public synchronized void qrecursos(int id, int num)
				throws InterruptedException {
			if ((num <= 0) || (num > max_recursos)) {
				throw new IllegalArgumentException();
			}
			int mi_turno = turno;
			++turno;
			while ((turno_actual != mi_turno) || (num > numRecursos)) {
				System.out.println("Tarea " + id + " espera por " + num);
				wait();
			}
			numRecursos -= num;
			System.out.println("Tarea " + id + " adquiere " + num + " ["
					+ numRecursos + "]");
			++turno_actual;
			notifyAll();
		}

		public synchronized void librecursos(int id, int num) {
			if (num <= 0) {
				throw new IllegalArgumentException();
			}
			System.out.println("Tarea " + id + " libera " + num);
			numRecursos += num;
			notifyAll();
		}
	}

	static class Tarea implements Runnable {
		private Random rnd = new Random();
		private int id;
		private Control control;

		public Tarea(int id, Control c) {
			this.id = id;
			control = c;
		}

		public void run() {
			try {
				while (true) {
					int num = 1 + rnd.nextInt(MAX_RECURSOS / 2);
					control.qrecursos(id, num);
					Thread.sleep(500 + rnd.nextInt(2000));
					control.librecursos(id, num);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			Control control = new Control(MAX_RECURSOS);
			Thread[] tarea = new Thread[NUM_TAREAS];
			for (int i = 0; i < tarea.length; ++i) {
				tarea[i] = new Thread(new Tarea(i, control));
				tarea[i].start();
			}
			for (int i = 0; i < tarea.length; ++i) {
				tarea[i].join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
