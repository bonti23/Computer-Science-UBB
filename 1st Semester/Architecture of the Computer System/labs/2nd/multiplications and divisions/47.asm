bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; h/a + (2 + b) + f/d – g/c
; a, b, c, d - byte, e, f, g, h - word
segment data use32 class=data
    a db 2
    b db 3
    c db 4
    d db 5
    e dw 6
    f dw 7
    g dw 8
    h dw 9

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [h]
        idiv byte[a]
        mov bl, 2
        add bl, [b]
        add al, bl
        mov bl, al
        mov ax, [f]
        div byte[d]
        add bl, al
        mov ax, [g]
        idiv byte[c]
        sub bl, al
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
