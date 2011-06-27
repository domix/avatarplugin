/* Copyright 2006-2011 the original author or authors.
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

	/** 
	 * Outputs the Gravatar this is associated with the given email address
	 * Note the parameter documentation at http://de.gravatar.com/site/implement/images/ 
	 * 
	 * @attr email		REQUIRED	the startDate for styling
	 * @attr size					the disired dimensions in pixels for the gravatar image (from 1 up to 512)
	 * @attr alt					alt-attribute for the resulting img-element
	 * @attr cssClass				class-attribute for the resulting img-element
	 * @attr title					title-attribute for the resulting img-element
	 * @attr id						id-attribute for the resulting img-element
	 * @attr name					name-attribute for the resulting img-element
	 * @attr defaultGravatarUrl		the default image to display if no gravatar is found; may be a URL or one of the following (defaults to the official Gravatar logo):
	 * 								<li>404: do not load any image if none is associated with the email hash, instead return an HTTP 404 (File Not Found) response
	 * 								<li>mm: (mystery-man) a simple, cartoon-style silhouetted outline of a person (does not vary by email hash)
	 * 								<li>identicon: a geometric pattern based on an email hash
	 * 								<li>monsterid: a generated 'monster' with different colors, faces, etc
	 * 								<li>wavatar: generated faces with differing features and backgrounds
	 * 								<li>retro: awesome generated, 8-bit arcade-style pixelated faces
	 * @attr gravatarRating			desired image rating censor-level; may be one of the following:
	 * 								<li>g (default): suitable for display on all websites with any audience type.
	 * 								<li>pg: may contain rude gestures, provocatively dressed individuals, the lesser swear words, or mild violence.
	 * 								<li>r: may contain such things as harsh profanity, intense violence, nudity, or hard drug use.
	 * 								<li>x: may contain hardcore sexual imagery or extremely disturbing violence.
	 * @emptyTag
	 */
	def gravatar = { attrs, body ->
		if (!attrs.email) throw new IllegalStateException("Property [email] must be set!")
		String email		= attrs.remove('email').toString()
		def hash			= MD5Util.md5Hex(email)
		def size			= attrs.remove('size')		?: 20
		def alt				= attrs.remove('alt')		?: "Gravatar"
		def cssClass		= attrs.remove('cssClass')	?: "avatar"
		def title			= attrs.remove('title')		?: ''
		def id				= attrs.remove('id')		?: ''
		def name			= attrs.remove('name')		?: ''
		def dgu				= attrs.remove('defaultGravatarUrl')	?: grailsApplication.config.avatarPlugin.defaultGravatarUrl
		def gravatarRating	= attrs.remove('gravatarRating')		?: grailsApplication.config.avatarPlugin.gravatarRating

		def gravatarBaseUrl	= request.isSecure() ? "https://secure.gravatar.com/avatar/" : "http://gravatar.com/avatar/"
		String gravatarUrl	= "$gravatarBaseUrl$hash"
		gravatarUrl	+= dgu.matches(/404|mm|identicon|monsterid|wavatar|retro|http.*/) ? "?d=${dgu}" : ''

		if (gravatarRating) {
			gravatarUrl += (gravatarUrl.contains('?') ? '&' : '?') + "r=${gravatarRating}"
		}

		// The size is requested to gravatar in order to get the imaged in the requested size
		// If we don't send the 's' parameter the image is received at 80x80
		def validGravatarSize = 1..512
		if(validGravatarSize.contains(size.toInteger())) {
			gravatarUrl += "&s=${size}"
		}

		out << """<img id="${id}" name="${name}" alt="$alt" class="$cssClass" height="$size" width="$size" src="$gravatarUrl" title="$title"/>"""
	}

	def twitter = { attrs, body ->
		def user
		try {
			user = new XmlParser().parse("http://api.twitter.com/1/users/show.xml?screen_name=${attrs.user}")
		} catch (FileNotFoundException) {
			user = new XmlParser().parse("http://api.twitter.com/1/users/show.xml?screen_name=twitter")
		}

		def image = user.profile_image_url.text()

		def alt = "twitter"
		def cssClass = "avatar"
		def title = attrs.user

		out << "<img alt='$alt' class='$cssClass' height='${attrs?.size ?: 20}' width='${attrs?.size ?: 20}' src='$image' title='$title'/>"
	}

	def facebook = { attrs, body ->
		def url = "https://graph.facebook.com/${attrs?.user ?: 'facebook'}/picture"
		new HTTPBuilder(url).request(GET) { req ->
			response.success = { resp ->
				def notFound = resp.headers.find { it.name.contains('WWW-Authenticate') }
				if (notFound) {
					url = "https://graph.facebook.com/facebook/picture"
				}
			}
			response.failure = { resp ->
			}
		}

		def alt = "facebook"
		def cssClass = "avatar"
		def title = attrs.user
		out << "<img alt='$alt' class='$cssClass' height='${attrs?.size ?: 20}' width='${attrs?.size ?: 20}' src='$url' title='$title'/>"
	}
}
