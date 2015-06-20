import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
//	public static void main(String[] args) {
//		Montecarlo mc = new Montecarlo(100000000);
//		System.out.println(mc.generarEstimacionPi());
//	}
	public static void crearVentana(JFrame ventana) {
		Panel panel = new Panel();
		// w = new WorkerMontecarlo(panel);
		Controlador ctr = new Controlador(panel);
		panel.controlador(ctr);
		// w.controlador(ctr);
		ventana.add(panel);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final JFrame ventana = new JFrame("Aproximacion para PI");
		// crearVentana(ventana);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				crearVentana(ventana);
				// w.execute();
			}
		});
	}

}
