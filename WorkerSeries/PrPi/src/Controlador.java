import java.awt.event.*;
import java.beans.*;
import java.awt.*;

public class Controlador implements ActionListener, PropertyChangeListener {

	private Panel panel;
	private WorkerMetodoMontecarlo w;
	private WorkerMetodoLeibniz ws;

	public Controlador(Panel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("COMENZAR")) {
			panel.progreso(0);
			panel.progreso2(0);
			panel.limpia1();
			panel.limpia2();

			int n = panel.numero();
			if (n < 0 || n > 1000000000) {
				//Agregar un lugar donde ponerle que no acepta numeros negativos ni mayores que 2^16
				//panel.
			} else {
				w = new WorkerMetodoMontecarlo(panel, n);
				ws = new WorkerMetodoLeibniz(panel, n);
				w.controlador(this);
				ws.controlador(this);
				w.execute();
				ws.execute();
			}
		} else {
			w.cancel(true);
			ws.cancel(true);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent ev) {
		if (ev.getPropertyName().equals("progress")) {
			int progreso = (Integer) ev.getNewValue();
			// panel.setProgreso(progreso);
			if (progreso >= 100) {
				// panel.setFin();
			}
		}
	}

}
