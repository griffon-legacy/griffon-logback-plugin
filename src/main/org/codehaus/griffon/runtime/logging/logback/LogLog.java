/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.griffon.runtime.logging.logback;

import java.io.PrintStream;

import static griffon.util.GriffonExceptionHandler.sanitize;

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class LogLog {

    public static final String DEBUG_KEY = "logback.debug";

    protected static final String PREFIX = "logback: ";
    protected static final String ERR_PREFIX = "logback:ERROR ";
    protected static final String WARN_PREFIX = "logback:WARN ";

    protected static boolean debugEnabled = false;
    protected static boolean quietMode = false;

    static {
        String key = getSystemProperty(DEBUG_KEY, null);
        if (key != null) {
            debugEnabled = !"false".equals(key.trim());
        }
    }

    public static void setInternalDebugging(boolean enabled) {
        debugEnabled = enabled;
    }

    public static void setQuietMode(boolean quiet) {
        quietMode = quiet;
    }

    public static void debug(String message) {
        debug(message, null);
    }

    public static void debug(String message, Throwable t) {
        if (debugEnabled) {
            log(message, t, false, PREFIX);
        }
    }

    public static void error(String message) {
        error(message, null);
    }

    public static void error(String message, Throwable t) {
        log(message, t, true, ERR_PREFIX);
    }

    public static void warn(String message) {
        warn(message, null);
    }

    public static void warn(String message, Throwable t) {
        log(message, t, true, WARN_PREFIX);
    }

    public static void log(String message, Throwable t, boolean error, String prefix) {
        if (quietMode) {
            return;
        }

        PrintStream ps = error ? System.err : System.out;

        ps.println(prefix + message);
        if (t != null) {
            sanitize(t).printStackTrace(ps);
        }
    }

    protected static String getSystemProperty(String key, String defaultValue) {
        try {
            return System.getProperty(key, defaultValue);
        } catch (Throwable e) { // MS-Java throws com.ms.security.SecurityExceptionEx
            debug("Was not allowed to read system property \"" + key + "\".");
            return defaultValue;
        }
    }
}
