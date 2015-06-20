import java.util.*;
public class Productor extends Thread{
	private Mensaje mensaje;
	private Random r = new Random();
	
	public Productor(Mensaje mensaje){
		this.mensaje = mensaje;
	}
	
	public void run(){
		for (int i=0; i<10; i++){
			try {
				Thread.sleep(500);
				mensaje.nuevoMs(r.nextInt(200));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
