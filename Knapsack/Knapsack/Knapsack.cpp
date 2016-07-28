// Knapsack.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "Node.h"
#include <iostream>
#include <algorithm>

void execute(Node* root);
std::list<int> getEvalutaionIndexVector(KnapsackState &state);
void branchAndBound(Node* node);
void PrintTree(Node* root, int level = 0);

Node* _candidate;

int main()
{
	std::vector<item> items = {
		{ 1,10,2,false },
		{ 2,7,1,false },
		{ 3,25,6,false },
		{ 4,24,5,false },
	};
	
	KnapsackState initialState;
	initialState.items = items;
	initialState.capacity = 7;
	initialState.feasible = true;

	Node* root = new Node(initialState);
	
	execute(root);

	system("pause");

	return 0;
}

void execute(Node* root) {
	auto evalOrder = getEvalutaionIndexVector(*root->GetState());
	
	root->GetState()->itemsToBranch = evalOrder;

	branchAndBound(root);
	
	PrintTree(root,0);

	std::cout << "Best Result:\n";
	for (auto i : _candidate->GetState()->items) {
		std::cout << "[" << i.included << "]";
	}

	system("pause");

}

void branchAndBound(Node* node) {
	KnapsackState* currentState = node->GetState();
	if (node->GetTotalWeight() <= currentState->capacity) {
		currentState->feasible = true;
		if (currentState->itemsToBranch.size() > 0) {
			std::list<int> toB = currentState->itemsToBranch;
			std::vector<item> items = currentState->items;

			if (_candidate != NULL) {
				if (_candidate->GetTotalValue() < node->GetTotalValue()) {
					_candidate = node;
				}
			}
			else {
				_candidate = node;
			}

			int count = 0;
			for (auto &i : items) {
				if (i.index == toB.front()) {
					i.included = true;
					toB.pop_front();
					break;
				}
				++count;
			}

			KnapsackState childState1 = {
				items,
				toB,
				currentState->capacity,
				false
			};
			KnapsackState childState2 = {
				currentState->items,
				toB,
				currentState->capacity,
				false
			};
			
			Node* c1 = new Node(childState1);
			Node* c2 = new Node(childState2);

			node->AddChildren(c1);
			node->AddChildren(c2);

			branchAndBound(c1);
			branchAndBound(c2);
		}
	}
	else
	{
		currentState->feasible = false;
	}
}


std::list<int> getEvalutaionIndexVector(KnapsackState &state) {
	auto evaluationItems = state.items;
	std::list<int> ret;

	std::sort(
		evaluationItems.begin(),
		evaluationItems.end(),
		[](item a, item b) {

			float rate1 = a.value / a.weight;
			float rate2 = b.value / b.weight;

			return rate1 > rate2;
		}
	);

	for (auto i : evaluationItems) {
		ret.push_back(i.index);
	}

	return ret;

}

void PrintTree(Node* root, int level) {
	auto children = *root->GetChildren();
	if (children.size() > 0) {
		PrintTree(children[0], level + 1);
		//PrintTree(children[1], level + 1);
	}

	auto state = root->GetState();
	for (auto i : root->GetState()->items) {
		for (int tab = 0; tab < level; tab++) {
			std::cout << "\t-";
		}
		
		if (state->feasible) {
			if (i.included) {
				std::cout << "F:" << level << "[" << i.index << "]";
			}
			else {
				std::cout << "F:" << level << "<" << i.index << ">";
			}
		}
		else {
			std::cout << "N:" << level << "<" << i.index << ">";
		}
		std::cout << "\n";	
	}
	std::cout << "TOTAL: " << root->GetTotalWeight() << ":" << root->GetTotalValue() << "\n";
	if (children.size() > 0) {
		//PrintTree(children[0], level + 1);
		PrintTree(children[1], level + 1);
	}
	
}