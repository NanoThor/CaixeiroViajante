package cv;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
	private List<List<Arco>> grafo;
	private int numeroDeVertices;

	public Grafo(int numeroDeVertices) {
		if (numeroDeVertices < 0)
			throw new IllegalArgumentException("o número de vertices deve ser não-negativo");
		this.numeroDeVertices = numeroDeVertices;

		// alocar espaço para os vertices
		grafo = new ArrayList<>(numeroDeVertices);
		for (int i = 0; i < numeroDeVertices; ++i) {
			grafo.add(new ArrayList<>());
		}
	}

	public void adicionarVertice() {
		++numeroDeVertices;
		grafo.add(new ArrayList<>());
	}

	public void insereArco(Arco arco) {
		if (arco == null)
			throw new IllegalArgumentException("o arco não deve ser null");

		int de = arco.getVerticeDe();
		int para = arco.getVerticePara();
		if (checarVertice(de))
			throw new IllegalArgumentException(
					"não é possível inserir arco, vertice `DE` não pertence ao conjunto de vertices.");
		if (checarVertice(para))
			throw new IllegalArgumentException(
					"não é possível inserir arco, vertice `PARA` não pertence ao conjunto de vertices.");

		// insere o arco no grafo
		grafo.get(de).add(arco);
	}

	/**
	 * 
	 * @param de
	 *            vertice de onde o arco parte
	 * @param para
	 *            vertice para onde o arco parte
	 * @return arco, se arco pertence ao Grafo, null, c.c.
	 */
	public Arco obterArco(int de, int para) {
		if (checarVertice(de))
			// TODO adicionar uma boa mensagem de erro
			throw new IllegalArgumentException("");
		if (checarVertice(para))
			// TODO adicionar uma boa mensagem de erro
			throw new IllegalArgumentException("");
		Arco arco = null;

		for (Arco a : grafo.get(de)) {
			if (de == a.getVerticeDe() && para == a.getVerticePara()) {
				arco = a;
				break;
			}
		}

		return arco;
	}

	private boolean checarVertice(int vertice) {
		return (vertice < 0 || vertice >= numeroDeVertices);
	}

	@Override
	public synchronized String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Grafo:\n");
		grafo.stream().forEach(e -> {
			e.stream().forEach(arco -> {
				builder.append(arco).append('\n');
			});
		});
		builder.append('\n');
		return builder.toString();
	}

	public int getNumeroDeVertices() {
		return numeroDeVertices;
	}
}
