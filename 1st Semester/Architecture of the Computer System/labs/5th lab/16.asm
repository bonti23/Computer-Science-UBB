bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Two character strings S1 and S2 are given. Obtain the string D by concatenating the elements found on odd positions in S2 and the elements found on even positions in S1.
segment data use32 class=data
    s1 db "abcdef"
    len1 equ $-s1
    s2 db "12345"
    len2 equ $-s2
    d resb len1+len2
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov edi, 0
        mov ebp, 0
        mov ecx, len2
        repeat:
              mov al, [s2+ebp]
              test ebp, 01h
              jz ok
              jmp false
              ok:
                    mov [d+edi], al
                    inc edi
              false:
                    inc ebp
        loop repeta
        mov ecx, len1
        repeat2:
              mov al, [s1+esi]
              test esi, 01h
              jz false2
              mov [d+edi], al
              inc edi
              false2:
                    inc esi
        loop repeat2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
