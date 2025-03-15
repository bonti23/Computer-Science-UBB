bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the bytes A and B, compute the doubleword C as follows:
;the bits 16-31 of C have the value 1
;the bits 0-3 of C are the same as the bits 3-6 of B
;the bits 4-7 of C have the value 0
;the bits 8-10 of C have the value 110
;the bits 11-15 of C are the same as the bits 0-4 of A
segment data use32 class=data
    a db CAh
    b db B2h
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        or dword[c], 11111111111111110000011000000000b
        movzx ebx, byte[b]
        shl ebx, 25
        shr ebx, 28
        or dword[c], ebx
        movzx eax, byte[a]
        shl eax, 27
        shr eax, 16
        or dword[c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
