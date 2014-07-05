#!/bin/bash

usage="$(basename "$0") [-h] -- program to print dependency files in order
input:  list of 'name [list of dependencies]'
        type one line by one line and type empty line to finish input.
output: return a list of names, with dependencies listed first

where:
    -h  show this help text"

while getopts ':h:' option; do
  case "$option" in
    h) echo "$usage"
       exit
       ;;
    :) printf "missing argument for -%s\n" "$OPTARG" >&2
       echo "$usage" >&2
       exit 1
       ;;
  esac
done
shift $((OPTIND - 1))
javac Graph.java
java Graph
