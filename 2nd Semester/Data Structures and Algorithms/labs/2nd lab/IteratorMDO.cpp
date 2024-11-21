#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
    keylist=d.lsikeys;
    currentKey=d.lsikeys->head;
    if(currentKey!= nullptr)
        currentValue=currentKey->values->head;
}

void IteratorMDO::prim(){
    currentKey=dict.lsikeys->head;
    if(currentKey!= nullptr)
        currentValue=currentKey->values->head;
}

void IteratorMDO::urmator(){
    if(!valid())
    {
        throw exception();
    }
    if(currentValue->urm!= nullptr)
    {
        currentValue=currentValue->urm;
    }
    else
    {
        currentKey=currentKey->urm;
        if(currentKey!= nullptr)
        {
            currentValue=currentKey->values->head;
        }
    }
}

//complexitate: teta(k)
void IteratorMDO::avanseazaKpasi(int k) {
    for (int i = 0; i < k; ++i) {
        if (!valid()) {
            throw std::runtime_error("Iteratorul nu este valid!");
        }
        if (currentValue->urm != nullptr)
            currentValue = currentValue->urm;
        else{
            if (currentKey->urm != nullptr) {
                currentKey = currentKey->urm;
                currentValue = currentKey->values->head;
            } else {
                // Am ajuns la finalul MDO-ului
                currentValue = nullptr;
                break;
            }
        }
    }
}


bool IteratorMDO::valid() const{
    return currentKey!= nullptr;
}

TElem IteratorMDO::element() const{
    if(!valid())
    {
        throw exception();
    }
    return TElem (currentKey->key,currentValue->value);
}

