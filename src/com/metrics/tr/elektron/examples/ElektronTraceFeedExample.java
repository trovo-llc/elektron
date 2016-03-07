package com.metrics.tr.elektron.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metrics.common.jobcontrol.IMarketEventBroadcaster;
import com.metrics.common.jobcontrol.IMarketEventListener;
import com.metrics.common.logging.LoggerInitializer;
import com.metrics.common.mapping.IBondMarketUpdate;
import com.metrics.common.mapping.IMarketEvent;
import com.metrics.tr.elektron.mapping.ElektronConnection;

/**
 * Listens to an ElektronConnection to acquire FINRA TRACE trade
 * messages (will fail if related Elektron account is not provisioned
 * for FINRA TRACE data).
 * 
 * Attempts to retrieve both US Corporate and US Govt Agency trades.
 * 
 * @author trovo
 */
public class ElektronTraceFeedExample {
	private static final Logger logger = LoggerFactory.getLogger(ElektronTraceFeedExample.class);
	
	public static void main(String... args) {
		// Initialize logging.
		LoggerInitializer.init();
	
		// Get user to login as.
		if (args == null || args.length == 0) {
			logger.error("Missing user. Please provide username as argument.  Example: ./elektron-trace-feed-example root");
			return;
		}
		String user = args[0];
		
		try {
			// Attempt to connect to Elektron using the high-level singleton ElektronConnection.
			ElektronConnection connection = ElektronConnection.getInstance(user);
			
			// Add a listener to echo messages to the console (note that messages are strongly typed).
			connection.addOrdinaryListener(new IMarketEventListener() {
				@Override
				public void listen(IMarketEvent event) {
					logger.info("==================================================================");
					logger.info("event type " + event.getClass().getName());
					if (event instanceof IBondMarketUpdate) {
						IBondMarketUpdate mktUpdate = (IBondMarketUpdate) event;
						logger.info(" identifier   = " + mktUpdate.getInstrumentId());
						logger.info(" tenor        = " + mktUpdate.getTenor());
						logger.info(" maturity     = " + mktUpdate.getMaturity());
						logger.info(" price        = " + mktUpdate.getPrice());
						logger.info(" yield        = " + mktUpdate.getYield());
						logger.info(" contributor  = " + mktUpdate.getContributor());
						logger.info(" trade time   = " + mktUpdate.getTradeTime());
						logger.info(" created time = " + mktUpdate.getCreatedTime());
					}
				}
	
				// Elektron session is being terminated.
				@Override
				public void terminate(IMarketEventBroadcaster broadcaster) {
					// No code... listeners are automatically removed upon termination of the Elektron session.
				}
			});
			
			// Add streaming data requests -- BTDS = Corporate Bonds, ATDS = Agency Debt.
			
			connection.addStreamingRequests("TRACEBTDS.FINR", "TRACEATDS.FINR");	
			
			// Wait for 30 seconds (messages received will appear in console).
			for (int i = 0; i < 30; i++) {Thread.sleep(1000);}	

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Terminate connection to Elektron.
			ElektronConnection.getInstance().stopApp();
		}
		
		logger.info("Completed " + ElektronConnectionExample.class.getName());
	}
}
