package com.ice.jni.registry;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class RegistryTest extends TestCase {
  public void testReadRegistry() throws Exception {
    RegistryKey HKLM = Registry.HKEY_LOCAL_MACHINE;
    Assert.assertNotNull(HKLM);

    RegistryKey orchestraProducts = HKLM.openSubKey("SOFTWARE\\Thales\\EPM\\Orchestra\\Products"); //$NON-NLS-1$
    Assert.assertNotNull(orchestraProducts);

    List<String> subKeys = new ArrayList<String>();
    Enumeration<String> keyElements = orchestraProducts.keyElements();
    boolean papeeteFound = false;
    while (keyElements.hasMoreElements()) {
      String nextElement = keyElements.nextElement();
      subKeys.add(nextElement);
      if ("OrchestraFramework".equals(nextElement)) {
        papeeteFound = true;
        RegistryKey openSubKey = orchestraProducts.openSubKey(nextElement);
        String value = openSubKey.getStringValue("InstallLocation");
        Assert.assertNotNull(value);
        Assert.assertTrue(new File(value).exists());
      }
    }
    Assert.assertTrue(papeeteFound);
    Assert.assertFalse(subKeys.isEmpty());
    Assert.assertTrue(subKeys.contains("OrchestraFramework")); //$NON-NLS-1$
  }
}
