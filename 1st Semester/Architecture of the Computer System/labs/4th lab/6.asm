bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the word A, obtain the integer number n represented on the bits 0-2 of A. Then obtain the word B by rotating A n positions to the right. Compute the doubleword C:
;the bits 8-15 of C have the value 0
;the bits 16-23 of C are the same as the bits of 2-9 of B
;the bits 24-31 of C are the same as the bits of 7-14 of A
;the bits 0-7 of C have the value 1
segment data use32 class=data
    a dw ABCDh
    b dw 0
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [a]
        shl ax, 13
        shr ax, 13
        mo cx, ax
        mov ax, [a]
        mov cl, cl
        ror ax, cl
        mov [b], ax
        or dword[c], 00000000000000000000000011111111b
        movsx ebx, word[b]
        shl ebx, 22
        shr ebx, 24
        shl ebx, 16
        or dword[c], ebx
        movsx eax, word[a]
        shl eax, 17
        shr eax, 24
        shl eax, 24
        or dword[c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
