HOW TO CREATE THE INPUT FILES

- 0. download and unpack the following files from http://wiki.dbpedia.org/Downloads: 
disambiguations_en.nt  labels_en.nt  long_abstracts_en.nt  redirects_en.nt
- 1. convert these files to csv (comma separated values format, however we use the tabulator as separator) with "cat filename.nt | nt2csv > filename.csv"
- 2. urldecode the urls:
- urldecode redirects.csv and disambiguations.csv via "cat filename.csv | urldecode > filename.tmp" and then remove the csv and rename the tmp to csv
3 - with labels_en.nt and long_abstracts_en.nt we have a problem because the second row is a literal and not url-
 but java escaped. 
new method: use wortschatz2dbpedia.util.DualDecode for this
#- 3. old method
#fortunately there exists the tools "cut" and "paste" which can split rows and put them back  together, #thus we do:
#cut -f1 labels_en.csv > labels_en.row1
#cut -f2 labels_en.csv > labels_en.row2
#cat labels_en.row1 | urldecode > labels_en.row1_decoded
#java -classpath *root directory of the class files* wortschatz2dbpedia.util.Unquote labels_en.row2 #labels_en.row2_decoded
#paste labels_en.row1_decoded labels_en.row2_decoded labels_en_decoded.csv
#(same procedure for the other file)
#WARNING: for me paste did not work correctly with  the dbpedia35 input file (check with wc -l if the file has the #same number of lines as the two columns). in this case use wortschatz2dbpedia.util.Paste (syntax Paste file1 file2)
4.sort the abstractfile with "LC_COLLATE=C sort --key=1 --field-separator=\t"
5. (optional) casecorrect the abstract file with wortschatz2dbpedia.util.CaseCorrect labelinputfile labeloutputfile abstractfile

As they are unix-shell scripts, if you use windows you need something like cygwin(?) which gives you a unix-like shell along with the most used tools. called tools like sed, cut and paste should be preinstalled or easily installable in most linux distributions.

