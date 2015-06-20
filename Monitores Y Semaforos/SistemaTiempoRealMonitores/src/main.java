
public class main {
	
	static protected DatoComp T = new DatoComp (0);
	static protected DatoComp P = new DatoComp (0);
	
	//Solucion campus virtual
	public static void main(String[] args) {
		
		SensorT St= new SensorT ();
		SensorP Sp= new SensorP ();
		Pantalla p =new Pantalla();
		St.start();
		p.start();
		Sp.start();
	}

}
