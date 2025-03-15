bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the word A and the byte B, compute the doubleword C as follows:
;the bits 0-3 of C are the same as the bits 6-9 of A
;the bits 4-5 of C have the value 1
;the bits 6-7 of C are the same as the bits 1-2 of B
;the bits 8-23 of C are the same as the bits of A
;the bits 24-31 of C are the same as the bits of B
segment data use32 class=data
    a dw ABCDh
    b db BCh
    c db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        or dword[c], 00000000000000000000000000110000b
        movzx ebx, byte[b]
        shl ebx, 24
        or dword[c], ebx
        movzx eax, word[a]
        shl eax, 8
        or dword[c], eax
        movzx ebx, byte[b]
        shl ebx, 29
        shr ebx, 30
        shl ebx, 6
        or dword[c], ebx
        movzx eax, word[a]
        shl eax, 22
        shr eax, 28
        or dword[c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
