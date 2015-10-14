class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		// "/"(view:"/index")
		"/"(controller: "netInterface", action: "index")
		"500"(view:'/error')
	}
}
