bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
;A byte string S is given. Obtain the string D1 which contains the elements found on the even positions of S and the string D2 which contains the elements found on the odd positions of S.
segment data use32 class=data
    s db 1, 5, 3, 8, 2, 9
    len equ $-s
    d1 resb len
    d2 resb len
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, 0
        mov ecx, len
        repeat:
              mov al, [s+esi]
              test esi, 01h
              jz even
              jmp odd
              even:
                    mov [d1+edi], al
                    inc edi
              jmp skip
              odd:
                    mov [d2+ebp], al
                    inc ebp
              skip:
                    inc esi
        loop repeta
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
