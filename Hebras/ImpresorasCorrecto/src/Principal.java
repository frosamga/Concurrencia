

public class Principal {
	
	public static void main(String[] args){
		int N = 10;
		GestorImpresoras g = new GestorImpresoras();
		Usuario[] usuario = new Usuario[N];
		for (int i = 0; i<usuario.length; i++){
			usuario[i] = new Usuario(i,g);
		}
		for (int i = 0; i<usuario.length; i++){
			usuario[i].start();
		}
	}

}
