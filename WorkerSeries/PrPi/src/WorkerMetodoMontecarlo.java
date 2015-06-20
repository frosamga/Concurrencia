import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Random;

import javax.swing.SwingWorker;

public class WorkerMetodoMontecarlo extends SwingWorker<Void, Pi> {

	Panel panel;
	private Random rnd;
	private int radio;
	private double contDentro, contTotal, contEstimaciones,max;

	public WorkerMetodoMontecarlo(Panel panel, int estimacion) {
		this.panel = panel;
		rnd = new Random(System.currentTimeMillis());
		contDentro = 0;
		contTotal = 0;
		contEstimaciones = estimacion;
		max=estimacion;
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		BigDecimal pi = null;
		while (contEstimaciones != 0 && !isCancelled()) {
			double x = rnd.nextDouble() * 2 - 1.0;
			double y = rnd.nextDouble() * 2 - 1.0;
			if (estaDentro(x, y)) {
				contDentro++;
			}
			contTotal++;
			contEstimaciones--;
			panel.progreso((int) (contTotal/max*100));
			pi = this.evaluarPi();
			panel.escribeFinal(pi);
		}

		System.out.println("pi Montecarlo = " + pi);
		return null;
	}

	public void controlador(PropertyChangeListener ctr) {
		this.addPropertyChangeListener(ctr);
	}

	private boolean estaDentro(double x, double y) {
		double aux = (double) Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
		if (aux < 1) {
			return true;
		} else {
			return false;
		}
	}

	private BigDecimal evaluarPi() {
		return new BigDecimal(4 * (contDentro / contTotal));
	}
}
