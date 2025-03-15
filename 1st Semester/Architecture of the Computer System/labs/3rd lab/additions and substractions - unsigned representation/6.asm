bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; (a+b)-(a+d)+(c-a)
; a - byte, b - word, c - double word, d - qword
segment data use32 class=data
    a db 2
    b dw 3
    c dd 4
    d dq 5

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, [a]
        cbw
        add ax, [b]
        cwde
        cdq
        mov ebx, eax
        mov ecx, edx

        ;a+d
        mov al, [a]
        cbw
        cwde
        cdq
        add eax, dword[d+0]
        adc edx, dword[d+4]

        sub ebx, eax
        sbb ecx, edx

        ;c-a
        movzx edx, byte[a]
        mov eax, [c]
        sub eax, edx
        cdq
        add ebx, eax
        adc ecx, edx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
