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

import ch.qos.logback.classic.log4j.XMLLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class XmlEncoder extends LayoutWrappingEncoder<ILoggingEvent> {

    protected boolean locationInfo;
    protected boolean properties;

    public void setLocationInfo(boolean b) {
        locationInfo = b;
    }

    public void setProperties(boolean b) {
        properties = b;
    }

    @Override
    public void start() {
        XMLLayout xmlLayout = new XMLLayout();
        xmlLayout.setContext(context);
        xmlLayout.setLocationInfo(locationInfo);
        xmlLayout.setProperties(properties);

        xmlLayout.start();
        layout = xmlLayout;
        super.start();
    }
}
