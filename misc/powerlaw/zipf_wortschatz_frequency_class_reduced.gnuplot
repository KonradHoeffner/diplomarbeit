#set terminal epslatex
#set terminal epslatex color
#set output 'time_acc.tex'
#set terminal svg
#set terminal png
#set terminal
set term postscript eps enhanced color
set output 'zipf_wortschatz_frequency_class_reduced.eps'
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

class0=1260685
class1=630342
class2=315171
class3=157585
class4=78792
class5=39396
class6=19698
class7=9849
class8=4924
class9=2462
class10=1231
class11=615
class12=307
class13=153
class14=76
class15=38
class16=19
class17=9
class18=4
class19=2
class20=1

unset title
plot   'frequencies_words_en_1m_xy_reduced.csv' using 1:2 title "Wortschatz, Normkorpus Englisch 1M" lt 2,\
	class0 lt 3  notitle, class1 lt 3 notitle, class2 lt 3 notitle, class3 lt 3 notitle, class4 lt 3 notitle, class5 lt 3 notitle, class6 lt 3 notitle, class7 lt 3 notitle, class8 lt 3 notitle, class9 lt 3 notitle, class10 lt 3 notitle, class11 lt 3 notitle, class12 lt 3 notitle, class13 lt 3 notitle, class14 lt 3 notitle, class15 lt 3 notitle, class16 lt 3 notitle, class17 lt 3 notitle, class18 lt 3 notitle, class19 lt 3 notitle, class20 lt 3 notitle

