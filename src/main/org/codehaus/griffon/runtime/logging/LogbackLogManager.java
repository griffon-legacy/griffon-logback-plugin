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

package org.codehaus.griffon.runtime.logging;

import griffon.core.ApplicationHandler;
import griffon.core.GriffonApplication;
import griffon.util.logging.LogManager;
import groovy.lang.Closure;
import groovy.util.ConfigObject;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Andres Almiray
 */
public class LogbackLogManager implements LogManager, ApplicationHandler {
    private final GriffonApplication app;

    public LogbackLogManager(GriffonApplication app) {
        this.app = app;
    }

    public GriffonApplication getApp() {
        return app;
    }

    public void configure(ConfigObject config) {
        Object logbackConfig = config.get("logback");
        if (logbackConfig instanceof Closure) {
            try {
                java.util.logging.LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(".level=INFO".getBytes()));
            } catch (IOException e) {
                // ignore
            }
            SLF4JBridgeHandler.install();
            try {
            LogbackConfig.initialize(config);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
