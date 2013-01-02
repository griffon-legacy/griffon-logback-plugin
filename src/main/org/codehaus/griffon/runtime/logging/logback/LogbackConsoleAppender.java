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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.pattern.PatternLayoutBase;
import groovy.util.ConfigObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static griffon.util.GriffonExceptionHandler.sanitize;

/**
 * Appends to the GrailsConsole instance in dev mode.
 * <p/>
 * Based on org.codehaus.groovy.grails.plugins.log4j.appenders.GrailsConsoleAppender.
 *
 * @author Graeme Rocher
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class LogbackConsoleAppender extends AppenderBase<ILoggingEvent> {
    protected static final String MESSAGE_PREFIX = "Message: ";
    protected PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    protected ByteArrayOutputStream os = new ByteArrayOutputStream();

    public LogbackConsoleAppender(ConfigObject config) {

    }

    public void setLayout(Layout<ILoggingEvent> layout) {
        if (layout instanceof PatternLayoutBase) {
            encoder.setPattern(((PatternLayoutBase<?>) layout).getPattern());
        }
    }

    @Override
    public void start() {
        encoder.setContext(getContext());
        encoder.start();
        try {
            encoder.init(os);
        } catch (IOException ignored) {
            // can't happen with ByteArrayOutputStream
        }
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        Level level = event.getLevel();
        String message = buildMessage(event);
        if (level.isGreaterOrEqual(Level.ERROR)) {
            System.err.println(message);
        } else {
            System.out.println(message);
        }
    }

    protected String buildMessage(ILoggingEvent event) {
        StringWriter w = new StringWriter();
        if (event.getThrowableProxy() instanceof ThrowableProxy) {
            Throwable throwable = ((ThrowableProxy) event.getThrowableProxy()).getThrowable();
            if (throwable != null) {
                w.append(MESSAGE_PREFIX).append(throwable.getMessage()).append(CoreConstants.LINE_SEPARATOR);
                sanitize(throwable);
                throwable.printStackTrace(new PrintWriter(w));
            }
        }
        w.flush();

        return w.toString();
    }

    protected synchronized String format(ILoggingEvent event) {
        try {
            encoder.doEncode(event);
        } catch (IOException ignored) {
            // can't happen with ByteArrayOutputStream
        }

        String formatted = new String(os.toByteArray());
        os.reset();

        return formatted;
    }
}
