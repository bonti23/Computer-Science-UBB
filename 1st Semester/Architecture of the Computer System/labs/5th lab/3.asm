bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two byte strings S1 and S2 are given, having the same length. Obtain the string D in the following way: each element found on the even positions of D is the sum of the corresponding elements from S1 and S2, and each element found on the odd positions of D is the difference of the corresponding elements from S1 and S2.
segment data use32 class=data
    s1 db 1, 2, 3, 4
    len1 equ $-s1
    s2 db 5, 6, 7
    len2 equ $-s2
    d resb len1+len2
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov ebp, len2-1
        mov edi, 0
        mov ecx, len1
        repeat:
              mov al, [s+esi]
              mov [d+edi], al
              inc edi
              inc esi
        loop repeat
        mov ecx, len2
        repeat2:
              mov al, [s2+ebp]
              mov [d+edi], al
              inc edi
              dec ebp
        loop repeat2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
