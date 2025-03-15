bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the byte A, obtain the integer number n represented on the bits 2-4 of A. Then obtain the byte B by rotating A n positions to the right. Compute the doubleword C as follows:
;the bits 8-15 of C have the value 0
;the bits 16-23 of C are the same as the bits of B
;the bits 24-31 of C are the same as the bits of A
;the bits 0-7 of C have the value 1
segment data use32 class=data
    a db CAh
    b db B2h
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, [a]
        mov [b], al
        shl al, 3
        shr al, 5
        mov cl, al
        mov al, [b]
        ror al, cl
        mov [b], al
        or dword[c],00000000000000000000000011111111b
        and dword[c],00000000000000000000000011111111b
        movzx ebx, byte[b]
        shl ebx, 16
        or dword[c], ebx
        movzx eax, byte[a]
        shl eax, 24
        or dword[c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
