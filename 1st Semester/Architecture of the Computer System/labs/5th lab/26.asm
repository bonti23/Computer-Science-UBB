bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; A byte string S is given. Obtain the maximum of the elements found on the even positions and the minimum of the elements found on the odd positions of S.
segment data use32 class=data
    s db 1, 4, 2, 3, 8, 4, 9, 5
    len equ $-s
    maxim resb 1
    minim resb 1
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi, 0
        mov bl, 0 ;maxim
        mov edi, 0 ;maxim
        mov, dl, 127 ;minim
        mov ebp, 0 ;minim
        mov ecx, len
        repeat:
              mov al, [s+esi]
              test esi, 01h
              jz even
              jmp false
              even:
                    cmp al, bl
                    jg max
                    jmp false
              max:
                    mov bl, al
              false:
                    inc esi
        loop repeat
        mov ecx, len
        mov esi, 0
        repeat2:
              mov al, [s+esi]
              test esi, 01h
              jz false2
              cmp al, dl
              jl min
              jmp false2
              min:
                    mov dl, al
              false2:
                    inc esi
        loop repeat2
        mov [maxim+edi], bl
        mov [minim+ebp], dl
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
