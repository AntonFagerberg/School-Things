#include <iregdef.h>
#include <idtcpu.h>
#include <excepthdr.h>

#define SWITCHES 0xbf900000
#define LEDS     0xbf900000
#define BUTTONS  0xbfa00000

        .globl start
        .ent start

start:  sub     sp, sp, 4       # Reserve new stack space
        sw      ra, 0(sp)       # Save return address

        jal     init_ext_int    # Initialize interrupts
        la      a0, IntHand     # Install our own interrupt routine
        jal     install_normal_int
        li      a0, EXT_INT3    # Enable interrupt 3 (K1, K2, timer)
        jal     enable_int      # Enable external timer interrupts
	
Loop:   jal     Comp            # Perform heavy computations
        b       Loop            # Repeat loop

                                # Standard program ending
        lw      ra, 0(sp)       # Restore return address
        addi    sp, sp, 4       # Dealloacte stack space
        j       _exit           # Jump to exit routine

        .end start

        .ent IntHand

IntHand:

        ### Add code for interrupt handler here! ###
	sub     sp, sp, 4        # Reserve new stack space
        sw      ra, 0(sp)        # Save return address
	jal	get_CAUSE		
	lw      ra, 0(sp)        # Restore return address
        addi    sp, sp, 4        # Dealloacte stack space
        andi	t1, v0, EXT_INT3 # Mask out buttons
	beq	t1, t0, skip	 # Skip if other interrupt
        
        la      t0, BUTTONS	 # Place adress of buttons in t0
        lb      t9, 0x0(t0)      # Load button port value
        sb	zero, 0(t0)	 # Reset button
        la      t0, SWITCHES	 # Place adress of switches in t0
        lb      a0, 0x0(t0)	 # Load switch position
        la      t0, LEDS	 # Place adress of leds in t0
        sb      a0, 0x0(t0)	 # Output switch position to LEDs

skip:	jr	ra
	nop

        .end IntHand

        .ent Comp

Comp:   li      t0, 0xffffff    # Initialize counter value
Delay:  sub     t0, t0, 1       # Decrease counter by 1
        bne     t0, r0, Delay   # Test if ready
        jr      ra              # Return to polling loop

        .end Comp