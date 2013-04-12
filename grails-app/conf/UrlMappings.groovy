class UrlMappings {
  static mappings = {
    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }
    "500"(view: '/error')
    name index: '/' {
      controller = 'avatar'
      action = [GET: 'index', POST: 'obtain']
    }
  }
}
