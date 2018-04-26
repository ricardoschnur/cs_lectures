	.data
	.globl validate_checksum
	.text

# -- validate_checksum --
# Argumente:
# a0 : Adresse einer Zeichenkette, die eine deutsche IBAN darstellt (22 Zeichen)
# Rueckgabewert:
# v0 : die berechnete Pruefsumme

# Sei x = BLZ concat KNR concat 1314 concat Prüfziffer.
# Sei y = BLZ concat KNR und z = Prüfziffer.
# Dann gilt: x mod 97 = (y * 10^6 + 1314 *10^2 + z) mod 97
#											= [ (y mod 97) * 27 + 62 + (z mod 97) ] mod 97
validate_checksum:

# Berechne $v0 = y mod 97
	li 		$a2, 97								# $a2 = 97
	la		$t0, 4($a0)						# $t0 = Startadresse
	la 		$t1, 0($t0)
	addiu	$t1, $t1, 17					# $t0 = Zieladresse

	lbu		$v0, 0($t0)						# $v0 = cn in ASCII
	addiu $v0, $v0, -48					# ASCII zu Ziffer
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x

	b 		mod_head							# Sprung nach mod_head

mod_loop:
	addu	$t0, $t0, 1						# Setze Adresse auf nächsten Koeffizienten

	lbu		$t2, 0($t0)						# $t2 = ci in ASCII
	addiu $t2, $t2, -48					# ASCII zu Ziffer
	remu 	$t2, $t2, $a2					# $t2 = $t2 mod x

	mulu 	$v0, $v0, 10					# $v0 = 10 * $v0
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x
	addu 	$v0, $v0, $t2					# $v0 = $v0 + ci
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x

mod_head:
	bne 	$t0, $t1, mod_loop		# Abbruchbedingung


# Berechne $v0 = (27 * $v0 + 62) mod 97
	mulu 	$v0, $v0, 27					# $v0 = 27 * $v0
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x
	addiu $v0, $v0, 62					# $v0 = 27 * $v0
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x


# Berechne $t0 = z mod 97
	la		$t0, 2($a0)						# $t0 = Prüfsumme Ziffer 1
	la		$t1, 3($a0)						# $t1 = Prüfsumme Ziffer 2
	lbu		$t0, 0($t0)						# $t0 = P1 in ASCII
	lbu		$t1, 0($t1)						# $t0 = P1 in ASCII
	addiu $t0, $t0, -48					# ASCII zu Ziffer
	addiu $t1, $t1, -48					# ASCII zu Ziffer
	mulu	$t0, $t0, 10					# $t0 = 10 * $t0
	addu 	$t0, $t0, $t1					# $t0 = $t0 + $t1
	remu 	$t0, $t0, $a2					# $t0 = $t0 mod x


# Berechne 	$v0 = ($v0 + $t0) mod 97
	addu 	$v0, $v0, $t0					# $v0 = $v0 + $t0
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x


	jr 		$ra
