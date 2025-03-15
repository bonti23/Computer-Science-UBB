bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given 4 bytes, compute in AX the sum of the integers represented by the bits 4-6 of the 4 bytes.
segment data use32 class=data
    a db 11001111b
    b db 10101010b
    c db 11110000b
    d db 10000001b

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov bl, [a]
        shl bl, 1
        shr bl, 5
        mov cl, bl
        mov bl, [b]
        shl bl, 1
        shr bl, 5
        add cl, bl
        mov bl, [c]
        shl bl, 1
        shr bl, 5
        add cl, bl
        mov bl, [d]
        shl bl, 1
        shr bl, 5
        add cl, bl
        mov al, cl
        cbw
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
