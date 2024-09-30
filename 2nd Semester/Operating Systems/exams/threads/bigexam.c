Last login: Thu Jun 27 07:29:46 on ttys000
alexandrabontidean@Air-Alexandra ~ % ssh ex3086@172.30.0.9
kex_exchange_identification: Connection closed by remote host
Connection closed by 172.30.0.9 port 22
alexandrabontidean@Air-Alexandra ~ % ssh ex3086@172.30.0.9
ex3086@172.30.0.9's password: 
-bash: warning: setlocale: LC_CTYPE: cannot change locale (UTF-8): No such file or directory
/home/xm/exam/os/account/alexandra.bontidean/ex3086> ls
exam-ew01.txt  exam-ew04.txt  exam-ew07.txt  exam-ew10.txt  exam-ew13.txt  exam-ew16.txt  exam-ew19.txt
exam-ew02.txt  exam-ew05.txt  exam-ew08.txt  exam-ew11.txt  exam-ew14.txt  exam-ew17.txt  exam-ew20.txt
exam-ew03.txt  exam-ew06.txt  exam-ew09.txt  exam-ew12.txt  exam-ew15.txt  exam-ew18.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew01.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew01.txt
RO: Daţi trei expresii regulare care acceptă orice linie care conţine cel puţin două vocale dar nicio cifră.
EN: Give three regular expressions that match any line that contains a least two vowels but no digits.

Answer:
grep -E "[A-Za-z]+[AEIOUaeiou][A-Za-z]+[AEIOUaeiou][A-Za-z]+" numefisier.txt | grep -v "[^0-9]"
grep -E -i "[a-z]+[aeiou][a-z]+[aeiou][a-z]+" numefisier.txt | grep -v "[^0-9]"
grep "[A-Za-z]+[AEIOUaeiou][A-Za-z]+[AEIOUaeiou][A-Za-z]+" numefisier.txt | grep -v "[^0-9]"
egrep e ca si grep -E, doar am schimba prima comanda
-v ce nu contine
-E = extended
-i case insensitive

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew02.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew02.txt
RO: Daţi o comandă GREP care afişează toate liniile dintr-un fişier care conţin un număr par de vocale (pe lângă alte eventuale caractere).
EN: Give a GREP command that display all the lines in a file that contain an even number of vowels (among other potential characters).

Answer:
grep -c "[AEIOUaeiou]{2,}" numefisier.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew03.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew04.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew04.txt
RO: Scrieţi o comandă AWK care afişează suma câmpurilor de pe poziţia egală cu numărul liniei curente.
EN: Write an AWK command that displays the sum of the fields on the position equal to the current line number.

Answer:

awk 'BEGIN{sum=0}{sum=sum+$NR}END{print sum}' numefisier.txt
NR=numarul liniei curente
$NR argumentul de pe pozitia=linia curenta
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew05.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew06.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew06.txt
RO: Scrieţi un script Shell UNIX care cere utilizatorului un nume director şi insistă până când primeşte un director care nu există deja.
EN: Write a UNIX Shell script that asks the user for a directory name and insists until it gets a directory that does not exist already.

Answer:
#!/bin/bash

read numedirector

if [-d numedirector];then
	read numedirector
	while [-d numedirector]; do
		read numedirector
	done
fi
echo "program realizat cu succes"

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew07.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew07.txt
RO: Desenati ierarhia proceselor create de codul de mai jos, incluzand procesul parinte.
EN: Draw the hierarchy of processes created by the code below, including the parent process.

    for(i=0; i<2; i++) {
        fork();
        execl("/etc", "/etc", NULL);
    }

Answer:
PARINTE
|__CHILD1
pentru ca dupa ce se va intra in execl, programul nu se va mai executa => vor fi doar 2 procese!
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew08.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew08.txt
RO: Adăugaţi liniile de cod C necesare pentru ca instrucţiunea de mai jos să suprascrie conţinutul unui fişier b.txt. Nu modificaţi instrucţiunea.
EN: Add the necessary lines of C code so that the instruction below overwrites the content of a file b.txt. Do not change the instruction.

    execlp("sort", "sort", "a.txt", NULL);

Answer:

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew09.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew09.txt
RO: De ce nu e recomandat sa comunicaţi bidirecţional printr-un singur pipe?
EN: Why is it not advisable to communicate bidirectionally through a single pipe?

Answer:
Comunicarea bidirectionala duce la deadlock!
la citire ar fi cand nu exista date disponibile
la scriere cand pipe este plin

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew10.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew11.txt
RO: Când aţi folosi un process în locul unui thread?
EN: When would you prefer using a process instead of a thread?

Answer:

/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew10.txt
RO: Ce va afişa secvenţa de comenzi de mai jos, considerând că f se creează ccu succes?
EN: What will display the sequence of commands below, considering the f is created successfully?

