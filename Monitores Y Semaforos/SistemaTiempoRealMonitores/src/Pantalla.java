
public class Pantalla extends Thread{
	
	public Pantalla(){
		
	}
	
	public void run () {
		boolean estadoant=false;
		boolean estadoantP=false;
		
		while (true) {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Info inf=main.T.Get();
			if (inf.estado!=estadoant) {
				if (inf.estado==false) {
					System.out.println("Cerramos");
					estadoant=false;
				} else {
					System.out.println("Abrimos");
					estadoant=true;
				}
			}
			System.out.print("Temp=");
			System.out.println(inf.d);

			inf=main.P.Get();
			if (inf.estado!=estadoantP) {
				if (inf.estado==false) {
					System.out.println("Cerramos V");
					estadoantP=false;
				} else {
					System.out.println("Abrimos V");
					estadoantP=true;
				}
			}
			System.out.print("Pres=");
			System.out.println(inf.d);


		
		}
	}

}
