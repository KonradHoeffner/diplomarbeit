#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
set terminal png
#set terminal
#set term postscript eps enhanced color
set output 'zipf_wortschatz_properties.png'
set encoding iso_8859_15

set logscale

#set title "Rang nach Häufigkeit"
set xlabel "Rang" 
set ylabel "Anzahl Sätze/Tripel (Frequenz)" 

set yrange  [1:100000000]
#set xrange [1:10000000]

plot 'propertyCount.csv' using 1 title "DBpedia Properties" lt 3, \
     'frequencies_words_de_3m.txt' using 1 title "Wortschatz, Normkorpus Deutsch 3M" lt 1, \
	'frequencies_words_en_1m.txt' using 1 title "Wortschatz, Normkorpus Englisch 1M" lt 2
