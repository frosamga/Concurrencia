
public class Contador extends Thread {
	
	private String letra;
	
	public Contador(String letra){
		this.letra = letra;
	}
	
	public void run(){
		for(int i=0;i<100;i++){
			System.out.print(letra);
		}
	}
	

}
