This project contains several examples of using Trovo's companion API to Elektron.  The project is organized as:

/src (source of the examples)
/lib (required JARs, including the trovo-elektron-api JAR)
/config (log4j config)
/docs
	README.txt (this file)
	/api (javadocs for the API)
build.xml (ant build file used to compile and run the examples)

To run the examples, you must install Apache Ant, available at: http://ant.apache.org

Each example takes a single argument specifying the user to connect to Elektron as.  If you are unsure what the correct user is, check with the administrator of your Elektron connection or try "root" (build.xml defaults to "root").

For example, to run the ElektronClientExample as user "jdoe":
- >ant run-client -Duser=jdoe

Or, to run as user "root" (the default), simply:
- >ant run-client

Each example uses its own ant target:

BenchmarkCurveExample
- >ant run-bench

ElektronConnectionExample
- >ant run-conn

ElektronMultiContributorExample
- >ant run-multi

ElektronTraceFeedExample
- >ant run-trace

ElektronClientExample
- >ant run-client


If you have questions, contact Trovo at ops@trovo.io.
