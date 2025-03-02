#ifndef LABORATOR_6_OOP_VALIDATOR_H
#define LABORATOR_6_OOP_VALIDATOR_H

#include <iostream>
#include <string>
#include <vector>
#include "Contract.h"

using namespace std;

class ValidateException {
private:
    vector<string> messages;
public:
    ValidateException(const vector<string>& messages) : messages(messages) {}

    friend ostream& operator<<(ostream& out, const ValidateException& exception);
};
ostream& operator<<(ostream& out, const ValidateException& ex);

class Validator {
public:
    void validate(const Contract &contract);
};

#endif //LABORATOR_6_OOP_VALIDATOR_H