mkfifo f
echo asdf > f
cat f

Answer:
asdf
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew11.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew11.txt
RO: Când aţi folosi un process în locul unui thread?
EN: When would you prefer using a process instead of a thread?

Answer:
cand doresc izolare si stabilitate. executia proceselor se realizeaza pe multiple masini, la threaduri so inchide un thread
si deschide altul.
deci cand doresc executie multipla in acelasi timp.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew12.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew13.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew14.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew15.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew15.txt
RO: Care e efectul apelului la sem_wait pentru un semafor cu valoarea zero?
EN: What is the effect of calling sem_wait on a semaphore with value zero?

Answer:
sem_wait decrementeaza semaforul. daca acesta este 0, atunci se va trece de la starea de executie in wait.

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew16.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew16.txt
RO: Cum puteţi decrementa valoarea unui semafor?
EN: How can you decrement the value of a POSIX semaphore?

Answer:
decrementarea unui semafor se realizeaza prin metoda P, care solicita acces la resurse.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew17.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew17.txt
RO: Ce puteţi face ca programator pentru a preveni deadlock-urile? Justificaţi răspunsul.
EN: What can you do as a software developer to prevent deadlocks? Justify your answer.

Answer:
blocarea resurselor in aceeasi ordine pentru prevenirea ciclurilor intre procese.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew18.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew18.txt
RO: Prin ce tranziţie de stare va trece un process când scrie într-un fişier?
EN: What state transition will a process undergo when writing to a file?

Answer:
trece din starea run in wait = asteapta niste resurse
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew19.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew19.txt
RO: Ce conţinue superblocul unui disc Linux?
EN: What is the content of the superblock on a Linux disk?

Answer:
superblocul este blocul 1 care contine:
- numarul de n inoduri
- numarul de zone definite pe disc
- pointeri spre harta de biti a alocarii inodurilor
- pointeri spre harta de biti a spatiului liber disc
- dimensiunea zonelor disc
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew20.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew20.txt
RO: Considerând că într-un bloc încap N adrese spre alte blocuri, câte blocuri de date sunt adresate de indirectarea dublă împreună cu cea triplă a unui i-nod?
EN: Considering that a block can contain N addresses towards other blocks, how many data blocks are addressed by an i-node's double and triple indirections together?

Answer:
n^2 + n^3

n la patrat + na la puterea a treia
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew01.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew01.txt
RO: Daţi trei expresii regulare care acceptă orice linie care conţine cel puţin două vocale dar nicio cifră.
EN: Give three regular expressions that match any line that contains a least two vowels but no digits.

Answer:
grep -E "[A-Za-z]+[AEIOUaeiou][A-Za-z]+[AEIOUaeiou][A-Za-z]+" numefisier.txt | grep -v "[^0-9]"
grep -E -i "[a-z]+[aeiou][a-z]+[aeiou][a-z]+" numefisier.txt | grep -v "[^0-9]"
grep "[A-Za-z]+[AEIOUaeiou][A-Za-z]+[AEIOUaeiou][A-Za-z]+" numefisier.txt | grep -v "[^0-9]"
egrep e ca si grep -E, doar am schimba prima comanda
-v ce nu contine
-E = extended
-i case insensitive

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew02.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew02.txt
RO: Daţi o comandă GREP care afişează toate liniile dintr-un fişier care conţin un număr par de vocale (pe lângă alte eventuale caractere).
EN: Give a GREP command that display all the lines in a file that contain an even number of vowels (among other potential characters).

Answer:
grep -c "[AEIOUaeiou]{2,}" numefisier.txt 
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew03.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew04.txt
RO: Scrieţi o comandă AWK care afişează suma câmpurilor de pe poziţia egală cu numărul liniei curente.
EN: Write an AWK command that displays the sum of the fields on the position equal to the current line number.

Answer:

awk 'BEGIN{sum=0}{sum=sum+$NR}END{print sum}' numefisier.txt
NR=numarul liniei curente
$NR argumentul de pe pozitia=linia curenta
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew03.txt
RO: Scrieţi o comandă SED care afişează dintr-un fişier doar liniile care conţin exclusiv o expresie artimetică validă conţinând adunări şi scăderi de numere întregi.
EN: Write a SED command that display from a file only the lines that contain exclusively a valid artihmetical expression of integer additions and subtractions.

Answer:
sed -E "s/[0-9]+[0-9]*\+[0-9]+[0-9]*|[0-9]+[0-9]*\-[0-9]+[0-9]*/1/g" fisier.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew05.txt
RO: Daţi două soluţii pentru a ascunde ieşirea standard şi ieşirea de eroare a unei comenzi prin redirectarea în /dev/null.
EN: Give two solutions for hiding a commands standard and error outputs by redirecting them to /dev/null.

