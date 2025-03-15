bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A word string s is given. Build the byte string d such that each element d[i] contains:
;- the count of zeros in the word s[i], if s[i] is a negative number
;- the count of ones in the word s[i], if s[i] is a positive number
segment data use32 class=data
    s1 dw -22, 145, -48, 127
    len equ ($-s)/2
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
              lodsw
              mov bl, 0
              cmp ax, 0
              jge positive
              jmp negative
              positive:
                    div byte[2]
                    cmp ah, 0
                    je pass
                    inc bl
                    pass:
                          mov ah, 0
                          cmp al, 0
                          jne positive
              jmp skip
              negative:
                    idiv byte[2]
                    cmp ah, 1
                    je pass2
                    inc bl
                    pass2:
                          mov ah, 0
                          cmp al, 0
                          jne negative
              skip:
                    mov al, bl
                    stosb
        loop repeat
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
