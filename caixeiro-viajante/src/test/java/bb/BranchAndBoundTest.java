package bb;

import static org.junit.Assert.*;

import org.junit.Test;

import kp.KnapsackProblem;
import kp.KnapsackProblem.State;

public class BranchAndBoundTest {

    @Test
    public void testRun() {
	double[] c = new double[] { 10.0, 7.0, 25.0, 24.0 };
	double[] a = new double[] { 2.0, 1.0, 6.0, 5.0 };
	double b = 7.0;

	State[] stateX = new State[] { State.STATELESS, State.STATELESS,
		State.STATELESS, State.STATELESS, };
	KnapsackProblem problem = new KnapsackProblem(c, a, b, stateX);
	System.out.println(problem);
	BranchAndBound bnb = new BranchAndBound();
	bnb.setProblem(problem);
	bnb.run();
    }

}
