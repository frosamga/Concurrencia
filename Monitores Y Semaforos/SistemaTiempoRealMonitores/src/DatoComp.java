
class DatoComp {
	private Info dato;

	public DatoComp(float ini) {

		dato = new Info();
		dato.d = ini;
	}

	public synchronized void Set(float i, boolean est) {

		dato.d = i;
		dato.estado = est;
	}

	public synchronized Info Get() {
		return dato;
	}

}
