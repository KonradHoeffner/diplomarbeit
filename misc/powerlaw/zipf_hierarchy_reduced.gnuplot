#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'zipf_hierarchy_reduced.eps'
set encoding iso_8859_15

set logscale

#set title "Rang nach Haeufigkeit"
set xlabel "Rang" 
set ylabel "Anzahl Instanzen" 

#set yrange  [1:100000000]
#set xrange [1:10000000]

plot 'dbpediaontology.csv' using 1 title "DBpedia classes" lt 4, \
	'yago_reduced.csv' using 1:2 title "YAGO classes" lt 5
