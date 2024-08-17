bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; (c+c+c)-b+(d-a)
; a, b, c, d - word
segment data use32 class=data
    a db 2
    b db 3
    c db 4
    d db 5

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [c]
        add ax, [c]
        add ax, [c]
        mov bx, [b]
        sub ax, bx
        mov bx, [d]
        sub bx, [a]
        add ax, bx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
