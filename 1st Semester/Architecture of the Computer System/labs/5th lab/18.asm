bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two byte strings A and B are given. Obtain the string R that contains only the odd positive elements of the two strings.
segment data use32 class=data
    a db 2, 1, 3, -3
    lena equ $-a
    b db 4, 5, -5, 7
    lenb equ $-b
    d resb lena+lenb
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, 0
        mov ecx, lena
        repeat:
              mov al, [a+esi]
              cmp al, 0
              jge verify
              jmp skip
              verify:
                    test al, 01h
                    jz false
                    mov [d+edi], al
                    inc edi
                    false:
              skip:
                    inc esi
        loop repeta
        mov ecx, lenb
        repeat2:
              mov bl, [b+ebp]
              cmp bl, 0
              jge verify2
              jmp skip2
              verify2:
                    test bl, 01h
                    jz false2
                    mov [d+edi], bl
                    inc edi
                    false2:
              skip2:
                    inc ebp
        loop repeat2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
