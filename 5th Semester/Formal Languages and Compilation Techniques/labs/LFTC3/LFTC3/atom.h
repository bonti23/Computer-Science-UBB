#pragma once
#include <string>
#include <vector>
#include <regex>
#include <algorithm>

using namespace std;

class Atom {
	string text;
	const regex id = regex("^[a-zA-Z_][a-zA-Z0-9_]{0,7}$");
	const regex CONST = regex("^-?[0-9]+(\\.[0-9]+)?([eE][+-]?[0-9]+)?$");
	const vector<string> operatori = { "<", ">", "<=", ">=", "+", "-", "/", "*", "%", ">>", "<<", "==", "!=", "="};
	const vector<string> separatori = { ".", ",", ";", "{", "}", "(", ")" };
	const vector<string> cuv_cheie = { "#include", "<iostream>", "<cmath>", "<cstring>", "using", "namespace", "std", "int", "main", "double", "struct", "cin", "cout", "if", "else", "while" };
public:
	explicit Atom(string t): text(move(t)){}
	
	bool is_id() const{
		return regex_match(text, id);
	}
	
	bool is_const() const {
		return regex_match(text, CONST);
	}

	bool is_operator() const {
		return find(operatori.begin(), operatori.end(), text) != operatori.end();
	}

	bool is_separator() const{
		return find(separatori.begin(), separatori.end(), text) != separatori.end();
	}

	bool is_cuv_cheie() const {
		return find(cuv_cheie.begin(), cuv_cheie.end(), text) != cuv_cheie.end();
	}

	const string& getText() const { 
		return text; 
	}
};
