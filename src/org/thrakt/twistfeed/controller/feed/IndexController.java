package org.thrakt.twistfeed.controller.feed;

import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.thrakt.twistfeed.model.IdUpdatedModel;
import org.thrakt.twistfeed.service.TwitterAccessService;
import org.thrakt.twistfeed.service.UpdatedManageService;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        ResponseList<Status> tweetList =
            TwitterAccessService.getTweetList(param("id"));
        User user = TwitterAccessService.getUser(Long.valueOf(param("id")));
        Date updated = tweetList.get(0).getCreatedAt();
        SimpleDateFormat isoFormat =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String feedUrl =
            request.getRequestURL().toString() + "id/" + param("id");
        response.setContentType("application/xml; charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pw.print("<feed xmlns=\"http://www.w3.org/2005/Atom\">");
        pw.print("<title>Twitter Timeline Feed for "
            + encodeToEntity(user.getName())
            + "</title>");
        pw.print("<link href=\"http://twitter.com/"
            + user.getScreenName()
            + "\"></link>");
        pw.print("<link type=\"application/atom+xml\" rel=\"self\" href=\""
            + feedUrl
            + "\"></link>");
        pw.print("<link rel=\"hub\" "
            + "href=\"http://pubsubhubbub.appspot.com\"></link>");
        pw.print("<summary>Twitter Timeline Feed for "
            + encodeToEntity(user.getName())
            + "</summary>");
        pw.print("<updated>" + isoFormat.format(updated) + "</updated>");
        pw.print("<id>" + feedUrl + "</id>");
        for (Status status : tweetList) {
            pw.print("<entry>");
            pw.print("<title>" + encodeToEntity(status.getText()) + "</title>");
            pw.print("<link href=\"https://twitter.com/"
                + status.getUser().getScreenName()
                + "/status/"
                + status.getId()
                + "\"></link>");
            pw.print("<id>https://twitter.com/"
                + status.getUser().getScreenName()
                + "/status/"
                + status.getId()
                + "</id>");
            pw.print("<summary type=\"html\">"
                + encodeToEntity(status.getText())
                + "</summary>");
            pw.print("<updated>"
                + isoFormat.format(status.getCreatedAt())
                + "</updated>");
            pw.print("<author><name>"
                + status.getUser().getScreenName()
                + " ("
                + encodeToEntity(status.getUser().getName())
                + ")</name><uri>http://twitter.com/"
                + status.getUser().getScreenName()
                + "</uri></author>");
            pw.print("</entry>");
        }
        pw.println("</feed>");
        pw.close();

        IdUpdatedModel lastUpdate =
            UpdatedManageService.getLastUpdated(param("id"));
        if (lastUpdate == null) {
            lastUpdate = new IdUpdatedModel();
            lastUpdate.setKey(Datastore.createKey(
                IdUpdatedModel.class,
                Long.valueOf(param("id"))));
        }
        if (!updated.equals(lastUpdate.getUpdated())) {
            lastUpdate.setUpdated(updated);
            UpdatedManageService.putLastUpdated(lastUpdate);

            URLFetchService ufs = URLFetchServiceFactory.getURLFetchService();
            HTTPRequest req =
                new HTTPRequest(
                    new URL("https://pubsubhubbub.appspot.com/publish"),
                    HTTPMethod.POST);
            req.setPayload(("hub.mode=publish&hub.url=" + URLEncoder.encode(
                feedUrl,
                "UTF-8")).getBytes());
            ufs.fetch(req);
        }

        return null;
    }

    private String encodeToEntity(String string) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < string.length(); i =
            string.offsetByCodePoints(i, 1)) {
            text.append("&#x");
            text.append(Integer.toHexString(string.codePointAt(i)));
            text.append(";");
        }
        return text.toString();
    }
}
