bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the words A and B, compute the doubleword C as follows:
;the bits 0-2 of C have the value 0
;the bits 3-5 of C have the value 1
;the bits 6-9 of C are the same as the bits 11-14 of A
;the bits 10-15 of C are the same as the bits 1-6 of B
;the bits 16-31 of C have the value 1
segment data use32 class=data
    a dw CFCFh
    b dw BNBNh
    c dd 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        or dword[c], 11111111111111110000000000111000b
        movzx eax, dword[a]
        shl eax, 17
        shr eax, 28
        shl eax, 6
        or dword[c], eax

        movzx ebx, word[b]
        shl ebx, 25
        shr ebc, 26
        shl ebx, 10
        or dword[c], ebx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
