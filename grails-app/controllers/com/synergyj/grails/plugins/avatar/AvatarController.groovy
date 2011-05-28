package com.synergyj.grails.plugins.avatar

class AvatarController {

  def index = { }
  def obtain = {
    render(view:'index',model:[email:params.email,twitter:params.twitter])
  }
}
