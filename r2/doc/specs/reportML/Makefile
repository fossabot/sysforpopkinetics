include ../../webdir.mk
T = reportML
HTML_OPS = --nochunks
STYLE = ../../stylesheet
WEB = $(WEBDIR)/v0.1/specs/$T
PNG = $(wildcard *.png)
HTML = $(wildcard nonmem/html/*.html)

all:	nonmem_html html pdf rtf

nonmem_html:
ifndef JAVA_HOME
	$(error A necessary system environment variable, JAVA_HOME, is not defined. \
	To be specific, there has to be bin/javac under the directory pointed by JAVA_HOME. )
else
	rm -rf nonmem/html
	mkdir nonmem/html
	$(JAVA_HOME)/bin/javac -classpath ../tools/dtddoc/java/dtdparser119.jar:../tools/dtddoc/java/jakarta-oro-2.0.6.jar ../tools/dtddoc/java/DTDDoc.java
	java -classpath ../tools/dtddoc/java/dtdparser119.jar:../tools/dtddoc/java/jakarta-oro-2.0.6.jar:.:../tools/dtddoc/java DTDDoc $T.dtd nonmem/html 
endif

html:	$T.html

pdf:	$T.pdf

rtf:	$T.rtf

%.html : $T.xml
	docbook2html --dsl $(STYLE)/local.dsl#html $(HTML_OPS)  $<

%.pdf : $T.xml
	docbook2pdf --dsl $(STYLE)/local.dsl#print $<

%.rtf : $T.xml
	docbook2rtf --dsl $(STYLE)/local.dsl#print $<

install:
	scp $T.html $(WEB)
	scp nonmem/reportML_nonmem.xml $(WEB)/nonmem
	scp nonmem/html/*.html $(WEB)/nonmem/html

	scp $T.pdf  $(WEB)
	for i in $(PNG); do \
	     scp $$i $(WEB); \
	done

clean:
	rm -f $T.pdf $T.rtf $T.html
	rm -rf nonmem/html
