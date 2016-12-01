#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'zipf_wortschatz_with_expected_values_reduced.eps'
set encoding iso_8859_15

set logscale

#set title "Rang nach Haeufigkeit"
set xlabel "Rang" 
set ylabel "Anzahl Sätze (Frequenz)" 

set yrange  [1:3000000]
set xrange [1:10000000]

#set y2range  [1:20]
#set y2tics 0, 0.2
#set ytics nomirror

zipf1(x)=1260685/x

unset title
plot   zipf1(x) lt 7 lw 6 title '1260685/x',\
	'frequencies_words_en_1m_xy_reduced.csv' using 1:2 title "Wortschatz, Normkorpus Englisch 1M" lt 2 lw 3
	
