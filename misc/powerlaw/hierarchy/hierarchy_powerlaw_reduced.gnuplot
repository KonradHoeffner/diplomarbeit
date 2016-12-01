#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'hierarchy_powerlaw_reduced.eps'
set encoding iso_8859_15
set logscale

#set title "Tripel zu Properties"
#set xlabel "Anzahl der Tripel" 
#set ylabel "Anzahl der Properties" 

#set xrange [1:100000000]
#set yrange [1:10000000]

plot 'frequencies_skos_reduced.csv' using 1:2 title "" lt 1, \
 'frequencies_yago_reduced.csv' using 1:2 title "" lt 2 ,\
 'frequencies_yago_expanded_reduced.csv' using 1:2 title "" lt 3
