#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
set terminal png
#set term postscript eps enhanced


set output 'powerlaw.png'

#set rmargin 15
#set lmargin 3
set yrange [1:200000]
set xrange [1:3000000]
#set size 0.7,0.7
#set key left
set logscale


#set data style linespoints
#set xtics (0,1,2,3,4)
set xlabel "Rank" 
set ylabel "Node Indegree" 
#milliseconds\n(lower line = extraction, upper line = total)"

plot 'clean.infoboxdegree.data' title "infobox generic" lt 1, \
     'clean.ontodegree.data' title "infobox mapping-based" lt 2 , \
     'clean.pagelinks.data' title "Wikipedia pagelinks" lt 3
     
     
    
