grails.project.dependency.resolution = {
  inherits("global") {
  }
  log "warn"
  plugins{
	/*
    This validation is for prevent load the following plugins in previous Grails versions.
    I some Grails versions from 1.3.* the 'export = false' does not work. For Grails 2.* works properly
    */
    if (grailsVersion.startsWith('2')) {
      build(":release:2.0.0") { export = false }
      build ":tomcat:$grailsVersion"
    }
  }
  repositories {
    mavenLocal()
    mavenCentral()
    grailsPlugins()
    grailsHome()
    grailsCentral()
    grailsRepo "http://grails.org/plugins"
    mavenRepo "http://repo.grails.org/grails/plugins"
  }
}

grails.release.scm.enabled = false
grails.project.repos.default = "grailsCentral"