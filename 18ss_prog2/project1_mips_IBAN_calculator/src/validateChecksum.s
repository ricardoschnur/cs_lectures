	.data
	.globl validate_checksum
validate_buf:
		.space 25
	.text

# -- validate_checksum --
# Argumente:
# a0 : Adresse einer Zeichenkette, die eine deutsche IBAN darstellt (22 Zeichen)
# Rueckgabewert:
# v0 : die berechnete Pruefsumme
validate_checksum:
	addu 	$sp, $sp, -4					# Lege Stack an
	sw		$ra, 0($sp)						# Sichere s0

	la 		$a1, validate_buf			# $a1 = validate_buf

# Schiebe BLZ+KNR nach vorne
	la		$t2, 21($a0)					#	$t2 = Endeadresse der IBAN
	la		$t3, 4($a0)						# $t3 = Leseadresse
	la		$t4, 0($a1)						# $t4 = Schreibadresse

	# Schiebe Zeichen von $t3 nach $t4
	lbu		$t5, 0($t3)
	sb		$t5, 0($t4)

	b 		val_head							# Sprung nach val_head

val_loop:
	addu	$t3, $t3, 1						# Erhöhe Leseadresse
	addu	$t4, $t4, 1						# Erhöhe Schreibadresse

	# Schiebe Zeichen von $t3 nach $t4
	lbu		$t5, 0($t3)
	sb		$t5, 0($t4)

val_head:
	bne 	$t3, $t2, val_loop		# Abbruchbedingung


# Schreibe Ländercode
	li $t2, 49
	sb $t2, 18($a1)
	li $t2, 51
	sb $t2, 19($a1)
	li $t2, 49
	sb $t2, 20($a1)
	li $t2, 52
	sb $t2, 21($a1)

# Schreibe Prüfziffer
	lbu		$t0, 2($a0)					# Schreibe Prüfziffer 1 in t0
	sb 		$t0, 22($a1)
	lbu		$t0, 3($a0)					# Schreibe Prüfziffer 2 in t0
	sb 		$t0, 23($a1)


# Berechne Rest
	la $a0, validate_buf
	li $a1, 24
	li $a2, 97
	jal modulo_str


# Räume auf
	lw		$ra, 0($sp)					# Restauriere s3
	addu 	$sp, $sp, 4					# Gebe Stack frei
	jr 		$ra
