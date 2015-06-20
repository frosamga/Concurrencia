public class Pantalla extends Thread {

	public Pantalla() {

	}

	public void run() {
		boolean estadoAnterior = false;
		boolean estadoAnteriorP = false;

		while (true) {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
			Info inf = main.T.Get();
			if (inf.estado != estadoAnterior) {
				if (inf.estado == false) {
					System.out.println("Cerramos");
					estadoAnterior = false;
				} else {
					System.out.println("Abrimos");
					estadoAnterior = true;
				}
			}
			System.out.print("Temperatura ");
			System.out.println(inf.d);

			inf = main.P.Get();
			if (inf.estado != estadoAnteriorP) {
				if (inf.estado == false) {
					System.out.println("Cerramos valvula");
					estadoAnteriorP = false;
				} else {
					System.out.println("Abrimos valvula");
					estadoAnteriorP = true;
				}
			}
			System.out.print("Presion: ");
			System.out.println(inf.d);

		}
	}

}
