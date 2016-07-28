package kp;

import static org.junit.Assert.*;

import org.junit.Test;

import kp.KnapsackProblem.State;

public class KnapsackProblemTest {

    @Test
    public void testKnapsackProblem() {
	{
	    double[] x = new double[] { 0.0, 0.0, 0.0, 0.0, };
	    double[] c = new double[] { 10.0, 7.0, 25.0, 24.0 };
	    double[] a = new double[] { 2.0, 1.0, 6.0, 5.0 };
	    double b = 7.0;
	    State[] stateX = new State[] { State.STATELESS, State.STATELESS,
		    State.STATELESS, State.STATELESS, };
	    KnapsackProblem problem = new KnapsackProblem(x, c, a, b, stateX);
	    boolean resolve = problem.resolve();
	    if (!resolve)
		fail("não conseguiu resolver");
	    System.out.println(problem);
	}

    }

}
