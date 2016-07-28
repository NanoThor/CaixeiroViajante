package kp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bb.Problem;

public class KnapsackProblem implements Problem {
    private double z;
    private double[] x;
    private double[] c;
    private double[] a;
    private double b;
    private int idxVarPart;

    public enum State {
	STATE_1, STATE_0, STATELESS
    }

    private State[] stateX;

    public KnapsackProblem(double[] x, double[] c, double[] a, double b,
	    State[] stateX) {
	super();
	this.x = x;
	this.c = c;
	this.a = a;
	this.b = b;
	this.stateX = stateX;
    }

    private int compare(int i, int j) {
	double cri = c[i] / a[i];
	double crj = c[j] / a[j];
	return Double.compare(crj, cri);
    }

    @Override
    public boolean resolve() {
	Integer[] idx = new Integer[a.length];
	for (int i = 0; i < idx.length; i++) {
	    idx[i] = i;
	}
	Arrays.sort(idx, this::compare);
	double s = 0.0;

	// variáveis já incluidas
	for (int i = 0; i < a.length; i++) {
	    if (stateX[i] == State.STATE_1)
		s += a[i];
	}

	// problema inviável
	if (s > b)
	    return false;

	// fazer o processo do guloso
	int j;
	z = 0.0;
	for (int i = 0; i < idx.length; i++) {
	    j = idx[i];
	    // evitar adicionar as variáveis que incluidas e as excluidas;
	    if (stateX[j] != State.STATELESS)
		continue;
	    if (s + a[j] > b) {
		z += c[j] * ((b - s) / a[j]);
		idxVarPart = j;
	    } else {
		x[j] = 1;
		s += a[j];
		z += c[j];
	    }
	}

	return true;
    }

    @Override
    public double z() {
	return z;
    }

    @Override
    public double[] x() {
	return x;
    }

    @Override
    public List<Problem> branch() {
	ArrayList<Problem> ps = new ArrayList<>(2);

	// quanda variavel escolhida para particionar é 0
	double[] x1 = Arrays.copyOf(x, x.length);
	x1[idxVarPart] = 0;
	State[] stateX1 = Arrays.copyOf(stateX, stateX.length);
	stateX1[idxVarPart] = State.STATE_0;
	Problem p1 = new KnapsackProblem(x1, c, a, b, stateX1);
	ps.add(p1);

	double[] x2 = Arrays.copyOf(x, x.length);
	x2[idxVarPart] = 1;
	State[] stateX2 = Arrays.copyOf(stateX, stateX.length);
	stateX2[idxVarPart] = State.STATE_1;
	Problem p2 = new KnapsackProblem(x2, c, a, b, stateX2);
	ps.add(p2);

	return ps;
    }

    @Override
    public boolean isTitular() {
	return false;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("KnapsackProblem {");
	builder.append("\n\tz: ");
	builder.append(z);
	builder.append("\n\tx: ");
	builder.append(Arrays.toString(x));
	builder.append("\n\tc: ");
	builder.append(Arrays.toString(c));
	builder.append("\n\ta: ");
	builder.append(Arrays.toString(a));
	builder.append("\n\tb: ");
	builder.append(b);
	builder.append("\n\tidxVarPart: ");
	builder.append(idxVarPart);
	builder.append("\n\tstateX: ");
	builder.append(Arrays.toString(stateX));
	builder.append("\n}");
	return builder.toString();
    }

}
