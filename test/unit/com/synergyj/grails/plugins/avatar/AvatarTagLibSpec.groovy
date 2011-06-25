package com.synergyj.grails.plugins.avatar

import grails.plugin.spock.*

class AvatarTagLibSpec extends TagLibSpec {
	
	def "gravatar: some variants"() {
		AvatarTagLib.metaClass.getGrailsApplication = {	// mock 'grailsApplication'
			[ config:
				[ avatarPlugin:
					[ defaultGravatarUrl:'', gravatarRating:'']
				]
			]
		}

		def e	= 'foo@example.org'
		
		expect:
		gravatar(email:e).contains("src=\"http://gravatar.com/avatar/64f677e30cd713a9467794a26711e42d&s=20\"")
		!gravatar(email:e, defaultGravatarUrl:'invalidDefault').contains("invalidDefault")
		gravatar(email:e, defaultGravatarUrl:'monsterid').contains("d=monsterid")
		gravatar(email:e, defaultGravatarUrl:'http://example.org/image.png').contains("d=http://example.org/image.png")
		gravatar(email:e, defaultGravatarUrl:'https://secure.example.org/image.png').contains("d=https://secure.example.org/image.png")
		!gravatar(email:e, size:'0').contains("s=0")
		gravatar(email:e, size:'1').contains("s=1")
		gravatar(email:e, size:'128').contains("s=128")
		gravatar(email:e, size:'512').contains("s=512")
		!gravatar(email:e, size:'513').contains("s=513")
	}
}