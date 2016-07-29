package bb;

import java.util.ArrayList;
import java.util.List;

public class BBNode {
    private BBNode parent;
    private List<BBNode> children;
    private Problem problem;
    private int id;

    public BBNode(Problem p, BBNode parent, int id) {
	setParent(parent);
	problem = p;
	children = new ArrayList<>();
	this.id = id;
    }

    // seta o pai e o coloca this na lista de filhos do no pai
    public void setParent(BBNode parent) {
	if (parent != null)
	    parent.addChild(this);
	this.parent = parent;
    }

    private void addChild(BBNode node) {
	if (node != null)
	    children.add(node);
    }

    public BBNode getParent() {
	return parent;
    }

    public List<BBNode> getChildren() {
	return children;
    }

    public Problem getProblem() {
	return problem;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Nó #");
	sb.append(id);
	sb.append('\n');
	sb.append("Titular: ");
	sb.append(problem.isTitular());
	sb.append('\n');
	sb.append("Viável: ");
	sb.append(problem.isSolved());
	sb.append('\n');
	sb.append("Problema: ");
	sb.append(problem);
	return sb.toString();
    }
}
