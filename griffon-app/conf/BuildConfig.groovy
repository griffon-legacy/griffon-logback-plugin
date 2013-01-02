griffon.project.dependency.resolution = {
    inherits('global') {
        excludes 'log4j', 'slf4j-log4j12'
    }
    log "warn"
    repositories {
        griffonHome()
        mavenCentral()
    }
    dependencies {
        compile('ch.qos.logback:logback-classic:1.0.9') {
            excludes 'dom4j', 'fest-assert', 'geronimo-jms_1.1_spec', 'greenmail','groovy-all', 'h2',
                     'hsqldb', 'integration', 'janino', 'junit', 'log4j-over-slf4j', 'logback-core',
                     'mail', 'mysql-connector-java', 'org.apache.felix.main', 'postgresql', 'servlet-api',
                     'scala-library', 'slf4j-api', 'slf4j-ext', 'subethasmtp'
        }
        compile('ch.qos.logback:logback-core:1.0.9') {
            excludes 'janino', 'jansi', 'mail', 'geronimo-jms_1.1_spec', 'easymock', 'servlet-api', 'scala-library', 'junit', 'fest-assert'
        }
    }
}

griffon {
    doc {
        logo = '<a href="http://griffon-framework.org" target="_blank"><img alt="The Griffon Framework" src="../img/griffon.png" border="0"/></a>'
        sponsorLogo = "<br/>"
        footer = "<br/><br/>Made with Griffon (@griffon.version@)"
    }
}

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }

    error 'org.codehaus.griffon',
          'org.springframework',
          'org.apache.karaf',
          'groovyx.net'
    warn  'griffon'
}