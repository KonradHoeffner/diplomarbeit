#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'skosexpansion.eps'
set encoding iso_8859_15
#set autoscale

#set title "Tripel zu Properties"
#set xlabel "Anzahl der Tripel" 
#set ylabel "Anzahl der Properties" 

#set xrange [1:100000000]
#set yrange [1:10000000]

plot 'wild_boar.txt' using 1 title "Wild boar" lt 1, \
 'pig.txt' using 1 title "Pig" lt 2, \
 'elephant.txt' using 1 title "Elephant" lt 3, \
 'star_trek.txt' using 1 title "Star Trek" lt 4, \
 'outer_space.txt' using 1 title "Outer space" lt 5, \
 'ant.txt' using 1 title "Ant" lt 6, \
 'bird.txt' using 1 title "Bird" lt 7, \
 


#'frequencies_occurrences_words_de_3m.csv' using 1:2 title "Wortschatz, Normkorpus Deutsch 3M" lt 1, \
	'frequencies_occurrences_words_en_1m.csv' using 1:2 title "Wortschatz, Normkorpus Englisch 1M" lt 2
