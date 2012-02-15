#include <iregdef.h>

        .data
        .align 2
        .globl Test

Test:   .word 1
        .word 3
        .word 5
        .word 7
        .word 9
        .word 8
        .word 6
        .word 4
        .word 2
        .word 0
TextA:  .asciiz "Lab 3, Home Assigment 1\n"
TextB:  .asciiz "The max is %d\n"
TextC:  .asciiz "Done\n"

        .text
        .align 2
        .globl FindMax
        .ent FindMax

FindMax:
        #subu    sp, sp, 8   # Reserve a new 8 byte stack frame
        #sw      s0, 0(sp)   # Save value of s0 on the stack
        #sw      s1, 4(sp)   # Save value of s1 on the stack

        ### Add code to find maximum value element here! ###
        
        or      t0, a0, zero    # Save address in t0
        add     v0, zero, zero  # Set v0 to 0
        add     t5, zero, zero  # Counter = 0
        addi    t6, zero, 10    # Nr of words
                
loop:   lw      t1, 0(t0)       # Read from address
                
        slt     t4, t1, v0      # if t1 < t2 -> t4 = 1
        bne     t4, zero, skip  # If t1 < t2 skip
        nop
        move    v0, t1          # Save biggest value in v0

skip:   addi    t0, t0, 4       # Set address to next word.
        addi    t5, t5, 1       # Add 1 to counter
        bne     t5, t6, loop    # Loop
        nop
                
        #lw      s1, 4(sp)   # Restore old value of s1
        #lw      s0, 0(sp)   # Restore old value of s0
        #addu    sp, sp, 8   # Pop the stack frame
        jr      ra           # Jump back to calling routine

        .end FindMax

        .text
        .align 2
        .globl start
        .ent start

start:  subu    sp, sp, 32  # Reserve a new 32 byte stack frame
        sw      ra, 20(sp)  # Save old value of return address
        sw      fp, 16(sp)  # Save old value of frame pointer
        addu    fp, sp, 28  # Set up new frame pointer

        la      a0, TextA   # Load address to welcome text
        jal     printf      # Call printf to print welcome text

        la      a0, Test    # Load address to vector
        jal     FindMax     # Call FindMax subroutine

        la      a0, TextB   # Load address to result text
        move    a1, v0      # Move result to second register
        jal     printf      # Call printf to print result text

        la      a0, TextC   # Load address to goodbye text
        jal     printf      # Call printf to print goodbye text

        lw      fp, 16(sp)  # Restore old frame pointer
        lw      ra, 20(sp)  # Restore old return address
        addu    sp, sp, 32  # Pop stack frame
        j       _exit       # Jump to exit routine

        .end start