package org.thrakt.twistfeed.controller.feed;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class NameControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/feed/name");
        NameController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
