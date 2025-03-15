bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two character strings S1 and S2 are given. Obtain the string D by concatenating the elements found on the positions multiple of 3 from S1 and the elements of S2 in reverse order.
segment data use32 class=data
    s1 db 1, 3, 6, 2, 3, 2
    len1 equ $-s1
    s2 db 6, 3, 8, 1, 2, 5
    len2 equ $-s2
    d resb len1+len2
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov ebp, len2-1
        mov edi, 0
        mov ecx, len1/3+1
        repeat:
              mov al, [s1+esi]
              mov [d+edi], al
              inc edi
              add esi, 3
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
