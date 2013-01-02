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

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.status.ErrorStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class MemoryAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    protected Encoder<ILoggingEvent> encoder;
    protected ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Override
    public void start() {
        try {
            encoder.init(outputStream);
            super.start();
        } catch (IOException e) {
            started = false;
            addStatus(new ErrorStatus("Failed to initialize encoder for appender named [" + name + "].", this, e));
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }

        try {
            event.prepareForDeferredProcessing();
            encoder.doEncode(event);
        } catch (IOException ioe) {
            started = false;
            addStatus(new ErrorStatus("IO failure in appender", this, ioe));
        }
    }

    public void setEncoder(Encoder<ILoggingEvent> e) {
        encoder = e;
    }

    public String getRenderedOutput() {
        return new String(outputStream.toByteArray());
    }
}
