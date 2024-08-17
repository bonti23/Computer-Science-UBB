bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; 3*[20*(b-a+2)-10*c]+2*(d-3)
; a, b, c - byte ; d - word
segment data use32 class=data
    a db 2
    b db 3
    c db 4
    d db 5

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, [b]
        sub al, [a]
        add al, 2
        mov bl, 20
        mul bl
        mov cx, ax
        mov al, 10
        mul byte[c]
        sub cx, ax
        mov ax, cx
        mov bx, 3
        mul bx
        mov cx, ax
        mov ax, [d]
        sub ax, 3
        mov bx, 2
        mul bx
        add cx, ax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
