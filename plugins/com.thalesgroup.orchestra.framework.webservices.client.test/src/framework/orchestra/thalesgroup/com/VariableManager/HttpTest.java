/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package framework.orchestra.thalesgroup.com.VariableManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.junit.Test;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;

/**
 * @author t0076261
 */
public class HttpTest {

  @Test
  public void httpRequest() throws Exception {
    final int variableManagerPort = ServerConfParam.getInstance().getPort();
    for (int i = 0; i < 10; i++) {
      final int id = i;
      Thread thread = new Thread(new Runnable() {
        @SuppressWarnings("nls")
        public void run() {
          try {
            Socket socket = new Socket("localhost", variableManagerPort);
            String request =
                "POST /execute HTTP/1.1\nHost: localhost\nOrchestra: " + id
                    + "\nConnection: close\nContent-Length: 76\n\rcommand=EDIT&readonly=false&targettoolname=Gimp&tool=word&projectname=Image1";
            socket.getOutputStream().write(request.getBytes());
            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String readLine;
            StringBuffer outputBuffer = new StringBuffer();
            while (null != (readLine = bufferedReader.readLine())) {
              outputBuffer.append(readLine);
            }
            if (!socket.isClosed()) {
              socket.close();
            }
            System.out.println(outputBuffer.toString());
          } catch (Throwable throwable_p) {
            throwable_p.printStackTrace();
          }
        }
      });
      thread.start();
    }
    Thread.sleep(5000);
  }
}