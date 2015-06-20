public class main {

	static protected DatoComp T = new DatoComp(0);
	static protected DatoComp P = new DatoComp(0);

	public static void main(String[] args) {

		SensorTemperatura sensorT = new SensorTemperatura();
		SensorPresion sensorP = new SensorPresion();
		Pantalla pant = new Pantalla();
		sensorT.start();
		pant.start();
		sensorP.start();
	}

}
