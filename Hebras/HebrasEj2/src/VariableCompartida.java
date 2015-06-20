public class VariableCompartida extends Thread {

	private int v;

	public VariableCompartida(int v) {
		this.v = v;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public void inc() {
		this.v++;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			this.inc();
			System.out.print(this.getV());
		}

	}
}
