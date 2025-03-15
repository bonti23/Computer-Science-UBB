bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A byte string S is given. Build the string D whose elements represent the sum of each two consecutive bytes of S.
segment data use32 class=data
    s db 1, 2, 3, 4, 5, 6
    len equ $-s
    d resb len-1
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov al, [s+esi]
        inc esi
        mov ecx, len-1
        repeat:
              mov bl, [s+esi]
              add al, bl
              mov [d+edi], al
              mov al, bl
              inc esi
              inc edi
        loop repeat
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
