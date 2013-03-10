package org.thrakt.twistfeed.controller.auth;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.thrakt.twistfeed.service.TwitterAccessService;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return redirect(TwitterAccessService.getAuthURL("singleUser"));
    }
}
