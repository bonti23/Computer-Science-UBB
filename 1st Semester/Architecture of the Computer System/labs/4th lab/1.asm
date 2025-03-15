bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the words A and B, compute the doubleword C as follows:
;the bits 0-4 of C are the same as the bits 11-15 of A
;the bits 5-11 of C have the value 1
;the bits 12-15 of C are the same as the bits 8-11 of B
;the bits 16-31 of C are the same as the bits of A
segment data use32 class=data
    a dw AF3Ch
    b dw 3CAAh
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        movsx eax, word[a]
        shl eax, 16
        or dword[c], eax
        or dword[c], 00000000000000000000111111100000b
        movsx ebx, word[b]
        shr ebx, 8
        shl ebx, 28
        shr ebx, 16
        or dword[c], ebx
        shr eax, 27
        or dword[c], eax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
