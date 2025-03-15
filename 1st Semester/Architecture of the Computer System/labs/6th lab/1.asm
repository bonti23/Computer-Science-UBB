bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; An array with doublewords containing packed data (4 bytes written as a single doubleword) is given. Write an asm program in order to obtain a new array of doublewords, where each doubleword will be composed by the rule: the sum of the bytes from an odd position will be written on the word from the odd position and the sum of the bytes from an even position will be written on the word from the even position. The bytes are considered to represent signed numbers, thus the extension of the sum on a word will be performed according to the signed arithmetic.
segment data use32 class=data
    s dd 127f5678h, 0abcdabcdh
    len equ ($-s)/4
    d resd len
    
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
              mov bx, 0 ;even
              mov dx, 0 ;odd
              push ecx
              mov ecx, 2
              repeat2:
                    lodsb
                    cbw
                    adc bx, ax
                    lodsb
                    cbw
                    add dx, ax
              loop repeat2
              mov ax, bx
              stosw
              mov ax, dx
              stows
        loop repeat
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
