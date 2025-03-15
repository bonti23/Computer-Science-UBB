bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two byte string S1 and S2 are given, having the same length. Obtain the string D by intercalating the elements of the two strings.
segment data use32 class=data
    s1 1, 3, 5, 7
    len1 equ $-s1
    s2 2, 6, 9, 4
    len2 equ $-s2
    d resb len1+len2
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, 0
        mov ecx, len1
        repeat:
              mov al, [s1+esi]
              mov [d+edi], al
              inc edi
              mov bl, [s2+ebp]
              mov [d+edi], bl
              inc edi
              inc esi
              inc ebp
        loop repeat
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
