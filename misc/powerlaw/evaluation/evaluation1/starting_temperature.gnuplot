set term postscript eps enhanced color
set output 'starting_temperature.eps'
set encoding iso_8859_15

#set logscale

#set title "Rang nach Haeufigkeit"
set xlabel "Anfangstemperatur" 
set ylabel "Präzision" 

set yrange  [0:0.3]
#set xrange [0:1]
set data style linespoints

plot   'starting_temperature.csv' using 1:2  title "" lt 1 lw 2
#plot    'cooling_factor.csv' using 1:2 title "Präzision der Disambiguierung" lt 1, \

