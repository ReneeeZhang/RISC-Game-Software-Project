#!/bin/bash

for((i=0; i<$1; i++))
do
    gradle run-client &
done
