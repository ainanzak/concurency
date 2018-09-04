package id.test.concurency.helper;

import org.apache.log4j.Logger;

public class LogUtils {
    private Logger logStream = Logger.getLogger("stream");
    private Logger logError = Logger.getLogger("error");
    private Logger logDebug = Logger.getLogger("debug");
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void info(Object message) {
        logStream.info(String.format("%s %s", id, message));
    }

    public void error(Object message, Throwable throwable) {
        logError.error(String.format("%s %s", id, message), throwable);
    }

    public void logDebugWriter(String str) {
        logDebug.debug(str);
    }
}
