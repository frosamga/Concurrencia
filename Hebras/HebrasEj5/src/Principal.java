public class Principal {

	public static Bandeja b;

	public static void main(String[] args) {

		int numClientes = 10;
		Clientes[] c = new Clientes[numClientes];
		b = new Bandeja();
		Reponedor r = new Reponedor(b);

		for (int i = 0; i < c.length; i++) {
			c[i] = new Clientes(b,i);
		}
		r.start();

		for (int i = 0; i < c.length; i++) {
			c[i].start();
		}

	}

}
