bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the byte A and the word B, compute the byte C as follows:
;the bits 0-3 are the same as the bits 2-5 of A
;the bits 4-7 are the same as the bits 6-9 of B.
segment data use32 class=data
    a dw ABCDh
    b dw BCDEh
    c db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov bx, [b]
        shl bx, 6
        shr bx, 12
        shl bx, 4
        mov bl, bl
        or byte[c], bl
        mov al, [a]
        shl al, 2
        shr al, 4
        or byte[c], al
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
