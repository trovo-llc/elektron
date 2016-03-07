package com.metrics.tr.elektron.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metrics.common.logging.LoggerInitializer;
import com.metrics.tr.elektron.ElektronResponseMsg;
import com.metrics.tr.elektron.client.ElektronClient;
import com.metrics.tr.elektron.client.IElektronAppBroadcaster.ElektronMessageType;
import com.metrics.tr.elektron.client.IElektronAppListener;
import com.metrics.tr.elektron.instruments.InstrumentMap.InstrumentType;

/**
 * Listens to an ElektronClient to acquire market data; 
 * resulting messages are low-level objects received
 * directly from Elektron.
 * 
 * @author trovo
 */
public class ElektronClientExample {
	private static final Logger logger = LoggerFactory.getLogger(ElektronClientExample.class);
	
	public static void main(String... args) {
		// Initialize logging.
		LoggerInitializer.init();
		
		// Get user to login as.
		if (args == null || args.length == 0) {
			logger.error("Missing user. Please provide username as argument.  Example: ./elektron-client-example root");
			return;
		}
		String user = args[0];
		
		try {
			// Attempt to connect to Elektron using low-level singleton ElektronClient.
			final ElektronClient elektronClient = ElektronClient.getInstance(user);
			
			// Add a listener to echo messages to the console (note that messages are maps of raw Elektron content).
			elektronClient.addListener(new IElektronAppListener() {

				@Override
				public void listen(ElektronMessageType msgType, Object addendum) {
					
					// Echo message type to console.
					logger.info(msgType.name());
					switch (msgType) {
					
					// Elektron responded with a new message... details are in the addendum.
					case NEW_RESPONSE_MSG:	
						try {
							ElektronResponseMsg response = (ElektronResponseMsg) addendum;
							logger.info(response.getDataMap().toString());
						} catch (Exception e) {
							logger.error("Problem processing response.", e);
						}
						break;
					
					// Elektron session is being terminated.
					case STOP_APP:
						// No code... listeners are automatically removed upon termination of the Elektron session.
						break;
					}
				}
				
			});
			
			// Add streaming data requests -- add or change items to test Elektron connection.								
			elektronClient.addInstruments(true, InstrumentType.CLIENT_STREAMING, "US10YT=RRPS", "US3MT=RRPS", "/IBM.N", "/C.N");
			
			// Wait for 30 seconds (messages received will appear in console).
			for (int i = 0; i < 30; i++) {Thread.sleep(1000);}			
	
		} catch (Exception e) {
			logger.error("Problem running example.", e);
		} finally {
			try {
				// Terminate connection to Elektron.
				ElektronClient.getInstance().stopApp();
			} catch (Exception e) {
				logger.error("Problem terminating connection.", e);
			}
		}

		logger.info("Completed " + ElektronClientExample.class.getName());
	}
	
}
