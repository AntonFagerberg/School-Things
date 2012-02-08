.set noreorder
.text
.globl start

.ent start
start:	addi	$a0, $zero, 0x3
loop:	addi	$t0, $t0, 0x1
		add		$v0, $v0, $t0
		bne		$t0, $a0, loop
		nop
.end start