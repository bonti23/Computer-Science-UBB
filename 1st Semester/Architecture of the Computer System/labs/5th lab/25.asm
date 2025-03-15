bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two character strings S1 and S2 are given. Obtain the string D which contains all the elements of S1 that do not appear in S2.
segment data use32 class=data
    s1 db "+42a84x5"
    len1 equ $-s1
    s2 db "a45"
    len2 equ $-s2
    r resb len1+len2
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ecx, len1
        repeat:
              mov al, [s1+esi]
              mov ebp, 0
              mov edx, ecx
              mov ecx, len2
              cmp ecx, 0
              je false
              repeat2:
                    mov bl, [s2+ebp]
                    cmp al, bl
                    je false
                    inc ebp
              loop repeat2
              mov [d+edi], al
              inc edi
              false:
                    inc esi
                    mov ecx, edx
        loop retea
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
