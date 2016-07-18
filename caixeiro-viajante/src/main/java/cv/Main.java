package cv;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new Grafo(10);
		System.out.println(grafo);
		grafo.insereArco(new Arco(1, 5, 10.0));
		grafo.insereArco(new Arco(1, 4, 12.04));
		System.out.println(grafo);
		grafo.insereArco(new Arco(5, 4, -23.7));
		System.out.println(grafo);
	}

}
