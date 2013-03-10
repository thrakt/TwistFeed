package org.thrakt.twistfeed.controller.auth.callback;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.thrakt.twistfeed.service.TwitterAccessService;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if (TwitterAccessService
            .twitterAuth("singleUser", param("oauth_verifier"))) {
            return redirect("/");
        }
        return redirect("http://twitter.com/logout");
    }
}
