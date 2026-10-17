/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.gen.tree.testng;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

/**
 * Reports a test method that TestNG skipped because one of its configuration methods (for instance
 * a {@code @BeforeMethod} setup) failed as a failed test.
 * <p>
 * The Gradle test task already fails the build on a failed configuration method. PIT's TestNG
 * plugin does not: it counts the skipped test methods as neither failed nor passed, so a mutant
 * that breaks the shared setup of a test class is reported as surviving every test of that class,
 * even though no test in the class could run against it. With this listener the skipped tests carry
 * the setup's exception as their failure, and PIT sees the mutant detected.
 * <p>
 * A test that skips itself with a {@link SkipException} stays skipped. Registered through
 * {@code META-INF/services/org.testng.ITestNGListener}, so the Gradle test task and the PIT minions
 * both load it without any further configuration.
 */
public class SkippedBySetupFailureListener implements IInvokedMethodListener
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterInvocation(final IInvokedMethod method, final ITestResult testResult)
	{
		if (method.isTestMethod() && testResult.getStatus() == ITestResult.SKIP
			&& testResult.getThrowable() != null
			&& !(testResult.getThrowable() instanceof SkipException))
		{
			testResult.setStatus(ITestResult.FAILURE);
		}
	}

}
