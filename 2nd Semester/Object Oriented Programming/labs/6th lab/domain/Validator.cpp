#include "Validator.h"
#include "Contract.h"
#include <vector>
#include <string>
#include <iostream>

void Validator::validate(const Contract &contract) {
    vector<string> messages;

    if (contract.get_name().empty())
        messages.push_back("Invalid name! ");
    if (contract.get_hours() == 0)
        messages.push_back("Invalid number of hours! ");
    if (contract.get_type().empty())
        messages.push_back("Invalid type! ");
    if (contract.get_professor().empty())
        messages.push_back("Invalid professor!");

    if (!messages.empty())
        throw ValidateException(messages);
}

ostream& operator<<(ostream& out, const ValidateException& exception) {
    for (const auto& msg : exception.messages) {
        out << msg << " ";
    }
    return out;
}
