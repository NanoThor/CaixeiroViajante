package bb;

import java.util.LinkedList;
import java.util.Queue;

public class BranchAndBound {
    Problem root;
    Problem problem;

    public void setProblem(Problem problem) {
	this.problem = problem;
    }

    public void run() {
	Problem titular = null;
	Queue<Problem> queue = new LinkedList<>();
	queue.add(problem);
	
	while (!queue.isEmpty()) {
	    Problem p = queue.poll();
	    boolean resolved = p.resolve();

	    // problema inviável;
	    if (!resolved)
		continue;

	    // eliminado pela titular
	    if (titular != null && titular.z() <= p.z()) {
		continue;
	    }

	    // novo titular
	    if (p.isTitular()) {
		titular = p;
		continue;
	    }

	    // gera os filhos
	    queue.addAll(p.branch());
	}
	
	System.out.println(titular);
    }
}
