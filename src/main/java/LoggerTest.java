import reporting.MyLogger;

public class LoggerTest {
	public static void main(String[] args) {

		MyLogger.trace("Trace message");
		MyLogger.debug("Debug message");
		MyLogger.info("Info message");
		MyLogger.warn("Warn message");
		MyLogger.error("Error message!!!");
		MyLogger.fatal("Fatal message!!!!!");

		try {
			throw new RuntimeException("");
		} catch (Exception e) {
			MyLogger.error("Exception!", e);
		}

	}
}
