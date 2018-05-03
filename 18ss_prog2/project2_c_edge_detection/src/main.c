#include <stdlib.h>
#include <stdio.h>

#include "argparser.h"
#include "convolution.h"
#include "derivation.h"
#include "gaussian_kernel.h"
#include "image.h"

int main(int const argc, char **const argv) {
	/**
	 * Parse arguments. The parsed image file name and threshold are available
	 * in the image_file_name and threshold variables (see argparser.h).
	 */
	parse_arguments(argc, argv);
	printf("Computing edges for image file %s with threshold %i\n", image_file_name, threshold);

	/**
	 * Read Image from given file. 
	 * If the input file is broken terminate with return value 1.
	 * Hint: The width and height of the image have to be accessible in the
	 * scope of this function. 
	 */
	// TODO: Implement me!

	/**
	 * Blur image with the given Gaussian kernel (see gaussian_kernel.h).
	 * Write the blurred image to the file out_blur.pgm
	 */
	// TODO: Implement me!

	/**
	 * Compute the derivation of the blurred image in x and y direction.
	 * Then write the rescaled results to the files out_d_x.pgm and out_d_y.pgm.
	 */
	// TODO: Implement me!

	/**
	 * Compute the gradient magnitude.
	 * Then write the rescaled result to the file out_gm.pgm
	 */
	// TODO: Implement me!

	/**
	 * Apply the threshold to the gradient magnitude.
	 * Then write the result to the file out_edges.pgm.
	 */
	// TODO: Implement me!
}
