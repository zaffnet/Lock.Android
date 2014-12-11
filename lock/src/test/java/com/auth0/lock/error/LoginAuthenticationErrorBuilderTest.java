/*
 * LoginAuthenticationErrorBuilderTest.java
 *
 * Copyright (c) 2014 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.lock.error;

import com.auth0.lock.event.AuthenticationError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.auth0.lock.utils.AuthenticationErrorDefaultMatcher.hasDefaultTitleAndMessage;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by hernan on 12/11/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class LoginAuthenticationErrorBuilderTest {

    private AuthenticationErrorBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new LoginAuthenticationErrorBuilder();
    }

    @Test
    public void shouldBuildAuthenticationError() throws Exception {
        assertThat(builder.buildFrom(new RuntimeException()), is(notNullValue()));
    }

    @Test
    public void shouldContainThrowable() throws Exception {
        Throwable throwable = new RuntimeException();
        assertThat(builder.buildFrom(throwable).getThrowable(), equalTo(throwable));
    }

    @Test
    public void shouldContainBasicMessages() throws Exception {
        AuthenticationError error = builder.buildFrom(new RuntimeException());
        assertThat(error, hasDefaultTitleAndMessage());
    }

}