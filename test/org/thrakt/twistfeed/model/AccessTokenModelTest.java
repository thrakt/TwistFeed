package org.thrakt.twistfeed.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AccessTokenModelTest extends AppEngineTestCase {

    private AccessTokenModel model = new AccessTokenModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
