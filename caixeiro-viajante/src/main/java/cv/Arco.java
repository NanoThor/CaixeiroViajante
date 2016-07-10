package cv;

public class Arco {
	int verticeDe;
	int verticePara;
	double peso;

	public Arco(int verticeDe, int verticePara, double peso) {
		super();
		this.verticeDe = verticeDe;
		this.verticePara = verticePara;
		this.peso = peso;
	}

	/**
	 * @return the verticeDe
	 */
	public int getVerticeDe() {
		return verticeDe;
	}

	/**
	 * @return the verticePara
	 */
	public int getVerticePara() {
		return verticePara;
	}

	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}

}
