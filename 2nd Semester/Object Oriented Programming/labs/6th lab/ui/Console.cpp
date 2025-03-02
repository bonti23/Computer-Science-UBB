#include "Console.h"
#include "Validator.h"

void Console::print(const vector<Contract>& contracts) {
    cout<<"Contracts:\n";
    for (const auto& contract : contracts) {
        cout<<"Name: "<<contract.get_name()<<", hours: "<<contract.get_hours()<<", type: "<<contract.get_type()<<", professor: "<<contract.get_professor()<<endl;
    }
    cout<<"Done printing!\n";
}

void Console::add() {
    string name, type, professor;
    int hours;
    cout<<"Name: ";
    cin>>name;
    cout<<"Number of hours: ";
    cin>>hours;
    cout<<"Type: ";
    cin>>type;
    cout<<"Professor: ";
    cin>>professor;
    service.addContract(name, hours, type, professor);
    cout<<"Added successfully!\n";
}

void Console::start() {
    while (true) {
        cout<<"Menu:\n";
        cout<<"1. Add contract\n";
        cout<<"2. Print contracts\n";
        cout<<"3. Sort by name\n";
        cout<<"4. Sort by professor\n";
        cout<<"5. Sort by type and hours\n";
        cout<<"6. Filter by professor\n";
        cout<<"7. Filter by hours\n";
        cout<<"0. Exit\n";
        cout<<"*******************************\n";
        cout<<"Instruction: ";
        int cmd;
        string prof;
        int hours;
        cin>>cmd;
        try {
            switch (cmd) {
                case 1:
                    add();
                    break;
                case 2:
                    print(service.getAll());
                    break;
                case 3:
                    print(service.sortByName());
                    break;
                case 4:
                    print(service.sortByProfessor());
                    break;
                case 5:
                    print(service.sortByTypeHours());
                    break;
                case 6:
                    cout<<"Professor: ";
                    cin>>prof;
                    print(service.filterProfessor(prof));
                    break;
                case 7:
                    cout << "Number of hours: ";
                    char h[101];
                    cin>>h;
                    h[strlen(h)] = '\0';
                    hours = atoi(h);
                    if (hours != 0)
                        print(service.filterHours(hours));

                    break;
                case 0:
                    return;
                default:
                    cout << "Comanda invalida!\n";
            }
        }
        catch (const RepositoryException& ex) {
            cout << ex << '\n';
        }
        catch (const ValidateException& ex) {
            cout << ex << '\n';
        }
    }
}
