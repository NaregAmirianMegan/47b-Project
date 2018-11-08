#!/bin/csh
limit cputime 120

set CASE_NAME = "$1"
set SOLVABLE = "$2"
set SOLVER_CLASS = "$3"
set INPUT_FILE = "$4"
set GOAL_FILE = "$5"


/bin/rm -f /tmp/out$$

echo "Trying $CASE_NAME"
if ($SOLVABLE == 1) then
	java $SOLVER_CLASS $INPUT_FILE $GOAL_FILE > /tmp/out$$
	if ($? != 0) then
		echo "*** Wrong solver exit status (expect 0 but got $?)"
	endif
	java -cp libs Checker $INPUT_FILE $GOAL_FILE < /tmp/out$$
	if ($? != 0 ) then
		echo "*** Incorrect solver output"
	endif
else
	java $SOLVER_CLASS $INPUT_FILE $GOAL_FILE
	if ($? != 1) then
		echo "*** Wrong solver exit status (expect 1 but got $?)"
	else
		echo "Verified"
	endif
endif
	

/bin/rm -f /tmp/out$$
echo ""