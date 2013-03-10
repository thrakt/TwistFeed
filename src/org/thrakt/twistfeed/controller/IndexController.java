package org.thrakt.twistfeed.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.thrakt.twistfeed.service.TwitterAccessService;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if(TwitterAccessService.isAuthenticated("singleUser")){
            return null;
        }
        return redirect("/auth");
    }
}
