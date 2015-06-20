public class Consumidor extends Thread{
	private Mensaje mensaje;
	private int id;
	
	public Consumidor(Mensaje mensaje,int id){
		this.mensaje = mensaje;
		this.id = id;
	}
	
	public void run(){
		boolean fin = false;
		while (!fin && !this.isInterrupted() || mensaje.hayMensaje(id)){
			try {
				mensaje.leoMs(id);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				fin = true;
				System.out.println("Consumidor "+id+" interrumpido");
			}
		}
		System.out.println("Consumidor "+id+" termina");
	}

}