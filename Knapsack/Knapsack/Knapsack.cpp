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
Node* getBetterCandidate();
void eraseBetterCandidate();


Node* _solution;
std::vector<Node*> _candidates;


int main()
{
	std::vector<item> items = {
		{ 1,45,3,false },
		{ 2,30,5,false },
		{ 3,45,9,false },
		{ 4,10,5,false },
	};
	
	KnapsackState initialState;
	initialState.items = items;
	initialState.capacity = 16;
	initialState.feasible = true;

	Node* root = new Node(initialState);
	
	execute(root);

	system("pause");

	return 0;
}

void execute(Node* root) {
	auto evalOrder = getEvalutaionIndexVector(*root->GetState());
	
	root->GetState()->itemsToBranch = evalOrder;
	root->CalculateUpperBound();
	
	
	
	branchAndBound(root);
	
	PrintTree(root,0);

	std::cout << "Best Result:\n";
	for (auto i : _solution->GetState()->items) {
		std::cout << "[" << i.included << "]";
	}

	system("pause");

}

void branchAndBound(Node* node) {
	std::cout << ">> UB:" << node->GetUpperBound() << "\n";
	KnapsackState* currentState = node->GetState();
	if (node->GetTotalWeight() <= currentState->capacity) {
		currentState->feasible = true;
		if (currentState->itemsToBranch.size() > 0) {
			std::list<int> toB = currentState->itemsToBranch;
			std::vector<item> items = currentState->items;

			if (!_candidates.empty()) {
				if (getBetterCandidate()->GetUpperBound() > node->GetUpperBound()) {
					Node* candidate = getBetterCandidate();
					eraseBetterCandidate();
					branchAndBound(candidate);
					return;
				}
			}

			if (_solution != NULL) {
				if (_solution->GetTotalValue() < node->GetTotalValue()) {
					_solution = node;
				}
			}
			else {
				_solution = node;
			}

			for (auto &i : items) {
				if (i.index == toB.front()) {
					i.included = true;
					toB.pop_front();
					break;
				}
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
			double ubc1 = c1->CalculateUpperBound();
			double ubc2 = c2->CalculateUpperBound();


			node->AddChildren(c1);
			node->AddChildren(c2);
			
			std::cout << "======\n";
			std::cout << "C1 UB:" << ubc1 << "\n";
			std::cout << "C2 UB:" << ubc2 << "\n";
			std::cout << "======\n";
			
			if (ubc1 >= ubc2) {

				if (!_candidates.empty() && ubc2 <= currentState->upper_bound) {
					//if (getBetterCandidate()->GetUpperBound() < ubc2) {
						_candidates.push_back(c2);
					//}
				}else{
					if (ubc2 <= currentState->upper_bound)
						_candidates.push_back(c2);
				}
				if (ubc1 <= currentState->upper_bound)
					branchAndBound(c1);
				if (ubc2 <= currentState->upper_bound)
					branchAndBound(c2);
			}
			else {		
				if (!_candidates.empty() && ubc1 <= currentState->upper_bound) {
					//if (getBetterCandidate()->GetUpperBound() < ubc2) {
						_candidates.push_back(c1);
					//}
				}
				else {
					if (ubc1 <= currentState->upper_bound)
						_candidates.push_back(c1);
				}

				if (ubc2 <= currentState->upper_bound)
					branchAndBound(c2);
				if (ubc1 <= currentState->upper_bound)
					branchAndBound(c1);		
			}	
		}
	}
	else
	{
		currentState->feasible = false;
	}
}

Node* getBetterCandidate() {
	double ub = 0;
	Node* candi = NULL;
	for (auto &c : _candidates) {
		if (ub < c->GetUpperBound()) {
			ub = c->GetUpperBound();
			candi = c;
		}
	}
	return candi;
}

void eraseBetterCandidate() {
	double ub = 0;
	int index = 0;

	for (int i = 0; i < _candidates.size(); i++) {
		if (ub < _candidates[i]->GetUpperBound()) {
			ub = _candidates[i]->GetUpperBound();
			index = i;
		}
	}
	_candidates.erase(_candidates.begin() + index);
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
	std::cout << "[VALUE: " << root->GetTotalWeight() << "] [WEIGHT: " << root->GetTotalValue() << "] [UPPER BOUND: "<< root->GetUpperBound() <<"\n";
	if (children.size() > 0) {
		//PrintTree(children[0], level + 1);
		PrintTree(children[1], level + 1);
	}
	
}