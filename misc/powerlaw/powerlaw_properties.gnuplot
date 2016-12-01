#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
set terminal png
#set terminal
set term postscript eps enhanced color
set output 'powerlaw_properties.eps'
set encoding iso_8859_15
set logscale

set title "Tripel zu Properties"
set xlabel "Anzahl der Tripel" 
set ylabel "Anzahl der Properties" 

set xrange [1:100000000]
set yrange [1:10000000]

plot 'propertyCountDistribution.csv' using 1:2 title "DBpedia Properties" lt 3

#'frequencies_occurrences_words_de_3m.csv' using 1:2 title "Wortschatz, Normkorpus Deutsch 3M" lt 1, \
	'frequencies_occurrences_words_en_1m.csv' using 1:2 title "Wortschatz, Normkorpus Englisch 1M" lt 2
