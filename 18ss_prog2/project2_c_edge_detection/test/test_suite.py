from test_case import *


TEST_SUITE = [
    GetPixelValueTestCase('public', 'small1', [(0, 0, 1), (2, 2, 9)]),
    ScaleImageTestCase('public', 'scale_image_1', 'scale_image_1'),
    ApplyThresholdTestCase('public', 'threshold1', 'threshold1', 1),
    ReadBrokenImageTestCase('public', 'imgbroken1'),
    ReadBrokenImageTestCase('public', 'imgbroken2'),
    ReadImageTestCase('public', 'small1', 'small1'),
    WriteImageTestCase('public', 'small1', 'small1', 'small1'),
    ConvolveTestCase('public', 'convolve1', 'convolve1', 'kernel1'),
    GradientMagnitudeTestCase('public', 'gm1', 'gm1_dx', 'gm1_dy', 'gm1'),
    MainTestCase('public', 'img_P', 100)
]
