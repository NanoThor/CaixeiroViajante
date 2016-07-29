#include "Node.h"

Node::Node(KnapsackState state)
{
	this->_state = state;
}

Node::~Node()
{
}

void Node::AddChildren(Node* child)
{
	child->SetParent(this);
	_children.push_back(child);

}

KnapsackState* Node::GetState()
{
	return &_state;
}

float Node::GetTotalWeight()
{
	float weight = 0;
	for (auto i : _state.items) {
		if(i.included)
			weight += i.weight;
	}
	return weight;
}

float Node::GetTotalValue()
{
	float value = 0;
	for (auto i : _state.items) {
		if (i.included)
			value += i.value;
	}
	return value;
}

void Node::SetParent(Node* param1)
{
	_parent = param1;
}

double Node::CalculateUpperBound()
{
	double ub = 0;
	double totalweight = 0;
	for (auto &i : _state.items) {
		if (i.included) {
			ub += i.value;
			totalweight += i.weight;
			i.fraction = 1;
		}
	}
	for (auto &i : _state.itemsToBranch) {
		item* it = GetItemById(i);
		double fraction = 0;
		if (totalweight + it->weight <= _state.capacity) {
			ub += it->value;
			totalweight += it->weight;
			it->fraction = 1;
		}
		else {
			fraction = ((_state.capacity - totalweight)/ it->weight);
			ub += it->value * fraction;
			totalweight += it->weight * fraction;
			it->fraction = fraction;
		}
	}
	_state.upper_bound = ub;
	return ub;
}

double Node::GetUpperBound()
{
	return _state.upper_bound;
}

item* Node::GetItemById(int id)
{
	for (auto i : _state.items) {
		if (id == i.index) {
			return &i;
		}
	}
}

std::vector<Node*>* Node::GetChildren()
{
	return &_children;
}

Node* Node::GetParent()
{
	return _parent;
}
