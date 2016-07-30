package main;

import java.util.Scanner;

import bb.BranchAndBound;
import kp.KnapsackProblem;
import kp.KnapsackProblem.State;

public class Main2 {

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	System.out.print("Digite a capacidade máxima da mochila: ");
	int capacidade = scanner.nextInt();
	System.out.print("Digite a capacidade a quantidade de itens: ");
	int quantidade = scanner.nextInt();

	double[] peso = new double[quantidade];
	double[] valor = new double[quantidade];
	State[] stateX = new State[quantidade];
	for (int i = 0; i < quantidade; i++) {
	    System.out.printf("Digite o peso do item %d: ", i + 1);
	    peso[i] = scanner.nextDouble();
	    System.out.printf("Digite o valor do item %d: ", i + 1);
	    valor[i] = scanner.nextDouble();
	    stateX[i] = State.STATELESS;
	}
	scanner.close();

	KnapsackProblem p = new KnapsackProblem(valor, peso, capacidade,
		stateX);
	BranchAndBound bb = new BranchAndBound();
	bb.setProblem(p);
	bb.run();

	System.out.println(bb.getNoTitular().toString());
    }

}
