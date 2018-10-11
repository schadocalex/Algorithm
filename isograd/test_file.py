#!/usr/bin/python3

# Use like this : ./test_file.py meilleur_dev_france_sept_2017_session_1/les_bureaux_salesforce/main.py
# input* and output* must be in the same folder

import sys
import os
from subprocess import check_output, CalledProcessError
from colorama import Fore, Back, Style

main = sys.argv[1]
DIR = os.path.dirname(main)
files = os.listdir(DIR)

# print(files)

inputs = sorted([x for x in files if 'input' in x])
outputs = sorted([x for x in files if 'output' in x])

for i in range(len(inputs)):
    expected_out = open(DIR + '/' + outputs[i]).read().rstrip()

    try:
        out = check_output(main + ' < ' + DIR + '/' + inputs[i], shell=True).decode("utf-8").rstrip()
    except CalledProcessError:
        sys.exit(0)
    
    if out == expected_out:
        print(Fore.GREEN + '[OK] ' + inputs[i] + ' -> ' + outputs[i])
    else:
        print(Fore.RED + '[XX] ' + inputs[i] + ' -> ' + outputs[i])
        print('Your code returns:')
        print(out)
        print('\nExpected output:')
        print(expected_out)
        print('\n')

    print(Style.RESET_ALL, end='')
