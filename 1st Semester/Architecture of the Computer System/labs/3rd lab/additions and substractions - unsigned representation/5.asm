bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; (c-a-d)+(c-b)-a
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
        cwde
        mov ecx, [c]
        sub ecx, eax
        mov eax, ecx
        cdq
        sub eax, dword[d+0]
        sbb edx, dword[d+4]
        mov cx, [b]
        movzx ecx, cx
        mov ebx, [c]
        sub ebx, ecx
        movzx ecx, byte[a]
        sub ebx, ecx
        mov ecx, ebx
        mov ebx, eax
        mov eax, ecx
        mov ecx, edx
        cdq
        add ebx, eax
        ac ecx, edx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
