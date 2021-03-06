package org.tbk.spring.useragentutils.test;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.tbk.spring.useragentutils.UserAgentResolverHandlerInterceptor;
import org.tbk.spring.useragentutils.UserAgentUtils;

public class UserAgentResolverHandlerInterceptorTest {

    private UserAgentResolverHandlerInterceptor userAgentResolverHandlerInterceptor;

    @Before
    public void before() {
        userAgentResolverHandlerInterceptor = new UserAgentResolverHandlerInterceptor();
    }

    @Test
    public void testWithoutUserAgentHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        userAgentResolverHandlerInterceptor.preHandle(request, new MockHttpServletResponse(), null);

        UserAgent currentUserAgent = UserAgentUtils.getCurrentUserAgent(request);
        Assert.assertEquals(Browser.UNKNOWN, currentUserAgent.getBrowser());
    }

    @Test
    public void testWithEmptyUserAgent() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("User-Agent", "");

        userAgentResolverHandlerInterceptor.preHandle(request, new MockHttpServletResponse(), null);

        UserAgent currentUserAgent = UserAgentUtils.getCurrentUserAgent(request);
        Assert.assertEquals(Browser.UNKNOWN, currentUserAgent.getBrowser());
    }

    @Test
    public void testFirefoxUserAgent() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("User-Agent", UserAgentStrings.FIREFOX);

        userAgentResolverHandlerInterceptor.preHandle(request, new MockHttpServletResponse(), null);

        UserAgent currentUserAgent = UserAgentUtils.getCurrentUserAgent(request);
        Assert.assertEquals(Browser.FIREFOX.getGroup(), currentUserAgent.getBrowser().getGroup());
    }
}
