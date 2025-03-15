bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A byte string S is given. Obtain the string D by concatenating the elements found on the even positions of S and then the elements found on the odd positions of S.
segment data use32 class=data
    s 1,2,3,4,5,6,7,8
    len equ $-s
    d resb len
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ecx, len
        repeat:
              ;odd
              mov al, [s+esi]
              test esi, 01h
              jz false
              mov [d+edi], al
              inc edi
              false:
                    in esi
        loop repeat
        mov ecx, len
        mov esi, 0
        repeat2:
              ;even
              mov al, [s+esi]
              test esi, 01h
              jz even
              jmp skip
              even:
                    mov [d+edi], al
                    inc edi
              skip:
                    inc esi
        loop repeat2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
