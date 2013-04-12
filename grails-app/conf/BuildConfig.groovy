grails.project.dependency.resolution = {
  inherits("global") {
  }
  log "warn"
  plugins{
    build(":release:2.2.1") { export = false }
    build(":tomcat:$grailsVersion") { export = false }
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