package nl.my888.commons.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class NoCacheFilterTest {

    @Test
    public void testInit() throws Exception {
        final NoCacheFilter testSubject = new NoCacheFilter();

        testSubject.init(null);

        // init should do nothing
        assertTrue(true);
    }

    @Test
    public void testDestroy() throws Exception {
        final NoCacheFilter testSubject = new NoCacheFilter();

        testSubject.destroy();

        // destroy should do nothing
        assertTrue(true);
    }

    @Test
    public void testDoFilter() throws Exception {

        final NoCacheFilter testSubject = new NoCacheFilter();

        final ServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = new MockFilterChain();

        testSubject.doFilter(request, response, chain);

        assertThat(response.getHeaderNames(), containsInAnyOrder("Cache-Control", "Pragma", "Expires"));
    }

}
