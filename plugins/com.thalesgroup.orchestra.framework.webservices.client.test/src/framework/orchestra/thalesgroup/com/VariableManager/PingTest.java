/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package framework.orchestra.thalesgroup.com.VariableManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;

import junit.framework.Assert;

/**
 * @author t0076261
 */
public class PingTest {
  @SuppressWarnings("nls")
  @Test
  public void ensureOrchestraFrameworkIsAlive() throws IOException {
    int variableManagerPort = ServerConfParam.getInstance().getPort();
    String connectString = "http://localhost:" + variableManagerPort + "/ping";
    URL url = new URL(connectString);
    InputStream openStream = url.openStream();
    byte[] readFile = FileHelper.readFile(openStream);
    String actual = new String(readFile);
    Assert.assertEquals("The Orchestra Framework should be up and running.", System.getProperty("user.name"), actual);
  }
}