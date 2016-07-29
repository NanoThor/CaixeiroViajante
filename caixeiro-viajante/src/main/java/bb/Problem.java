package bb;

import java.util.List;

public interface Problem {
    /**
     * Resolve o problema
     * 
     * @return
     */
    public boolean resolve();

    /**
     * Determina se o problema já foi resolvido.
     * 
     * @return true se o problema foi resolvido, false, caso contrario.
     */
    public boolean isSolved();

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
