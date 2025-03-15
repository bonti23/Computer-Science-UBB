bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the words A and B, compute the byte C as follows:
;the bits 0-5 are the same as the bits 5-10 of A
;the bits 6-7 are the same as the bits 1-2 of B.
;Compute the doubleword D as follows:
;the bits 8-15 are the same as the bits of C
;the bits 0-7 are the same as the bits 8-15 of B
;the bits 24-31 are the same as the bits 0-7 of A
;the bits 16-23 are the same as the bits 8-15 of A.
segment data use32 class=data
    a dw ABCDh
    b dw BCDEh
    c db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [a]
        shl ax, 5
        shr ax, 10
        or byte[c], al
        mov bx, [b]
        shr bx, 1
        shl bx, 14
        shr bx, 8
        or byte[c], bl
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
