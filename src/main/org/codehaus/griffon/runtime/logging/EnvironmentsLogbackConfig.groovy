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

package org.codehaus.griffon.runtime.logging

import griffon.util.Environment
import org.codehaus.griffon.runtime.logging.logback.LogLog

/**
 * Based on org.codehaus.groovy.grails.plugins.log4j.EnvironmentsLog4jConfig.
 *
 * @author Graeme Rocher
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class EnvironmentsLogbackConfig {

    protected LogbackConfig config

    EnvironmentsLogbackConfig(LogbackConfig config) {
        this.config = config
    }

    def development(Closure callable) {
        if (Environment.current == Environment.DEVELOPMENT) {
            config.invokeCallable callable
        }
    }

    def production(Closure callable) {
        if (Environment.current == Environment.PRODUCTION) {
            config.invokeCallable callable
        }
    }

    def test(Closure callable) {
        if (Environment.current == Environment.TEST) {
            config.invokeCallable callable
        }
    }

    def methodMissing(String name, args) {
        if (args && args[0] instanceof Closure) {
            // treat all method calls that take a closure as custom environment names
            if (Environment.current == Environment.CUSTOM && Environment.current.name == name) {
                config.invokeCallable args[0]
            }
        } else {
            LogLog.error "Method missing when configuring Logback: $name"
        }
    }
}
