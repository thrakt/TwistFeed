package org.thrakt.twistfeed.controller.feed;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.thrakt.twistfeed.service.TwitterAccessService;

public class NameController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return redirect("/feed/id/"
            + TwitterAccessService.getUserId(param("name")));
    }
}
