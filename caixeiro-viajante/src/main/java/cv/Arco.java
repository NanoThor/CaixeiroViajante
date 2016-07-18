package cv;

public class Arco {
	private int verticeDe;
	private int verticePara;
	private double peso;

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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%d -> %d: %f", verticeDe, verticePara, peso);
	}
}
