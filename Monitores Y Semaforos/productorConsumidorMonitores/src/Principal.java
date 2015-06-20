public class Principal {
	
	public static void main(String[] args){
		int cantidad = 10;
		Mensaje mensaje = new Mensaje(cantidad);
		Productor prod = new Productor(mensaje);
		Consumidor[] cons = new Consumidor[cantidad];
		for (int i = 0; i<cantidad; i++){
			cons[i] = new Consumidor(mensaje,i);
		}
		prod.start();
		for (int i = 0; i<cantidad; i++){
			cons[i].start();
		}
		try{
			prod.join();
		}catch(InterruptedException ie){}
		for (int i = 0; i<cantidad; i++){
			cons[i].interrupt();
		}
	}

}
