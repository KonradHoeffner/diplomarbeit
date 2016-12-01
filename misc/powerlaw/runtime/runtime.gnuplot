#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'runtime.eps'
set encoding iso_8859_15
#set logscale
set data style linespoints
#set title "Tripel zu Properties"
set xlabel "filesize in kb" 
set ylabel "time in ms" 

#set xrange [1:90000]
#set yrange [0:1000000]

plot 'runtime.txt' using 1:2 title "AllTest runtime" lt 3

#'frequencies_occurrences_words_de_3m.csv' using 1:2 title "Wortschatz, Normkorpus Deutsch 3M" lt 1, \
	'frequencies_occurrences_words_en_1m.csv' using 1:2 title "Wortschatz, Normkorpus Englisch 1M" lt 2
