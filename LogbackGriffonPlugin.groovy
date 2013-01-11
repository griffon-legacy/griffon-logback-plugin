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

/**
 * @author Andres Almiray
 */
class LogbackGriffonPlugin {
    // the plugin version
    String version = '0.1'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '1.2.0 > *'
    // the other plugins this plugin depends on
    Map dependsOn = [:]
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, qt
    List toolkits = []
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-logback-plugin'
    // Map of Bnd directives and/or Manifest entries
    // see http://www.aqute.biz/Bnd/Bnd for reference
    Map manifest = [
        'Bundle-Description': 'Replaces Log4j with Logback for logging'
    ]

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'Replaces Log4j with Logback for logging'
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
This plugin provides the same logging support as the standard Log4j plugin, but
using [Logback][1] instead. The current implementation is a port of the [logback][2]
plugin for Grails by [Burt Beckwith][3].

Usage
-----

The DSL for this plugin is very similar to the one for Log4j. Converting should
only require a few steps. For starters, change `log4j = {` `to logback = {` , e.g.

    logback = {
        error 'org.codehaus.griffon'
        info  'griffon.util',
              'griffon.core',
              'griffon.swing',
              'griffon.app'
    }

As you can see, defining logger levels is the same as for Log4j. One difference
is that there is no "fatal" level in Logback, so although you can use that in
the DSL it will be mapped to the "error" level.

You define appenders the same way, inside a `appenders block. Layouts are
supported in Logback but are mostly deprecated in favor of Encoders, so only
Encoders are supported in the DSL. The DSL supports the same four encoders as the
Log4j DSLs Layouts, i.e. 'xml', 'html', 'simple', and 'pattern'. 'pattern' is the
default and uses a syntax that is very similar to Log4j's, with a few differences.
See the [syntax documentation][4] for more information.

The DSL also supports the same Appenders as the Log4j DSL, i.e. 'null', 'console',
'file', 'rollingFile' and 'event'.

Here's an example of a more complex configuration. It overrides the default
`console` appender to change the layout pattern (note that the property is
`pattern` and not `conversionPattern as in Log4j), adds a FileAppender`, and
attaches the appender to the `griffon` package and sub-packages. In addition the
Root logger is updated to use the 'warn' level, and to have both the console
('stdout') and 'mylog' appenders:

    logback = {
        appenders {
            console name: 'stdout', encoder: pattern(pattern: '%d [%t] %-5p %c{2} %m%n')
            file name: 'mylog', file: '/tmp/my.log'
        }

        error 'org.codehaus.griffon'

        info mylog: 'griffon'

        root {
            warn 'stdout', 'mylog'
        }
    }

Configuration
-------------

This plugin requires changes to the following files

__griffon-app/conf/BuildConfig.groovy__

Exclude Log4j from the default dependencies. Search for the `inherits('global')`
block and change it to

    inherits("global") {
        excludes 'log4j', 'slf4j-log4j12'
    }

__griffon-app/conf/Config.groovy__

Instruct the application that `LogbackLogManagerFactory` should be used instead
of the default factory

    app.logManager.factory = 'org.codehaus.griffon.runtime.logging.LogbackLogManagerFactory'

### Issues

As you've seen, you can create Appenders and other components programmatically,
and you use a similar syntax as in the Log4j DSL (i.e. call the `appender` method
with your Appender as its argument). But the configuration process has changed
in Logback, and all components must have a `LoggingContext` set, and usually must
have their `start()` method called (analagous to the `activateOptions()` method in
Log4j). This is done for you when you use the DSL to create an Appender or
Encoder, but if you create your own you need an additional step. If you call the
`dslInit` method, it will set the `LoggingContext` and call `start()` for you
(and do other configuration steps that may be needed in the future). It returns
the object that is passed to it to keep the DSL simple.

[1]: http://logback.qos.ch/
[2]: http://grails.org/plugin/logback
[3]: http://burtbeckwith.com
[4]: http://logback.qos.ch/manual/layouts.html#conversionWord
'''
}
