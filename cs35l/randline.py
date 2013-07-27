#!/usr/bin/python

import random, sys
from optparse import OptionParser

class randline:
    def __init__(self, filename):
        f = open(filename, 'r')
        self.lines = f.readlines()
        f.close()

    def chooseline(self):
        return random.choice(self.lines)

def main():
    version_msg = "%prog 2.0"
    usage_msg = """%prog [OPTION]... FILE

Output randomly selected lines from FILE."""

    parser = OptionParser(version=version_msg,
                          usage=usage_msg)
    parser.add_option("-n", "--numlines",
                      action="store", dest="numlines", default=1,
                      help="output NUMLINES lines (default 1)")
    parser.add_option("-u", "--unique",
                      action="store_true", dest="uPredicate", default=0,
                      help="output with same probability")
    parser.add_option("-w", "--without-replacement",
                      action="store_true", dest="wPredicate", default=0,
                      help="output without replacement")
    options, args = parser.parse_args(sys.argv[1:])

    try:
        numlines = int(options.numlines)
        uPredicate = int(options.uPredicate)
        wPredicate = int(options.wPredicate)
    except:
        parser.error("invalid NUMLINES: {0}".
                     format(options.numlines))
    if numlines < 0:
        parser.error("negative count: {0}".
                     format(numlines))
    if len(args) == 0:
        parser.error("wrong number of operands")

# Append multiple files
    i=0
    newList=[]
    while i<=len(args)-1:
        myfile=open(args[i],'r')
        oldList=myfile.readlines()
# When the last character of the line is not '\n', it appends '\n'.
        if oldList[len(oldList)-1][-1]!='\n':
            oldList[0]=oldList[0]+'\n'
        newList=newList+oldList
        i=i+1

# "-u" option
    if uPredicate==1:
# list->set->list removes dupplicates.
        newList=list(set(newList))
# watch out 'w+' option for creating a file.
    nf=open("newFile.txt",'w+')
    nf.writelines(newList)
    nf.close()
    input_file = "newFile.txt"

    try:
        generator = randline(input_file)
# "-w" option
        if wPredicate==1:
            if numlines > len(newList):
# watch out the syntax of print function. It should be parenthesized.
                print('Numlines should be <= {0}.'.format(len(newList))) 
                sys.exit()
            for index in range(numlines):
                theLine=generator.chooseline()
                sys.stdout.write(theLine)
                generator.lines.remove(theLine)
        else:
            for index in range(numlines):
                sys.stdout.write(generator.chooseline())
    except IOError as e:
        errno, strerror = e.args
        parser.error("I/O error({0}): {1}".
                     format(errno, strerror))

if __name__ == "__main__":
    main()
