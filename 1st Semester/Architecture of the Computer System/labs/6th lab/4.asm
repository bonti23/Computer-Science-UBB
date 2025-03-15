bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A byte string s is given. Build the byte string d such that every byte d[i] is equal to the count of ones in the corresponding byte s[i] of s.
segment data use32 class=data
    s db 5, 25, 55, 127
    len equ $-s
    d resb len
    two db 2
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, s
        mov edi, d
        mov ecx, len
        jecxf final
        cld
        repeat:
              lodsb
              mov bl, 0
              repeat2:
                    mov h, 0
                    div byte[two]
                    cmp ah, 0
                    je false
                    inc bl
                    false:
                    cmp al, 0
                    jne repeat2
              mov al, bl
              stosb
        loop repeat
        final
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
