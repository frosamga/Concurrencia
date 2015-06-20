import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import javax.swing.SwingWorker;

public class WorkerMetodoLeibniz extends SwingWorker<Void, Pi> {

	Panel panel;
	private double contEstimaciones, max;
	private BigDecimal total;
	private int cont = 0;

	public WorkerMetodoLeibniz(Panel panel, int estimacion) {
		this.panel = panel;
		contEstimaciones = estimacion;
		max = estimacion;
		total = new BigDecimal(0);
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		BigDecimal pi;
		double div;
		while (contEstimaciones != 0 && !isCancelled()) {
			div = 1 + cont * 2;
			pi = new BigDecimal((Math.pow(-1, cont)) * (4d / div));
			total = total.add(pi);
			contEstimaciones--;
			cont++;
			panel.progreso2((int)(cont / max * 100));
			panel.escribeFinal2(total);
			
		}
		System.out.println("pi Leibniz = " + total);
		return null;
	}

	public void controlador(PropertyChangeListener ctr) {
		this.addPropertyChangeListener(ctr);
	}
}