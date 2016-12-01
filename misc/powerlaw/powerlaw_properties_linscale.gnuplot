#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'powerlaw_properties_linscale.eps'
set encoding iso_8859_15

set data style linespoints

#set logscale

set title "Tripel zu Properties"
set xlabel "Anzahl Tripel" 
set ylabel "Anzahl Properties" 

#set yrange [1:1000000]
#set xrange [1:10000000]

plot 'propertyCountDistribution.csv' using 1:2 title "DBpedia Properties" lt 3 lw 5

