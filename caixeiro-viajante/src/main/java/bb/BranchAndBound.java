package bb;

import java.util.PriorityQueue;
import java.util.Queue;

public class BranchAndBound {
    private BBNode root;
    private Problem problem;
    private Problem titular;
    private BBNode noTitular;
    private int counter;

    public void setProblem(Problem problem) {
	this.problem = problem;
    }

    private static int problemComparator(BBNode n1, BBNode n2) {
	Problem p1 = n1.getProblem();
	Problem p2 = n2.getProblem();
	return Double.compare(p2.z(), p1.z());
    }

    public void run() {
	titular = null;
	noTitular = null;
	counter = 0;

	// fila de problemas que vão gerar filhos.
	Queue<BBNode> queue = new PriorityQueue<>(
		BranchAndBound::problemComparator);

	root = new BBNode(problem, null);
	resolve(root, queue);

	while (!queue.isEmpty()) {
	    BBNode n = queue.poll();
	    Problem p = n.getProblem();
	    System.out.println(n);

	    if (titular != null && titular.z() > p.z()) {
		System.out.println("Eliminado pela Titular\n");
		continue;
	    }

	    // gera os filhos
	    for (Problem pc : p.branch()) {
		BBNode nc = new BBNode(pc, n);
		resolve(nc, queue);
	    }

	}

	System.out.println("Titular:");
	System.out.println(titular);
    }

    private void resolve(BBNode n, Queue<BBNode> queue) {
	Problem p = n.getProblem();
	boolean resolved = p.resolve();
	System.out.printf("Problem #%d\n", counter);
	System.out.println(p);
	++counter;
	if (!resolved) {
	    System.out.println("Inviável!");
	    return;
	}

	if (p.isTitular()) {
	    if (titular == null) {
		System.out.println("Nova Titular");
		titular = p;
		noTitular = n;
	    } else if (p.z() > titular.z()) {
		System.out.println("Nova Titular");
		titular = p;
		noTitular = n;
	    } else {
		System.out.println("Eliminado pela titular");
		System.out.println(p);
	    }
	} else {
	    // p é candidata a gerar filhos
	    queue.add(n);
	}
    }
}
