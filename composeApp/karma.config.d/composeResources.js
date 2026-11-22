const path = require("node:path");

config.files.push({
	pattern: path.resolve(config.basePath, "kotlin/composeResources/**/*"),
	included: false,
	served: true,
	watched: false,
});

config.proxies["/composeResources/"] = "/base/kotlin/composeResources/";
