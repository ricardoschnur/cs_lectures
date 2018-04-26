	.data
	.globl iban2knr
	.text
# -- iban2knr
# Argumente:
# a0: IBAN Puffer (22 Bytes)
# a1: BLZ Puffer (8 Bytes)
# a2: KNR Puffer (10 Bytes)
# Ausgaberegister: keine
# Funktion:
# Schreibt BLZ und KNR in die entsprechenden Puffer
iban2knr:
  move 	$s0, $a0		  # Sichere IBAN Puffer in $s0
	move 	$s1, $a1		  # Sichere BLZ Puffer in $s1
	move 	$s2, $a2		  # Sichere KNR Puffer in $s2
	move 	$s3, $ra		  # Sichere Rücksprungadresse in $s3

	# Schreibe BLZ in Buffer
	li		$a0, 8				# $a0 =	8
	la 		$a1, 4($s0)  	# Lade Startadresse der BLZ in $a1
	la		$a2, 0($s1)		# Lade Startadresse des BLZ Puffers in $a2
	jal 	store_bytes

	# Schreibe KNR in Buffer
	li		$a0, 10				# $a0 =	10
	la 		$a1, 12($s0) 	# Lade Startadresse der KNR in $a1
	la		$a2, 0($s2)		# Lade Startadresse des KNR Puffers in $a2
	jal 	store_bytes

	move 	$ra, $s3		  # Lade Rücksprungadresse
	jr		$ra


# -- store_bytes --
# Eingaberegister:
# a0: Anzahl der Stellen (von links)
# a1: Startadresse
# a2: Zieladresse
# Ausgaberegister: keine
# Funktion:
# Schreibt die ersten $a0 Bytes ab $a1 nach $a2
store_bytes:
	li 			$t0, 0						# $t0 = 1
	la 			$t1, 0($a1)				# Lade Startadresse in $t1
	la 			$t2, 0($a2)				# Lade Zieladresse in $t2

sb_loop:
	lb			$t3, 0($t1)				# Lade nächstes Byte
	sb			$t3, 0($t2)				# Speichere Byte
	addiu 	$t1, $t1, 1				# Lade Adresse des nächsten Bytes in $t1
	addiu 	$t2, $t2, 1				# Lade Zieladresse für nächstes Byte in $t2

	addiu 	$t0, $t0, 1				# Erhöhe $t0 um 1
	bne			$t0, $a0, sb_loop	# Überpüfe ob $t0 < $a0

sb_return:
	jr	$ra
