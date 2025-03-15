bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A string of doublewords is given. Obtain the list made out of the high bytes of the high words of each doubleword from the given list with the property that these bytes are multiple of 3.
segment data use32 class=data
    s dd DD 12345678h, 1A2B3C4Dh, FE98DC76h
    len equ ($-s)/4
    d resb len
    three dd 3
    
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
              lodsw
              shr eax, 24
              mov bl, al
              cdq
              idiv dword[three]
              cmp edx, 0
              je addd
              jmp skip
              addd:
                    mov al, bl
                    stosb
              skip:
        loop repeat
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
