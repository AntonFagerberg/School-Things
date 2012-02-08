#include <iregdef.h>

.set noreorder
.text
.globl start

.ent start
start:	jal		wait 			# Wait for button click
		nop
		lui		s0, 0xbf90		# Load switch port address
		lb		s1, 0x0(s0)		# Read first number from switches
		nop
		jal		wait 			# Wait for button click
		nop
		lb		s2, 0x0(s0)		# Read second number from switches
		nop
		add		s3, s1, s2		# Perform an arithmetic operation
		sb		s3, 0x0(s0)		# Write the result to LEDs
		b		start			# Repeat all over again

wait:	lui 	a0, 0xbfa0		# Load K2 button address
		addi	t2, r0, 0x1		# Create mask 00...001
		lb		t0, 0x0(a0)		# Load byte from Interrupt address
		nop					
		and		t1, t2, t0		# Mask out LSB
		beq		t1, r0, wait	# Loop if LSB == 0
		nop
rel:	lb		t0, 0x0(a0)		# Load byte from Interrupt address
		nop						
		and		t1, t2, t0		# Mask out LSB
		bne		t1, r0, rel		# Loop if LSB == 1
		nop
		j		r31				# Jump to return address
.end start