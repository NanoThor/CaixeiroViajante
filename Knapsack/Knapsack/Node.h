#pragma once
#include<vector>
#include<list>

struct item {
	int index;
	float value;
	float weight;
	bool included;
};

struct KnapsackState {
	std::vector<item> items;
	std::list<int> itemsToBranch;
	float capacity;
	bool feasible;
};

class Node
{

	Node* _parent;
	std::vector<Node*> _children;
	KnapsackState _state;

public:
	Node(KnapsackState state);
	~Node();

	void AddChildren(Node* child);
	Node* GetParent();
	std::vector<Node*>* GetChildren();
	KnapsackState* GetState();

	float GetTotalWeight();
	float GetTotalValue();
	void SetParent(Node* param1);
};
