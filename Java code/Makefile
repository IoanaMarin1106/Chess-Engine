
default: build

build:
	javac *.java

run:
	java Game

vs_fairymax:
	xboard -debug -fcp "make run" -scp "fairymax" -secondInitString "new\nrandom\nsd 2\n" -tc 5 -inc 2 -autoCallFlag true -mg 10 -sgf partide.txt -reuseFirst false

clean:
	rm -f *.class
