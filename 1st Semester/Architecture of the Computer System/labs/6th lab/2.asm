bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; An array of words is given. Write an asm program in order to obtain an array of doublewords, where each doubleword will contain each nibble unpacked on a byte (each nibble will be preceded by a 0 digit), arranged in an ascending order within the doubleword.
segment data use32 class=data
    s dw 1432h, 8675h, 0ADBCh
    len equ ($-s)/2
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
              lodsw
              mov bl, al
              and bl, 00001111b
              mov bh, al
              and bh, 11110000b
              mov dl, ah
              and dl, 00001111b
              mov dh, ah
              and dh, 11110000b
              cmp bh, bl
              jna step1 ;bh<bl
              xchg bh, bl
              step1:
                    cmp dl, bl
                    jna step2 ;dl<bl
                    xchg dl, bl
              step2:
                    cmp dh, bl
                    jna step3 ;dh<bl
                    xchg dh, bl
              step3:
                    cmp dl, bh
                    jna step4
                    xchg dl, bh
              step4:
                    cmp dh, bh
                    jna step5
                    xchg dh, bh
              step5:
                    cmp dh, dl
                    jna fin
                    xchg dh, dl
              fin:
                    mov eax, 0
                    mov al, bl
                    shl eax, 8
                    add al, bh
                    shl eax, 8
                    add al, dl
                    shl eax, 8
                    add al, dh
                    stosd
        loop repeat
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
