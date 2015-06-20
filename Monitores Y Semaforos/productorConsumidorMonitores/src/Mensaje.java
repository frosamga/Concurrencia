import java.util.*;
public class Mensaje {
	private int ms;
	private Set<Integer> consumidores;
	private int C;
	private boolean hayMensaje = false;
	public Mensaje(int C){
		this.C = C;
		consumidores = new HashSet<Integer>();
	}
	
	public synchronized void nuevoMs(int ms) throws InterruptedException{
		while (consumidores.size()!=0){
			wait();
		}
		System.out.println("Productor deja el mensaje "+ms);
		this.ms = ms;
		hayMensaje = true;
		for (int i = 0; i<C; i++){
			consumidores.add(i);
		notifyAll();
		}
	}
	
	public synchronized int leoMs(int id) throws InterruptedException{
		while (!consumidores.contains(id))
			wait();
		System.out.println("Consumidor "+id+" consume dato "+ms);
		consumidores.remove(id);
		if (consumidores.isEmpty()) {
			hayMensaje = false;
			notifyAll();
		}
		return ms;
	}
	public synchronized boolean hayMensaje(int id){
		return consumidores.contains(id);
	}

}
