#ifndef LABORATOR_6_OOP_CONTRACT_H
#define LABORATOR_6_OOP_CONTRACT_H

#include <iostream>
#include <string>

using namespace std;

class Contract{
private:
    string name;
    int hours;
    string type;
    string professor;
public:
    Contract(string name, int hours, string type, string professor):
        name(name),
        hours(hours),
        type(type),
        professor(professor){}

    //copy constructor
    Contract(const Contract &contract):
        name(contract.name),
        hours(contract.hours),
        type(contract.type),
        professor(contract.professor){}

    string get_name() const{
        return name;
    }

    int get_hours() const{
        return hours;
    }

    string get_type() const{
        return type;
    }

    string get_professor() const{
        return professor;
    }

    void set_name(const string &new_name){
        name=new_name;
    }

    void set_hours(const int new_hours){
        hours=new_hours;
    }

    void set_type(const string &new_type){
        type=new_type;
    }

    void set_professor(const string &new_professor){
        professor=new_professor;
    }

};
#endif //LABORATOR_6_OOP_CONTRACT_H
