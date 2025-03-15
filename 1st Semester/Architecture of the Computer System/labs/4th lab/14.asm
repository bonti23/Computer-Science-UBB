bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given the doubleword A, obtain the integer number n represented on the bits 14-17 of A. Then obtain the doubleword B by rotating A n positions to the left. Finally, obtain the byte C as follows:
;the btis 0-5 of C are the same as the bits 1-6 of B
;the bits 6-7 of C are the same as the bits 17-18 of B
segment data use32 class=data
    a dd CFCFCFCFh
    b dd 0
    c db F3h
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov eax, [a]
        shl eax, 14
        shl eax, 28
        mov al, al
        mov cl, al
        mov ebx, [a]
        rol ebx, cl
        mov dword[b], ebx
        mov eax, [b]
        shl eax, 25
        shr eax, 26
        mov al, al
        or byte[c], al
        mov eax, [b]
        shl eax, 13
        shr eax, 30
        shl eax, 6
        mov al, al
        or byte[c], al
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
