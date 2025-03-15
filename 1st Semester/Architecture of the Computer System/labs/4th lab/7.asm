bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the words A and B, compute the doubleword C:
;the bits 0-4 of C have the value 1
;the bits 5-11 of C are the same as the bits 0-6 of A
;the bits 16-31 of C have the value 0000000001100101b
;the bits 12-15 of C are the same as the bits 8-11 of B
segment data use32 class=data
    a dw ABCDh
    b dw BCDEh
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        or dword[c], 00000000011001010000000000011111b
        movsx eax, word[a]
        shl eax, 25
        shr eax, 20
        or dword[c], eax
        movsx ebx, word[b]
        shl ebx, 20
        shr ebx, 28
        shl ebx, 12
        or dword[c], ebx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
