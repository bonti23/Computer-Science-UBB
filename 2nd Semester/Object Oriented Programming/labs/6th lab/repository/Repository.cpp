#include "Repository.h"

void Repository::store(const Contract &contract) {
    if(exist(contract))
        throw RepositoryException("There is already a contract with this name and type!");
    all.push_back(contract);
}
bool Repository::exist(const Contract& contract)const {
    try {
        find(contract.get_name(), contract.get_type());
        return true;
    }
    catch (RepositoryException&) {
        return false;}
}
const Contract& Repository::find(std::string name, std::string type) const {
    for (const auto& contract : all) {
        if (contract.get_name() == name && contract.get_type() == type) {
            return contract;
        }
    }
    throw RepositoryException("There isn't any contract with this name and type!");
}

const vector<Contract>& Repository::getAll() const noexcept {
    return all;
}

ostream& operator<<(ostream& out, const RepositoryException& ex) {
    out << ex.messages;
    return out;
}
