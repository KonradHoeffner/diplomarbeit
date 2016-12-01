set terminal epslatex
# set terminal epslatex color
set output 'time_acc.tex'

#set rmargin 15
#set lmargin 3
#set yrange [0:75]
#set xrange [0:7]
set size 0.7,0.7
#set key left

set data style linespoints
#set xtics (0,1,2,3,4)
set xlabel "time in seconds" 
set ylabel "accuracy in percent" 1.5,0.0
#illiseconds\n(lower line = extraction, upper line = total)"

plot 'time_acc_normal.data' title "normal" lt 1, \
     'time_acc_sparql.data' title "SPARQL" lt 2
