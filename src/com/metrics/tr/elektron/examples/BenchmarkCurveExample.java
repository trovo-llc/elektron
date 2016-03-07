package com.metrics.tr.elektron.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metrics.common.logging.LoggerInitializer;
import com.metrics.common.mapping.adapters.thomsonreuters.IBenchmarkCurve;
import com.metrics.common.mapping.adapters.thomsonreuters.TrBenchmarkCurve;
import com.metrics.tr.elektron.mapping.BenchmarkCurveMap;

/**
 * Retrieves a benchmark interest rate curve in one step; echoes updated interest 
 * rates to the console.
 * 
 * NOTE: an inventory of available curves is enumerated, see TrBenchmarkCurve.
 * 
 * @author trovo
 */
public class BenchmarkCurveExample {
	private static final Logger logger = LoggerFactory.getLogger(BenchmarkCurveExample.class);
	
	public static void main(String[] args) {
		// Initialize logging.
		LoggerInitializer.init();
		
		// Get user to login as.
		if (args == null || args.length == 0) {
			logger.error("Missing user. Please provide username as argument.  Example: ./benchmark-curve-example root");
			return;
		}
		String user = args[0];

		// Get a benchmark curve (enumerated type TrBenchmarkCurve lists available curves).
		BenchmarkCurveMap benchmarkMap = BenchmarkCurveMap.getInstance(user); 
		IBenchmarkCurve ust = benchmarkMap.getCurve(TrBenchmarkCurve.UST);
		
		// Wait until BenchmarkCurve is ready.
		for (int i = 0; i < 60; i++) {
			if (!ust.isReady()) {
				logger.info(i + "=== Benchmark curve is not ready =======================================");
				// A BenchmarkCurve will respond "not ready" until each constituent instrument is observed at least once.
				if (ust.hasMissingInitialEvents()) logger.info(ust.getMissingInitialEvents().toString());
				try {Thread.sleep(1000);} catch (Exception e) {}
			} else {
				logger.info(i + "=== Benchmark curve is ready ===========================================");
				// Echo list of constituent instruments to the console.
				logger.info("RIC chain = " + ust.getRicChain());
				logger.info("RIC list  = " + ust.getRicList());
				break;
			} 
		}
		
		// Echo specified tenors to the console for 60 seconds.
		for (int i = 0; i < 60; i++) {
			logger.info("Tenor = 2 years, rate = " + ust.interpolate(2.0) + "; Tenor = 10 years, rate = " + ust.interpolate(10.0));
			try {Thread.sleep(1000);} catch (Exception e) {}
		}
		
		// Terminate connection to Elektron.
		benchmarkMap.stopApp();
		
		logger.info("Completed " + BenchmarkCurveExample.class.getName());
	}
}
