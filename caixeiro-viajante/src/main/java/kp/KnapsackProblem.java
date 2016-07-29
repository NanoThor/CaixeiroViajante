package kp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bb.Problem;

public class KnapsackProblem implements Problem {
    private double z;
    private double s;
    private double[] x;
    private double[] c;
    private double[] a;
    private double b;
    private int idxVarPart;

    public enum State {
	STATE_1, STATE_0, STATELESS
    }

    private State[] stateX;
    private boolean solved;

    public KnapsackProblem(double[] c, double[] a, double b, State[] stateX) {
	super();
	this.x = new double[a.length];
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
	s = 0.0;
	z = 0.0;

	// ordenação dos custos relativos
	Integer[] idx = new Integer[a.length];
	for (int i = 0; i < idx.length; i++) {
	    idx[i] = i;
	}
	Arrays.sort(idx, this::compare);

	// variáveis já incluidas
	for (int i = 0; i < a.length; i++) {
	    if (stateX[i] == State.STATE_1) {
		s += a[i];
		z += c[i];
		x[i] = 1;

	    } else if (stateX[i] == State.STATE_0) {
		x[i] = 0;
	    }
	}

	// problema inviável
	if (s > b) {
	    solved = false;
	    return solved;
	}

	// fazer o processo do guloso
	int j;
	idxVarPart = -1;
	for (int i = 0; i < idx.length; i++) {
	    j = idx[i];
	    // evitar adicionar as variáveis que incluidas e as excluidas;
	    if (stateX[j] != State.STATELESS)
		continue;
	    if (s + a[j] > b) {
		x[j] = ((b - s) / a[j]);
		z += c[j] * x[j];

		if (x[j] == 0.0) {
		    idxVarPart = -1;
		} else {
		    idxVarPart = j;
		}

		break;
	    } else {
		x[j] = 1;
		s += a[j] * x[j];
		z += c[j];
	    }
	}

	solved = true;
	return solved;
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
	State[] stateX1 = Arrays.copyOf(stateX, stateX.length);
	stateX1[idxVarPart] = State.STATE_0;
	Problem p1 = new KnapsackProblem(c, a, b, stateX1);
	ps.add(p1);

	State[] stateX2 = Arrays.copyOf(stateX, stateX.length);
	stateX2[idxVarPart] = State.STATE_1;
	Problem p2 = new KnapsackProblem(c, a, b, stateX2);
	ps.add(p2);

	return ps;
    }

    @Override
    public boolean isTitular() {
	return idxVarPart == -1;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("KnapsackProblem {");
	builder.append("\n\tz: ");
	builder.append(z);
	builder.append("\n\ts: ");
	builder.append(s);
	builder.append("\n\tb: ");
	builder.append(b);
	builder.append("\n\tx: ");
	builder.append(Arrays.toString(x));
	builder.append("\n\tc: ");
	builder.append(Arrays.toString(c));
	builder.append("\n\ta: ");
	builder.append(Arrays.toString(a));
	builder.append("\n\tidxVarPart: ");
	builder.append(idxVarPart);
	builder.append("\n\tstateX: ");
	builder.append(Arrays.toString(stateX));
	builder.append("\n}");
	return builder.toString();
    }

    @Override
    public boolean isSolved() {
	return solved;
    }

}
