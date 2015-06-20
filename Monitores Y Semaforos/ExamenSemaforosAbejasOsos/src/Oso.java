public class Oso extends Thread{
	
	private Tarro tarro;
	public Oso(Tarro tarro){
		this.tarro = tarro;
	}
	
	public void run(){
		boolean fin = false;
		while(!this.isInterrupted() && !fin){
			try {
				tarro.comeMiel();
			} catch (InterruptedException e) {
				fin = true;
			}
		}
	}

}