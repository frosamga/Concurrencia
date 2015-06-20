public class Main {

	/** Ejercicio Monitores Fumadores.
	 * Considera un sistema formado por tres hebras fumadores que se pasan el
	 * día liando cigarros y fumando. Para liar un cigarro necesitan tres
	 * ingredientes: tabaco, papel y cerillas. Cada fumador dispone de un surtido
	 * suficiente (para el resto de su vida) de uno de los tres ingredientes.
	 * Cada fumador tiene un ingrediente diferente, es decir, un fumador tiene
	 * una cantidad infinita de tabaco, el otro de papel y el otro de cerillas.
	 * Hay también una hebra agente que pone dos de los tres ingredientes encima
	 * de una mesa. El agente dispone de unas reservas infinitas de cada uno de
	 * los tres ingredientes y escoge de forma aleatoria cuáles son los
	 * ingredientes que pondrá encima de la mesa. Cuando los ha puesto, el
	 * fumador que tiene el otro ingrediente puede fumar (los otros dos no).
	 * Para ello coge los ingredientes, se lía un cigarro y se lo fuma. Cuando
	 * termina de fumar vuelve a repetirse el ciclo. En resumen, el ciclo que
	 * debe repetirse es : "agente pone ingredientes -fumador hace cigarro
	 * -fumador fuma -fumador termina de fumar -agente pone ingredientes -..."
	 * Es decir, en cada momento a lo sumo hay un fumador fumando
	 * 
	 * 
	 * 
	 *  Alan
	 */

	public static void main(String[] args) {
		Mesa mesa = new Mesa();
		Agente agente = new Agente(mesa);
		Fumador[] fumador = new Fumador[3];
		for (int i = 0; i < 3; i++) {
			fumador[i] = new Fumador(i + 1, mesa);
		}
		agente.start();
		for (int i = 0; i < 3; i++) {
			fumador[i].start();
		}
	}
}
