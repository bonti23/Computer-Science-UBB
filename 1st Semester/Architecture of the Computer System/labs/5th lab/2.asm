bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Given a character string S, obtain the string D containing all special characters (!@#$%^&*) of the string S. 
segment data use32 class=data
    s db '+', '4', '2', 'a', '@', '3', '$', '*'
    len equ $-s
    characters db "!@#$%^&*"
    len2 equ $-characters
    d resw len
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, len
        mov edi, 0
        mov esi, 0
        repeat:
              mov al, [s+esi]
              mov edx, ecx
              mov ecx, len2
              mov ebp, 0
              repeat2:
                    mov bl, [characters+ebp]
                    cmp al, bl
                    je addd
                    inc ebp
              loop repeat2
              jmp skip
              addd:
                    mov [d+edi], al
                    inc edi
              skip:
                    inc esi
                    mov ecx, edx
        loop repeat
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