Answer:

/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew05.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew05.txt
RO: Daţi două soluţii pentru a ascunde ieşirea standard şi ieşirea de eroare a unei comenzi prin redirectarea în /dev/null.
EN: Give two solutions for hiding a commands standard and error outputs by redirecting them to /dev/null.

Answer:
/dev/null este fisierul care ascunde, si e gol
comanda 2>&1 | /dev/null
comanda 2>&1 > /dev/null
sau si prin >>
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew06.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew07.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew08.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew09.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew10.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew11.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew12.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew12.txt
RO: Ce este o "resursă critică"?
EN: What is a "critical resource"?

Answer:
resursa critica reprezinta variabilele sau altele se afla in sectiunea critica, aceasta fiind blocul de cod care acceseaza si
manipuleaza resursele partajate si trebuie executata de UN SINGUR thread. resursele se modifica din bloc. aceasta inseamna 
resursa critica.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew13.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew13.txt
RO: De ce apelul pthread_cond_wait primeşte şi un mutex ca argument?
EN: Why does the pthread_cond_wait call get also a mutex as argument?

Answer:
deoarece e nevoie de variabila conditionala asupra carui mutex se aplica! fiecare mutex are o variabila conditionala!
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew14.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew14.txt
RO: Care va fi efectul înlocuirii apelurilor la pthread_mutex_lock cu apeluri la pthread_rwlock_wrlock?
EN: What will be the effect of replacing calls to pthread_mutex_lock with calls to pthread_rwlock_wrlock?

Answer:
pthread_mutex_lock deschide sectiunea critica, iar cel de-al doilea apel cu read lock si write lock se aplica asupra 
sectiunii critice direct.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew15.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew16.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew15.txt
RO: Care e efectul apelului la sem_wait pentru un semafor cu valoarea zero?
EN: What is the effect of calling sem_wait on a semaphore with value zero?

Answer:
sem_wait decrementeaza semaforul. daca acesta este 0, atunci se va trece de la starea de executie in wait.

/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew16.txt
RO: Cum puteţi decrementa valoarea unui semafor?
EN: How can you decrement the value of a POSIX semaphore?

Answer:
decrementarea unui semafor se realizeaza prin metoda P, care solicita acces la resurse.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-17.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew17.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew17.txt
RO: Ce puteţi face ca programator pentru a preveni deadlock-urile? Justificaţi răspunsul.
EN: What can you do as a software developer to prevent deadlocks? Justify your answer.

Answer:
blocarea resurselor in aceeasi ordine pentru prevenirea ciclurilor intre procese.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew18.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew18.txt
RO: Prin ce tranziţie de stare va trece un process când scrie într-un fişier?
EN: What state transition will a process undergo when writing to a file?

Answer:
trece din starea run in swap = so muta paginile procesului din memoria RAM pe disc
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew19.txt
RO: Ce conţinue superblocul unui disc Linux?
EN: What is the content of the superblock on a Linux disk?

Answer:
superblocul este blocul 1 care contine:
- numarul de n inoduri
- numarul de zone definite pe disc
- pointeri spre harta de biti a alocarii inodurilor
- pointeri spre harta de biti a spatiului liber disc
- dimensiunea zonelor disc
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew20.txt
RO: Considerând că într-un bloc încap N adrese spre alte blocuri, câte blocuri de date sunt adresate de indirectarea dublă împreună cu cea triplă a unui i-nod?
EN: Considering that a block can contain N addresses towards other blocks, how many data blocks are addressed by an i-node's double and triple indirections together?

Answer:
n^2 + n^3

n la patrat + na la puterea a treia
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew09.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> nano exam-ew14.txt
/home/xm/exam/os/account/alexandra.bontidean/ex3086> cat exam-ew14.txt
RO: Care va fi efectul înlocuirii apelurilor la pthread_mutex_lock cu apeluri la pthread_rwlock_wrlock?
EN: What will be the effect of replacing calls to pthread_mutex_lock with calls to pthread_rwlock_wrlock?

Answer:
pthread_mutex_lock deschide sectiunea critica, iar cel de-al doilea apel cu read lock si write lock se aplica asupra 
sectiunii critice direct.
efectul ca DOAR UN SINGUR THREAD sa aiba acces la sectiunea critica.
/home/xm/exam/os/account/alexandra.bontidean/ex3086> xnlock
bash: xnlock: command not found
/home/xm/exam/os/account/alexandra.bontidean/ex3086> xmlock
Connection to 172.30.0.9 closed by remote host.
Connection to 172.30.0.9 closed.
alexandrabontidean@Air-Alexandra ~ % 
