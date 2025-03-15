bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; An array of doublewords, where each doubleword contains 2 values on a word (unpacked, so each nibble is preceded by a 0) is given. Write an asm program to create a new array of bytes which contain those values (packed on a single byte), arranged in an ascending manner in memory, these being considered signed numbers.
segment data use32 class=data
    s dd 0702090Ah, 0B0C0304h, 05060108h
    len equ ($-s)/4
    d resb len*2
    
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
              mov bl, al
              lodsb
              shl bl, 4
              add al, bl
              stosb
        loop repeat
        mov ecx, len-1
        do:
              mov ebx, ecx
              mov esi, 0
              do2:
                    mov al, [d+esi]
                    mov dl, [d+esi+1]
                    cmp al, dl
                    jl dontswap
                    mov [d+esi], dl
                    mod [d+esi+1], al
                    dontswap:
                          inc esi
                          dec ebx
                          jnz do2
        loop do
        final
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
