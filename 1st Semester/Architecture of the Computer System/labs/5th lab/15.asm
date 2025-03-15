bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two byte strings A and B are given. Obtain the string R by concatenating the elements of B in reverse order and the odd elements of A.
segment data use32 class=data
    a db 2, 1, 3, 3, 4, 2, 6
    lena equ $-a
    b db 4, 5, 7, 6, 2, 1
    lenb equ $-b
    r resb lena+lenb
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, lenb-1
        mov ecx, lenb
        repeat:
              mov al, [b+ebp]
              mov [r+edi], al
              inc edi
              dec ebp
        loop repeta
        mov ecx, lena
        repeat2:
              mov al, [a++esi]
              test al, 01h
              jz false
              mov [r+edi], al
              inc edi
              false:
                    inc esi
        loop repeat2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
