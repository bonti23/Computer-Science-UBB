bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two byte strings s1 and s2 are given. Build the byte string d such that, for every byte s2[i] in s2, d[i] contains either the position of byte s2[i] in s1, either the value of 0.
segment data use32 class=data
    s1 db 7, 33, 55, 19, 46
    len1 equ $-s1
    s2 db 33, 21, 7, 13, 27, 19, 55, 1, 46
    len2 equ $-s2
    d resb len2
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, s2
        mov edi, d
        mov ecx, len2
        jecxf final
        cld
        repeat:
              lodsb
              push ecx
              mov ecx, len1
              mov ebx, 0
              repeat2:
                    cmp al, [s1+ebx]
                    je put
                    inc ebx
              loop repeat2
              jmp zero
              put:
                    inc ebx
                    mov al, bl
                    stosb
              jmp skip
              zero:
                    mov al, 0
                    stosb
              skip:
                    pop ecx
        loop repeat
        final
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
