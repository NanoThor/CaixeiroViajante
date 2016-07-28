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

std::vector<Node*>* Node::GetChildren()
{
	return &_children;
}

Node* Node::GetParent()
{
	return _parent;
}
