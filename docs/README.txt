========
OVERVIEW
========
This project contains several examples of using Trovo's companion API to Elektron.  The project is organized as:

/bin (scripts to run the examples)
/src (source of the examples)
/lib (required JARs, including the trovo-elektron-api JAR)
/conf (log4j config)
/docs
	README.txt (this file)
	/api (javadocs for the API)
build.xml (ant build file optionally used to compile and run the examples)

========
EXAMPLES
========
You can see source code for the examples in /src.  Here's a summary:

BenchmarkCurveExample.java
- Retrieves a benchmark interest rate curve in one step; echoes updated interest rates to the console.

ElektronClientExample.java
- Listens to an ElektronClient to acquire market data; resulting messages are low-level objects received directly from Elektron.

ElektronConnectionExample.java
- Listens to an ElektronConnection to acquire market data; resulting messages are high-level objects that are consistently mapped, strongly typed, and validated.

ElektronMultiContributorExample.java
- Listens to an ElektronConnection to acquire market data from multiple contributors. Resulting messages will be limited by the access privileges of the underlying Elektron account.
- Resulting messages are high-level objects that are consistently mapped, strongly typed, and validated.

ElektronTraceFeedExample.java
- Listens to an ElektronConnection to acquire FINRA TRACE trade messages (will fail if related Elektron account is not provisioned for FINRA TRACE data).
- Attempts to retrieve both US Corporate and US Govt Agency trades.

============
RUN EXAMPLES
============
Each example takes a single argument specifying the user to connect to Elektron as.  If you are unsure what the correct user is, check with the administrator of your Elektron connection or try "root".

Scripts are provided to run each of the examples on multiple platforms.  For example, to run the ElektronClientExample example as user "jdoe" from a command prompt on Windows:
- >cd bin
- >elektron-client-example.bat jdoe

Or, to run from a terminal on Unix or Mac:
- >cd bin
- >./elektron-client-example jdoe

================================
COMPILING/RUNNING VIA APACHE ANT
================================
If you wish to do your own compiling and running, you can do so easily with Apache Ant, available at http://ant.apache.org.

By using the provided build.xml, you can compile (all platforms) with:
- >ant compile

And run with:
- >ant benchmark-curve-example -Duser=jdoe

==========
QUESTIONS?
==========
If you have questions, contact Trovo at ops@trovo.io.
