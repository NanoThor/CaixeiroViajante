package bb;

import java.util.List;

public interface Problem {
    /**
     * Resolve o problema
     * @return 
     */
    public boolean resolve();

    /**
     * @return o valor da solução do problema.
     */
    public double z();

    /**
     * 
     * @return o vetor X contendo os valores de x.
     */
    public double[] x();

    public List<Problem> branch();

    public boolean isTitular();
}
