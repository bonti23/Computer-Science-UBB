bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; (a+b)/2+(10-a/c)+b/4
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
        mov al, [a]
        add al, [b]
        mov ah, 0 ;extension to ax unsigned
        mov bl, 2
        div bl ;al
        mov bl, al
        mov cl, 10
        movzx ax, byte[a]
        div byte[c]
        sub cl, al
        add bl, cl
        movzx ax, byte[b]
        mov cl, 4
        div cl
        add bl, al
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
