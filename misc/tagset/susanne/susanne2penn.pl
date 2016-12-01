#!/usr/bin/perl -w

#input:
# A19:0010a       -       YB      <minbrk>        -       [Oh.Oh]
# A19:0010b       -       AT      The     the     [O[S[Nns:s.
# A19:0010c       -       NP1s    Baltimore       Baltimore       [NP1s&.
# A19:0010d       -       CC      and     and     [NP1g+.

# task:
# convert into a phrase structure tree repr

# algo:
# take the fourth col and insert it into the \. in the last col

my $brack = '[0-9A-Za-z\&\@:\+=\-]*\s*';
while (<>)
{
  chomp;
  my @line = split;

  # remove conflicts with sexp output
  $line[3] =~ s/\(/-LBR-/g;
  $line[3] =~ s/\)/-RBR-/g;
  $line[2] =~ s/\(/-LBR-/g;
  $line[2] =~ s/\)/-RBR-/g;

  # end of sentence
  if ($line[5] eq '.O]') { $line[5] =~ s/$/\n/o; }
  elsif ($line[5] =~ /\.Oh\]$/) { $line[5] =~ s/$/\n/o;  }

  # insert word and change parens to sexp
  $line[5] =~ s/\./ ($line[2] $line[3]) /;
  $line[5] =~ s/\[/ ( /go;
  $line[5] =~ s/$brack\]/ ) /go;
  $line[5] =~ s/^\s*//o;

  print $line[5];
}
print "\n";
