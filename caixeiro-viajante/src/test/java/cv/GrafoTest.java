package cv;

import static org.junit.Assert.*;

import org.junit.Test;

public class GrafoTest {

	@Test
	public void testInsereArco() {
		// Arco a = null;
		Grafo g = new Grafo(10);
		// testa a possibilidade de inserção de arco nulo no grafo
		try {
			g.insereArco(null);
			fail("não deveria ser possível inserir arco 'null'");
		} catch (IllegalArgumentException e) {
		}

		// testa a possibilidade de inserção de arco com vertice `PARA` negativo
		try {
			g.insereArco(new Arco(-1, 0, 0));
			fail("não deveria ser possível inserir arco com vertice `DE` negativo.");
		} catch (IllegalArgumentException e) {
		}

		// testa a possibilidade de inserção de arco com vertice `PARA` negativo
		try {
			g.insereArco(new Arco(0, -1, 0));
			fail("não deveria ser possível inserir arco com vertice `PARA` negativo.");
		} catch (IllegalArgumentException e) {
		}

	}

	@Test
	public void testObterArco() {
		// TODO Implementar os testes
		// fail("Not yet implemented");
	}

}
