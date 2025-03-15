bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two character strings S1 and S2 are given. Obtain the string D by concatenating the elements of S2 in reverse order and the elements found on even positions in S1.
segment data use32 class=data
    s1 db '+', '2', '2', 'b', '8', '6', 'X', '8'
    s2 db 'a', '4', '5'
    len1 equ $-s1
    len2 equ $-s2
    d resb len1+len2
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, len2-1
        mov ecx, len2
        repeat:
              mo al, [s2+ebp]
              mov [d+edi], al
              inc edi
              dec ebp
        loop repeat
        mov ecx, len1
        repeat2:
              mov al, [s1+esi]
              test esi, 01h
              jz skip
              mov [d+edi], al
              inc edi
              skip:
                    inc esi
        loop repeat2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
