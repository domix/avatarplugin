grails.project.dependency.resolution = {
  inherits("global") {
  }
  log "warn"
  plugins{
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
