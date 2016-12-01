#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'skosexpansion2.eps'
set encoding iso_8859_15
#set autoscale

#set title "Tripel zu Properties"
#set xlabel "Anzahl der Tripel" 
#set ylabel "Anzahl der Properties" 

#set xrange [1:100000000]
#set yrange [1:10000000]

plot 'hospital_information_system.txt' using 1 title "Hospital Information system" lt 8, \
 'data_integrity.txt' using 1 title "Data integrity" lt 9, \
 'house.txt' using 1 title "House" lt 10, \
 'plum.txt' using 1 title "Plum" lt 11


#'frequencies_occurrences_words_de_3m.csv' using 1:2 title "Wortschatz, Normkorpus Deutsch 3M" lt 1, \
	'frequencies_occurrences_words_en_1m.csv' using 1:2 title "Wortschatz, Normkorpus Englisch 1M" lt 2
