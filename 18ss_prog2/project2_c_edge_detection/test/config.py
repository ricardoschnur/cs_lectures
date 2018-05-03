import argparse

from timeout_error import TimeoutError


VERBOSE = True

def get_argparser():
    argparser = argparse.ArgumentParser()
    argparser.add_argument('-f', '--filter', type=str, metavar='<regex>', help='only execute tests matching this regex')
    argparser.add_argument('-l', '--list', action='store_true', help='only list tests, don\'t execute')
    return argparser

def run_tests(args, all_tests):
    num_passed = 0
    for t in all_tests:
        try:
            msg = t.run_test()
            print 'Running test', t.get_name()
            if msg is None:
                print("PASS")
                num_passed += 1
            else:
                print("FAIL: %s" % msg)
        except TimeoutError:
            print("FAIL: time out")
    print 'Passed {:d} out of {:d} tests.'.format(num_passed, len(all_tests))
