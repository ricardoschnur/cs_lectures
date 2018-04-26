	.data
	.globl modulo_str
	.text

# --- modulo_str ---
# Arguments:
# a0: Startadresse des Puffers
# a1: Anzahl der Zeichen im Puffer
# a2: Der Teiler
# Rueckgabewert:
# v0: Die im Puffer [$a0 bis $a0 + $a1 - 1] darstellte Dezimalzahl (kodiert in ASCII Ziffern '0' bis '9') modulo $a2
modulo_str:
	la 		$t1, 0($a0)
	addu	$t1, $t1, $a1
	addiu	$t1, $t1, -1					# $t1 = Zieladresse

	lbu		$v0, 0($a0)						# $v0 = cn in ASCII
	addiu $v0, $v0, -48					# ASCII zu Ziffer
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x

	b 		mod_head							# Sprung nach mod_head

mod_loop:
	addu	$a0, $a0, 1						# Setze Adresse auf nächsten Koeffizienten

	lbu		$t2, 0($a0)						# $t2 = ci in ASCII
	addiu $t2, $t2, -48					# ASCII zu Ziffer
	remu 	$t2, $t2, $a2					# $t2 = $t2 mod x

	mulu 	$v0, $v0, 10					# $v0 = 10 * $v0
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x
	addu 	$v0, $v0, $t2					# $v0 = $v0 + ci
	remu 	$v0, $v0, $a2					# $v0 = $v0 mod x

mod_head:
	bne 	$a0, $t1, mod_loop		# Abbruchbedingung
	jr 		$ra
