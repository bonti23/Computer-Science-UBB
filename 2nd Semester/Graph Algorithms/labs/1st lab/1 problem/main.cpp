#include <iostream>

using namespace std;

int main() {
    int a[100][100]={0}, n;

    //citire
    int i, j;
    cin>>n;
    for(int k=0; k<n; k++){
        cin>>i>>j;
        a[i-1][j-1]=1;
        a[j-1][i-1]=1;
    }

    //matrice adiacenta
    for (int i=0; i<n; i++){
        for(int j=0; j<n; j++)
            cout<<a[i][j]<<" ";
        cout<<endl;
    }

    //lista de adiacenta
    for (int i=0; i<n; i++){
        cout<<i+1<<" are varfurile: ";
        for(int j=0; j<n; j++)
            if (a[i][j]!=0)
                cout<<j+1<<" ";
        cout<<endl;
    }
    fin.close();
    return 0;
}
