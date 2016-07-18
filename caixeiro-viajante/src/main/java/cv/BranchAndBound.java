package cv;

public class BranchAndBound implements Runnable {

	private static class BaBArco extends Arco {
		public BaBArco(int verticeDe, int verticePara, double peso) {
			super(verticeDe, verticePara, peso);
		}
	}

	public static Grafo process(Grafo g) {
		int numeroDeVertices = g.getNumeroDeVertices();
		int[] memo = new int[numeroDeVertices];
		Grafo bab = new Grafo(1);

		// //
		// Criar a arvore branch and bound

		return null;
	}

	@Override
	public void run() {
	}

}

class AuxiliarBranchAndBound {
	private Grafo grafo;

	public AuxiliarBranchAndBound(Grafo grafo) {
		super();
		this.grafo = grafo;
	}

}