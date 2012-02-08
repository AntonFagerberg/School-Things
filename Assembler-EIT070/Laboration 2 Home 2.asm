#include <iregdef.h>

.set noreorder
.text
.globl start

.ent start
start:		add	s0, r0, 0x61	# char a
		add	s1, r0, 0x7a	# char z
		lui	s2, 0xffff	# char å
		ori	s2, s2, 0xffe5	
		lui	s3, 0xffff	# char ä
		ori	s3, s3, 0xffe4	
		lui	s4, 0xffff	# char ö
		ori	s4, s4, 0xfff6	
		
loop:		jal	getchar		# Read char from console, return in v0
		nop
		beq	v0, s2,	dosub	# Jump to dosub if char is å
		nop
		beq	v0, s3,	dosub	# Jump to dosub if char is ä
		nop
		beq	v0, s4,	dosub	# Jump to dosub if char is ö
		nop
		bgtu	s0, v0, output	# Skip dosub if char < a
		nop
		bgtu	v0, s1, output	# Skip dosub if char > z
		nop
dosub:		sub	v0, v0, 0x20	# Add 0x20 to convert lower case to upper case
		
output:		add	a0, v0, r0	# Move v0 to a0 (getchar arg -> putchar arg)
		jal	putchar		# Write char to console
		nop
		j	loop		# It goes on and on and on and on...
		nop
.end start