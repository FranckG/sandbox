package com.thalesgroup.orchestra.framework.model.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("nls")
public class ProcessExecutionHandler {
  public static String exec(String[] args) throws IOException {
    return exec(args, true);
  }

  public static String exec(String[] args, boolean printWarn) throws IOException {
    return exec(args, printWarn, "");
  }

  public static String exec(String[] args, boolean printWarn, String workingdir) throws IOException {

    Runtime runtime = Runtime.getRuntime();
    final Process process;
    if ((workingdir == null) || workingdir.equals("")) {
      process = runtime.exec(args);
    } else {
      File location = new File(workingdir);
      process = runtime.exec(args, null, location);
    }
    ProcessOutputHandler inputStreamHandler = new ProcessOutputHandler(process.getInputStream());
    inputStreamHandler.start();

    ProcessOutputHandler errorStreamHandler = new ProcessOutputHandler(process.getErrorStream());
    errorStreamHandler.start();

    try {

      process.waitFor();
      inputStreamHandler.join();
      errorStreamHandler.join();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (process.exitValue() != 0) {
      System.out.println("Exit value was : " + process.exitValue());
    }

    return inputStreamHandler.getResult();
  }

  /**
   * Handle Process output.
   * @author T0052089
   */
  public static class ProcessOutputHandler extends Thread {
    /**
     * The StringBuffer holding the captured output.
     */
    private final StringBuffer _captureBuffer;

    /**
     * Stream being read.
     */
    private final BufferedReader _reader;

    /**
     * Constructor.
     */
    ProcessOutputHandler(InputStream processInputStream_p) {
      _reader = new BufferedReader(new InputStreamReader(processInputStream_p));
      // Use StringBuffer because it is thread-safe.
      _captureBuffer = new StringBuffer();
    }

    /**
     * Stream the data.
     */
    @Override
    public void run() {
      try {
        String readLine = null;
        while (null != (readLine = _reader.readLine())) {
          _captureBuffer.append(readLine + "\n");
        }
      } catch (IOException ioException_p) {
        ioException_p.printStackTrace();
      } finally {
        // Close quietly.
        try {
          _reader.close();
        } catch (IOException exception_p) {
          // Nothing to do.
        }
      }
    }

    public String getResult() {
      if (_captureBuffer.length() > 0) {
        // Remove last "\n".
        return _captureBuffer.substring(0, _captureBuffer.length() - 1);
      }
      return "";
    }
  }
}