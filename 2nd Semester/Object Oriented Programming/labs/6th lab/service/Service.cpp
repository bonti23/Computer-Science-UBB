#include "Service.h"
#include <functional>
#include <algorithm>

vector<Contract> Service::sortare(bool(*smaller)(const Contract&, const Contract&)) {
    vector<Contract> v = repo.getAll(); // Fac o copie a vectorului

    for (size_t i = 0; i < v.size(); i++) {
        for (size_t j = i + 1; j < v.size(); j++) {
            if (!smaller(v[i], v[j])) { // Folosim corect funcția primită ca parametru
                std::swap(v[i], v[j]); // Interchimbare mai eficientă
            }
        }
    }
    return v;
}

void Service::addContract(const string& name, int hours, const string& type, const string& professor) {
    Contract c{name, hours, type, professor};
    validator.validate(c);
    repo.store(c);
}

vector<Contract> Service::sortByName() {
    auto copyAll = repo.getAll();
    std::sort(copyAll.begin(), copyAll.end(),
              [](const Contract& c1, const Contract& c2) {
                  return c1.get_name() < c2.get_name();
              });
    return copyAll;
}

vector<Contract> Service::sortByProfessor() {
    auto copyAll = repo.getAll();
    std::sort(copyAll.begin(), copyAll.end(),
              [](const Contract& c1, const Contract& c2) {
                  return c1.get_professor() < c2.get_professor();
              });
    return copyAll;
}

vector<Contract> Service::sortByTypeHours() {
    auto copyAll = repo.getAll();
    std::sort(copyAll.begin(), copyAll.end(),
              [](const Contract& c1, const Contract& c2) {
                  if (c1.get_type() == c2.get_type()) {
                      return c1.get_hours() < c2.get_hours();
                  }
                  return c1.get_type() < c2.get_type();
              });
    return copyAll;
}

vector<Contract> Service::filterProfessor(const string& professor) {
    return filtreaza([professor](const Contract& c) {
        return c.get_professor() == professor;
    });
}

vector<Contract> Service::filterHours(int hours) noexcept {
    return filtreaza([hours](const Contract& c) {
        return c.get_hours() == hours;
    });
}

vector<Contract> Service::filtreaza(function<bool(const Contract&)> fct) {
    vector<Contract> rez;
    for (const auto& contract : repo.getAll()) {
        if (fct(contract)) {
            rez.push_back(contract);
        }
    }
    return rez;
}
