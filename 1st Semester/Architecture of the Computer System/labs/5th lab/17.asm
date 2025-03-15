bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two byte strings S1 and S2 are given, having the same length. Obtain the string D so that each element of D represents the maximum of the corresponding elements from S1 and S2.
segment data use32 class=data
    s1 db 1, 3, 6, 2, 3, 7
    len1 equ $-s1
    s2 db 6, 3, 8, 1, 2, 5
    len2 equ $-s2
    d resb len1
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, 0
        mov ecx, len2
        repeat:
              mov al, [s1+esi]
              mov bl, [s2+ebp]
              cmp al, bl
              jge addA
              cmp al, bl
              jl addB
              addA:
                    mov [d+edi], al
                    inc edi
              jmp skip
              addB:
                    mov [+edi], bl
                    inc edi
              skip:
                    inc esi
                    inc ebp
        loop repeta
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
