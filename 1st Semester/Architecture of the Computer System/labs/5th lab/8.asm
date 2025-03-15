bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A character string S is given. Obtain the string D that contains all capital letters of the string S.
segment data use32 class=data
    s db "bAbB2%xM"
    len equ $-s
    d resb len
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ecx, len
        repeat:
              mov al, [s1+esi]
              cm al, 'A'
              jl false
              cmp al, Z'
              jg false
              mov [d+edi], al
              inc edi
              false:
                    inc esi
        loop repeat
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
