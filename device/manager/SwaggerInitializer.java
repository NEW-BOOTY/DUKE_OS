/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package device.manager;

import org.eclipse.jetty.server.handler.ResourceHandler;

public class SwaggerInitializer {
    public static ResourceHandler getSwaggerHandler() {
        ResourceHandler swagger = new ResourceHandler();
        swagger.setDirectoriesListed(false);
        swagger.setWelcomeFiles(new String[]{"index.html"});
        swagger.setResourceBase("webapp/swagger-ui");
        return swagger;
    }
}
