package org.thrakt.twistfeed.controller;

import org.slim3.controller.router.RouterImpl;

public class AppRouter extends RouterImpl {
    public AppRouter() {
        addRouting(
            "/feed/name/{name}",
            "/feed/name?name={name}");
        addRouting(
            "/feed/id/{id}",
            "/feed/?id={id}");
    }
}
