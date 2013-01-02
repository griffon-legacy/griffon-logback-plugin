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

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import static griffon.util.GriffonExceptionHandler.sanitize;

/**
 * A Logback adapter that produces cleaner, more informative stack traces.
 * Based on org.slf4j.impl.GrailsLog4jLoggerAdapter.
 *
 * @author Graeme Rocher
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class LogbackLoggerFactory implements ILoggerFactory, StatusListener {

    protected LoggerContext loggerContext = new LoggerContext();

    public LogbackLoggerFactory() {
        loggerContext.setName(CoreConstants.DEFAULT_CONTEXT_NAME);
        loggerContext.getStatusManager().add(this);
    }

    public Logger getLogger(String name) {
        return new LogbackLoggerAdapter(loggerContext.getLogger(name));
    }

    public void reset() {
        loggerContext.reset();
        loggerContext.getStatusManager().add(this);
    }

    public LoggerContext getLoggerContext() {
        return loggerContext;
    }

    public void addStatusEvent(Status status) {
        if (status.getEffectiveLevel() >= Status.WARN) {
            System.err.println(status);
            if (status.getThrowable() != null) {
                sanitize(status.getThrowable()).printStackTrace(System.err);
            }
        }
    }
}
