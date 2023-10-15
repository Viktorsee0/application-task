package com.spg.applicationTask.engine;

import java.util.HashMap;
import java.util.Map;

public class ServletContainer {

    private Map<String, AbstractController> servlets = new HashMap<>();

    public void registerServlet(AbstractController controller) {
        servlets.put(controller.apiPath, controller);
    }


}
