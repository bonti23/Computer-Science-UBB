bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; (d+d-b)+(c-a)+d
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
        mov al, 0
        movzx eax, al
        cdq
        mov eax, dword[d+0]
        mov edx, dword[d+4]
        add eax, dword[d+0]
        adc edx, dword[d+4]
        mov ebx, eax
        mov ecx, edx

        movzx eax, word[b]
        cdq
        sub ebx, eax
        sbb ecx, edx

        mov al, 0
        sub al, [a]
        movsx eax, al
        add eax, [c]
        cdq
        add ebx, eax
        adc ecx, edx
        add ebx, dword[d+0]
        adc ecx, dword[d+4]
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
