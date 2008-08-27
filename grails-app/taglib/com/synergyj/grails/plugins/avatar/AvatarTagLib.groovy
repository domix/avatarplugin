/* Copyright 2006-2007 the original author or authors.
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
package com.synergyj.grails.plugins.avatar

import com.synergyj.grails.plugins.avatar.util.MD5Util

class AvatarTagLib {
	static namespace = "avatar"
	
	def gravatar = { attrs, body ->
		if(!attrs.email) throw new IllegalStateException("Property [email] must be set!")
		def email = attrs.email
		def size = 20
        def hash = MD5Util.md5Hex(email)
		def alt = "Gravatar"
		def cssClass = "avatar"
		def gravatarBaseUrl = "https://secure.gravatar.com/avatar/"
		def gravatarUrl = "$gravatarBaseUrl$hash"

		if(attrs.size) size = attrs.size
		
		if(attrs.alt) alt = attrs.alt
		
		if(attrs.cssClass) cssClass = attrs.cssClass

		def dgu = null
		if(attrs.defaultGravatarUrl) {
			dgu = attrs.defaultGravatarUrl
		} else {
			dgu = grailsApplication.config.avatarPlugin.defaultGravatarUrl			
		}
		if(dgu) {
			gravatarUrl += "?d=${dgu}"
		}
		
		def gravatarRating = null
		if(attrs.gravatarRating) {
			gravatarRating = attrs.gravatarRating
		} else {
			gravatarRating = grailsApplication.config.avatarPlugin.gravatarRating
		}
		if(gravatarRating) {
			if(gravatarUrl.contains('?')) {
				gravatarUrl += "&r=${gravatarRating}"
			}
			else {
				gravatarUrl += "?r=${gravatarRating}"
			}
		}
		
		
		
		
		
        out << """
			<img alt="$alt" class="$cssClass" height="$size" src="$gravatarUrl" width="$size" />
		"""
	}
}
