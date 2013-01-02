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

import org.slf4j.Logger

import ch.qos.logback.classic.Level
import ch.qos.logback.core.Appender

/**
 * Based on org.codehaus.groovy.grails.plugins.log4j.RootLog4jConfig.
 *
 * @author Graeme Rocher
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class RootLogbackConfig {
    protected Logger root
    protected LogbackConfig config

    RootLogbackConfig(Logger root, LogbackConfig config) {
        this.root = root
        this.config = config
    }

    def debug(Object[] appenders = null) {
        setLevelAndAppender Level.DEBUG, appenders
    }

    def info(Object[] appenders = null) {
        setLevelAndAppender Level.INFO, appenders
    }

    def warn(Object[] appenders = null) {
        setLevelAndAppender Level.WARN, appenders
    }

    def trace(Object[] appenders = null) {
        setLevelAndAppender Level.TRACE, appenders
    }

    def all(Object[] appenders = null) {
        setLevelAndAppender Level.ALL, appenders
    }

    def error(Object[] appenders = null) {
        setLevelAndAppender Level.ERROR, appenders
    }

    def fatal(Object[] appenders = null) {
        setLevelAndAppender Level.ERROR, appenders
    }

    def off(Object[] appenders = null) {
        setLevelAndAppender Level.OFF, appenders
    }

    void setProperty(String s, o) {
        root."$s" = o
    }

    protected setLevelAndAppender(Level level, Object[] appenders) {
        root.level = level
        for (appenderName in appenders) {
            Appender appender
            if (appenderName instanceof Appender) {
                appender = appenderName
            } else {
                appender = config.appenders[appenderName?.toString()]
            }
            if (appender) {
                root.addAppender appender
            }
        }
    }
}
