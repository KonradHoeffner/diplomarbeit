#set terminal latex
#set output "bench_write.tex"

#set terminal png nocrop enhanced font arial 8 
set terminal png size 960 640
#size 420,320 
set output 'evaluation.png'
set boxwidth 2.0 
set style fill   solid 1.00 border -1
set style histogram clustered gap 2 title  offset character 0, 0, 0
set datafile missing '-'
set style data histograms
#set xtics border in scale 1,0.5 nomirror rotate by -45  offset character 0, 0, 0 
#set xtics   ("0" 0.00000, "0.01" 1.00000, "0.1" 2.00000, "1" 3.00000)

#set ylabel "%"
#set xlabel "Iterations"

set grid nopolar
set grid noxtics nomxtics ytics nomytics noztics nomztics \
 nox2tics nomx2tics noy2tics nomy2tics nocbtics nomcbtics
set grid layerdefault   linetype 0 linewidth 1.000,  linetype 0 linewidth 1.000


#set key invert reverse Left outside 
set key  reverse Left outside 
set key autotitle columnheader

set multiplot
#######################
#COLUMNS 3 and 4 WHERE SWITCHED
#######################

set nokey

set size 0.33, 0.5
set origin 0.0, 0.5 
set title "Precision" 
set yrange [ 0.00000 : 1.0 ] noreverse nowriteback
plot 'no_precision'  using 2:xtic(1)  ti col ,  '' u 4 ti col , '' u 3 ti col , '' u 5 ti col
#############################
set size 0.33, 0.5
set origin 0.28, 0.5
set title "Recall" 
plot 'no_recall'  using 2:xtic(1)  ti col ,  '' u 4 ti col , '' u 3 ti col , '' u 5 ti col
#############################
set size 0.33, 0.5
set origin 0.56, 0.5
set title "F-Measure" 
plot 'no_fmeasure'  using 2:xtic(1)  ti col ,  '' u 4 ti col , '' u 3 ti col , '' u 5 ti col
 
#############################
#set size 0.25, 0.5
#set origin 0.75, 0.5
#set title "Pred. Accuracy" 
#plot 'no_accuracy'  using 2:xtic(1)  ti col ,  '' u 4 ti col , '' u 3 ti col , '' u 5 ti col

############################
############################
#COLUMNS 3 and 4 WHERE SWITCHED
############################
############################
set size 0.33, 0.5
set origin 0.0, 0.0 
set title "Precision" 
plot 'with_precision'  using 2:xtic(1)  ti col ,  '' u 3 ti col , '' u 4 ti col , '' u 5 ti col
############################
set size 0.33, 0.5
set origin 0.28, 0.0 
set title "Recall" 
plot 'with_recall'  using 2:xtic(1)  ti col ,  '' u 3 ti col , '' u 4 ti col , '' u 5 ti col
############################
set size 0.33, 0.5
set origin 0.56, 0.0 
set title "F-Measure" 
plot 'with_fmeasure'  using 2:xtic(1)  ti col ,  '' u 3 ti col , '' u 4 ti col , '' u 5 ti col

unset multiplot


