grails.project.dependency.resolution = {
  inherits("global") {
  }
  log "warn"
  plugins{
	test ":code-coverage:1.2.4" { export = false }
	test ":spock:0.5-groovy-1.7" { export = false }
	build ":release:1.0.2-SNAPSHOT" { export = false }
  }
  repositories {
    mavenLocal()
    mavenCentral()
    grailsPlugins()
    grailsHome()
    grailsCentral()
  }
  dependencies {
	compile("org.codehaus.groovy.modules.http-builder:http-builder:0.5.0") {
		excludes 'groovy', 'xml-apis', 'xercesImpl', 'commons-lang'
	}
	runtime 'commons-httpclient:commons-httpclient:3.0.1'
	runtime 'xerces:xerces:2.4.0'
  }
}
