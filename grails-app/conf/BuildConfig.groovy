grails.project.dependency.resolution = {
  inherits("global") {
  }
  log "warn"
  plugins{
	test (":code-coverage:1.2.5") { export = false }
	test ("org.spockframework:spock:0.6-groovy-1.8-SNAPSHOT") { export = false }
	build (":release:1.0.2-SNAPSHOT") { export = false }
  }
  repositories {
    mavenLocal()
    mavenCentral()
    grailsPlugins()
    grailsHome()
    grailsCentral()
  }
  dependencies {
	compile("org.codehaus.groovy.modules.http-builder:http-builder:0.5.2") {
		excludes 'groovy', 'xml-apis', 'xercesImpl', 'commons-lang'
	}
	runtime ('commons-httpclient:commons-httpclient:3.1') {
	    excludes 'commons-logging', 'commons-codec'
	}
	runtime 'xerces:xerces:2.4.0'
  }
}
