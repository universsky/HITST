class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		// 修改默认主页
		// "/"(view:"/index")
		"/"(controller: "netInterface", action: "index")

		"500"(view:'/error')
	}
}
