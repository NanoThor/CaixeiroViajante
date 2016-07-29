package bb;

import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Queue;

public class BranchAndBound {
    PrintStream out = System.out;
    private BBNode root;
    private Problem problem;
    private Problem titular;
    private BBNode noTitular;
    private int numSubProblems = 0;

    public void setOut(PrintStream out) {
	this.out = out;
    }

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
	// counter = 0;

	// fila de problemas que vão gerar filhos.
	Queue<BBNode> queue = new PriorityQueue<>(
		BranchAndBound::problemComparator);

	root = new BBNode(problem, null, numSubProblems++);
	resolve(root, queue);

	while (!queue.isEmpty()) {
	    BBNode n = queue.poll();
	    Problem p = n.getProblem();
	    // out.println(n);

	    if (titular != null && titular.z() > p.z()) {
		// out.println("Eliminado pela Titular\n");
		continue;
	    }

	    // gera os filhos
	    for (Problem pc : p.branch()) {
		BBNode nc = new BBNode(pc, n, numSubProblems++);
		resolve(nc, queue);
	    }

	}

	// out.println("Titular:");
	// out.println(titular);
    }

    private void resolve(BBNode n, Queue<BBNode> queue) {
	Problem p = n.getProblem();
	boolean resolved = p.resolve();
	// out.printf("Problem #%d\n", counter);
	// out.println(p);
	// ++counter;
	if (!resolved) {
	    // out.println("Inviável!");
	    return;
	}

	if (p.isTitular()) {
	    if (titular == null) {
		// out.println("Nova Titular");
		titular = p;
		noTitular = n;
	    } else if (p.z() > titular.z()) {
		// out.println("Nova Titular");
		titular = p;
		noTitular = n;
	    } else {
		// out.println("Eliminado pela titular");
		// out.println(p);
	    }
	} else {
	    // p é candidata a gerar filhos
	    queue.add(n);
	}
    }

    public int getNumSubProblems() {
	return numSubProblems;
    }

    public BBNode getNoTitular() {
	return noTitular;
    }
}
