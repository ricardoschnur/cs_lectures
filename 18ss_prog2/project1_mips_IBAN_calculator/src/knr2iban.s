	.data
	.globl knr2iban
	.text
# -- knr2iban
# Argumente:
# a0: IBAN buffer (22 Zeichen)
# a1: BLZ buffer (8 Zeichen)
# a2: KNR buffer (10 Zeichen)
knr2iban:

# Sichere Adressen
	addu 	$sp, $sp, -16		# Lege Stack an
	sw		$ra, 0($sp)			# Sichere Rücksprungadresse
	sw		$s0, 4($sp)			# Sichere s0
	sw		$s1, 8($sp)			# Sichere s1
	sw		$s2, 12($sp)		# Sichere s2

	move $s0 $a0					# IBAN in $s0
	move $s1 $a1					# BLZ in $s1
	move $s2 $a2					# KNR in $s2

# Schreibe Ländercode
	li $t0, 68
	sb $t0, 0($a0)
	li $t0, 69
	sb $t0, 1($a0)
	li $t0, 49

# Schreibe Prüfziffer
	li $t0, 48
	sb $t0, 2($a0)
	sb $t0, 3($a0)

# Kopiere BLZ mit memcpy
	move 	$a0, $s0
	addiu $a0, $a0, 4
	move 	$a1, $s1
	li 		$a2, 8
	jal 	memcpy

# Kopiere KNR mit memcpy
	move 	$a0, $s0
	addiu $a0, $a0, 12
	move 	$a1, $s2
	li 		$a2, 10
	jal 	memcpy

# Berechne Prüfsumme
	move 	$a0, $s0
	jal 	validate_checksum
	li 		$t0, 98
	subu 	$t0, $t0, $v0

# Schreibe Prüfsumme
	move 	$a0, $t0
	move 	$a1, $s0
	addiu $a1, $a1, 2
	li 		$a2, 2
	jal		int_to_buf



	lw		$ra, 0($sp)			# Restauriere Rücksprungadresse
	lw		$s0, 4($sp)			# Restauriere s0
	lw		$s1, 8($sp)			# Restauriere s1
	lw		$s2, 12($sp)		# Restauriere s2
	addu 	$sp, $sp, 16		# Gebe Stack frei
	jr	$ra
