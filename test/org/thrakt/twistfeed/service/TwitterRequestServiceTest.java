package org.thrakt.twistfeed.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TwitterRequestServiceTest extends AppEngineTestCase {

    private TwitterAccessService service = new TwitterAccessService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
