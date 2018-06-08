section .data
    msg db "hello, world!"

section .text
    global _start
_start:
    mov	rax, 1                  ; sys_write
    mov rdi, 1                  ; stdout
    mov rsi, msg                ; msg
    mov rdx, 13                 ; length
    syscall
    mov rax, 60                 ; exit
    mov rdi, 0                  ; status
    syscall
