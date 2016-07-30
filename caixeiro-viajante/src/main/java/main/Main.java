package main;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

import bb.BranchAndBound;
import kp.KnapsackProblem;
import kp.KnapsackProblem.State;

public class Main {
    public static void main(String[] args) throws Exception {
	final int quantidadeItemMochila = 10;
	final int numeroTestes = 10;
	final int intervalo = 100;

	Random random = new Random();
	int[][] subproblemas = new int[quantidadeItemMochila][numeroTestes];
	long[][] tempos = new long[quantidadeItemMochila][numeroTestes];

	for (int i = 0; i < quantidadeItemMochila; ++i) {
	    final int ii = i + 1;
	    int size = ii * intervalo;
	    System.out.println("Para size=" + size);

	    for (int j = 0; j < numeroTestes; ++j) {

		double[] c = new double[size];
		double[] a = new double[size];
		double b = 0.0;
		State[] stateX = new State[size];

		System.out.println("Gerando...");
		for (int k = 0; k < size; ++k) {
		    c[k] = random.nextInt(100) + random.nextDouble();
		    a[k] = random.nextInt(100) + random.nextDouble();
		    b += a[k];
		    stateX[k] = State.STATELESS;
		}
		b /= 2;

		System.out.println("Resolvendo...");

		KnapsackProblem problem = new KnapsackProblem(c, a, b, stateX);
		BranchAndBound bnb = new BranchAndBound();
		bnb.setProblem(problem);
		long time = System.currentTimeMillis();
		bnb.run();
		time = System.currentTimeMillis() - time;
		subproblemas[i][j] = bnb.getNumSubProblems();
		tempos[i][j] = time;
	    }
	}
	long version = System.currentTimeMillis();
	PrintStream resultadosTempos = new PrintStream(
		"tempos-" + version + ".csv");
	resultadosTempos.println("sep=;");
	PrintStream resultadosSubProblemas = new PrintStream(
		"subproblemas-" + version + ".csv");
	resultadosSubProblemas.println("sep=;");

	for (int j = 1; j <= quantidadeItemMochila; ++j) {
	    resultadosTempos.printf("%d;", intervalo * j);
	    resultadosSubProblemas.printf("%d;", intervalo * j);
	}
	resultadosTempos.println();
	resultadosSubProblemas.println();

	for (int j = 0; j < numeroTestes; ++j) {
	    for (int i = 0; i < quantidadeItemMochila; i++) {
		resultadosTempos.printf("%d;", tempos[i][j]);
		resultadosSubProblemas.printf("%d;", subproblemas[i][j]);
	    }
	    resultadosTempos.println();
	    resultadosSubProblemas.println();
	}

	System.out.println(Arrays.deepToString(subproblemas));
	System.out.println(Arrays.deepToString(tempos));

	resultadosTempos.close();
	resultadosSubProblemas.close();
    }
}
