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
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET

class AvatarTagLib {
	static namespace = "avatar"

	def gravatar = { attrs, body ->
		if(!attrs.email) throw new IllegalStateException("Property [email] must be set!")
		String email = attrs.email.toString()
		def size = 20
        def hash = MD5Util.md5Hex(email)
		def alt = "Gravatar"
		def cssClass = "avatar"
		def title = ''
		def id = ''
		def name = ''

		def gravatarBaseUrl = "http://gravatar.com/avatar/"
		if (request.isSecure()) {
			gravatarBaseUrl = "https://secure.gravatar.com/avatar/"
		}

		String gravatarUrl = "$gravatarBaseUrl$hash"

		if(attrs.size) size = attrs.size

		if(attrs.alt) alt = attrs.alt
        
        if(attrs.title) title = attrs.title

		if(attrs.cssClass) cssClass = attrs.cssClass
		
		if(attrs.name) name = attrs.name
		
		if(attrs.id) id = attrs.id
        
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
		
		// The size is requested to gravatar in order to get the imaged in the requested size
		// If we don't send the 's' parameter the image is received at 80x80
		gravatarUrl += "&s=${size}"

        out << """
			<img id='${id}' name='${name}' alt='$alt' class='$cssClass' height='$size' width='$size' src='$gravatarUrl' title='$title'/>
		"""
	}
	
	
	def twitter = { attrs, body ->
	  def user
    try{      
	   user = new XmlParser().parse("http://api.twitter.com/1/users/show.xml?screen_name=${attrs.user}")
	  }catch(FileNotFoundException){
	    user = new XmlParser().parse("http://api.twitter.com/1/users/show.xml?screen_name=twitter")
	  }
	  
    def image = user.profile_image_url.text()
    
    def alt = "twitter"
		def cssClass = "avatar"
		def title = attrs.user
    
	  out << """
  	  <img alt='$alt' class='$cssClass' height='${attrs?.size ?: 20}' width='${attrs?.size ?: 20}' src='$image' title='$title'/>
    """
	}
	
	def facebook = { attrs, body ->
	  def url = "https://graph.facebook.com/${attrs?.user ?: 'facebook'}/picture"
	  new HTTPBuilder(url).request(GET) { req ->
      response.success = { resp ->
        def notFound = resp.headers.find { it.name.contains('WWW-Authenticate') }
        if(notFound){
          url = "https://graph.facebook.com/facebook/picture"
        }
      }
      response.failure = { resp ->
      }
    }
	  
	  def alt = "facebook"
		def cssClass = "avatar"
		def title = attrs.user
	  out << """
  	  <img alt='$alt' class='$cssClass' height='${attrs?.size ?: 20}' width='${attrs?.size ?: 20}' src='$url' title='$title'/>
    """
	}
}
