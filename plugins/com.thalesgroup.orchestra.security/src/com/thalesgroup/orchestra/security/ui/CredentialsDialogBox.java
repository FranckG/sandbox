/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.thalesgroup.orchestra.security.credentials.Credentials;
import com.thalesgroup.orchestra.security.credentials.CredentialsResponse;
import com.thalesgroup.orchestra.security.credentials.ICredentialsResponse;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class CredentialsDialogBox extends JDialog {

  private static final long serialVersionUID = 1L;

  private static final int MAX_TEXT_WIDTH = 500;

  private JLabel onTopMessage;

  private final JLabel loginLbl;
  private final JTextField loginFld;
  private final JLabel passwordLbl;
  private final JPasswordField passwordFld;
  private JLabel confirmPasswordLbl;
  private JPasswordField passwordConfirmationFld;

  private final JButton okBtn;
  private final JButton cancelBtn;

  private CredentialsDialogBoxStatus userClick;

  private int gridY = 0;

  /**
   * Display dialog box for user login/password input.
   * @param optionalUIPasswordConfirmation_p : If <code>true</code> a password confirmation field will be displayed.
   * @param optionalUIOnTopMessage_p : If not <code>null</code> or empty, this {@link String} message will be displayed on the top of dialog box. (NB: This
   *          message can be HTMl formatted.)
   * @return {@link ICredentialsResponse}.
   */
  public static ICredentialsResponse show(final boolean optionalUIPasswordConfirmation_p, final String optionalUIOnTopMessage_p) {

    // Before UI creation, set UI default OS look and feel.
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      // Exception must not be raised.
    }

    // JFrame parent of my JDialog creation.
    final JFrame frame = new JFrame("Login password JDialog parent frame"); //$NON-NLS-1$

    try {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 150);
      frame.setLayout(new FlowLayout());
      frame.setVisible(false);

      CredentialsDialogBox loginPasswordDlg = new CredentialsDialogBox(frame, optionalUIPasswordConfirmation_p, optionalUIOnTopMessage_p);
      loginPasswordDlg.setAlwaysOnTop(true);
      loginPasswordDlg.setVisible(true);

      // if user click 'OK' take into account the user's inputs.
      Credentials credentials;
      if (loginPasswordDlg.getUserClick() == CredentialsDialogBoxStatus.DISPLAYED_AND_OK)
        credentials = new Credentials(loginPasswordDlg.getLogin(), loginPasswordDlg.getPassword());
      else
        credentials = null;

      return new CredentialsResponse(loginPasswordDlg.getUserClick(), credentials);

    } finally {
      frame.dispose(); // disposing JFrame parent of my JDialog creation. If not AWT/SWING thread always up.
    }
  }

  /**
   * Wrap a message with a given Font and a maximum text width
   * @param message_p
   * @param fontMetrics_p
   * @param maxWidth_p
   * @return wrapped message
   */
  private String wrapMessage(final String message_p, final FontMetrics fontMetrics_p, final int maxWidth_p) {
    StringBuilder builder = new StringBuilder();
    builder.append("<html><b>");

    int len = message_p.length();
    int lineSize = 0;
    for (int i = 0; i < len; i++) {
      char c = message_p.charAt(i);
      int charSize = fontMetrics_p.charWidth(c);
      if ((lineSize + charSize) > maxWidth_p) {
        builder.append("<br>");
        lineSize = charSize;
      } else
        lineSize += charSize;
      builder.append(c);
    }
    builder.append("</b></html>");
    return builder.toString();
  }

  private CredentialsDialogBox(final Frame parent, final boolean optionalPasswordConfirmationField_p, final String optionalOnTopMessage_p) {
    super(parent, "Credentials management", true); //$NON-NLS-1$

    // set default user click response to CLOSE
    this.setUserClick(CredentialsDialogBoxStatus.DISPLAYED_AND_CLOSE);

    // first : JTextArea component if optional message to display asked.
    boolean msgToDisplay = (null == optionalOnTopMessage_p) ? false : !optionalOnTopMessage_p.isEmpty();

    if (msgToDisplay) {
      this.onTopMessage = new JLabel();
      this.onTopMessage.setText(this.wrapMessage(optionalOnTopMessage_p, this.onTopMessage.getFontMetrics(this.onTopMessage.getFont()), MAX_TEXT_WIDTH));
    }

    // second : main panel with labels and fields to fill.
    JPanel fieldsPanel = new JPanel(new GridBagLayout());

    this.loginLbl = new JLabel("Login: "); //$NON-NLS-1$
    GridBagConstraints cs = new GridBagConstraints();
    cs.fill = GridBagConstraints.HORIZONTAL;
    cs.insets = new Insets(4, 4, 4, 4);
    cs.gridx = 0;
    cs.gridy = ++this.gridY;
    cs.gridwidth = 1;
    cs.weightx = 0.1;
    fieldsPanel.add(this.loginLbl, cs);

    this.loginFld = new JTextField(20);
    cs = new GridBagConstraints();
    cs.fill = GridBagConstraints.HORIZONTAL;
    cs.insets = new Insets(4, 4, 4, 4);
    cs.gridx = 1;
    cs.gridy = this.gridY;
    cs.gridwidth = 3;
    cs.weightx = 0.9;
    fieldsPanel.add(this.loginFld, cs);

    this.passwordLbl = new JLabel("Password: "); //$NON-NLS-1$
    cs = new GridBagConstraints();
    cs.fill = GridBagConstraints.HORIZONTAL;
    cs.insets = new Insets(4, 4, 4, 4);
    cs.gridx = 0;
    cs.gridy = ++this.gridY;
    cs.gridwidth = 1;
    cs.weightx = 0.1;
    fieldsPanel.add(this.passwordLbl, cs);

    this.passwordFld = new JPasswordField(15);
    cs = new GridBagConstraints();
    cs.fill = GridBagConstraints.HORIZONTAL;
    cs.insets = new Insets(4, 4, 4, 4);
    cs.gridx = 1;
    cs.gridy = this.gridY;
    cs.gridwidth = 2;
    cs.weightx = 0.9;
    fieldsPanel.add(this.passwordFld, cs);

    if (optionalPasswordConfirmationField_p) {
      this.confirmPasswordLbl = new JLabel("Password confirmation: "); //$NON-NLS-1$
      cs.gridx = 0;
      cs.gridy = ++this.gridY;
      cs.gridwidth = 1;
      cs.weightx = 0.1;
      fieldsPanel.add(this.confirmPasswordLbl, cs);

      this.passwordConfirmationFld = new JPasswordField(15);
      cs.gridx = 1;
      cs.gridy = this.gridY;
      cs.gridwidth = 2;
      cs.weightx = 0.9;
      fieldsPanel.add(this.passwordConfirmationFld, cs);
    }

    fieldsPanel.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

    // Third panel with buttons.
    this.okBtn = new JButton("Ok"); //$NON-NLS-1$
    this.okBtn.addActionListener(new ActionListener() {
      @Override
      @SuppressWarnings("synthetic-access")
      public void actionPerformed(final ActionEvent e) {
        if (optionalPasswordConfirmationField_p && !CredentialsDialogBox.this.getPassword().equals(CredentialsDialogBox.this.getPasswordConfirmation())) {
          JOptionPane.showMessageDialog(CredentialsDialogBox.this,
              "Password and password confirmation are not the same. Please enter them again.", "Password ", JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$//$NON-NLS-2$
          CredentialsDialogBox.this.clearPassword(); // reset passwords
        } else {
          CredentialsDialogBox.this.setUserClick(CredentialsDialogBoxStatus.DISPLAYED_AND_OK);
          CredentialsDialogBox.this.dispose();
        }
      }
    });

    this.cancelBtn = new JButton("Cancel"); //$NON-NLS-1$
    this.cancelBtn.addActionListener(new ActionListener() {
      @Override
      @SuppressWarnings("synthetic-access")
      public void actionPerformed(final ActionEvent e) {
        CredentialsDialogBox.this.setUserClick(CredentialsDialogBoxStatus.DISPLAYED_AND_CANCEL);
        CredentialsDialogBox.this.dispose();
      }
    });

    JPanel btnPanel = new JPanel();
    btnPanel.add(this.okBtn);
    btnPanel.add(this.cancelBtn);

    if (msgToDisplay)
      this.getContentPane().add(this.onTopMessage, BorderLayout.PAGE_START);
    this.getContentPane().add(fieldsPanel, BorderLayout.CENTER);
    this.getContentPane().add(btnPanel, BorderLayout.PAGE_END);

    this.getRootPane().setDefaultButton(this.okBtn); // OK button focused by default

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    this.pack();
    this.setResizable(false);
    this.setLocationRelativeTo(parent);
    this.toFront();
  }

  /**
   * @return <code>String</code> login inputed by user in the dialog box.
   */
  public String getLogin() {
    return this.loginFld.getText().trim();
  }

  /**
   * @return <code>String</code> password inputed by user in the dialog box.
   */
  public String getPassword() {
    return new String(this.passwordFld.getPassword());
  }

  /**
   * @return <code>String</code> password confirmation inputed by user in the dialog box.
   */
  public String getPasswordConfirmation() {
    return new String(this.passwordConfirmationFld.getPassword());
  }

  /**
   * This method clear the content of the two dialog box password fields.
   */

  public void clearPassword() {
    this.passwordFld.setText(""); //$NON-NLS-1$
    this.passwordConfirmationFld.setText(""); //$NON-NLS-1$
  }

  /**
   * @return user button click in the dialog box.
   */
  public CredentialsDialogBoxStatus getUserClick() {
    return this.userClick;
  }

  private void setUserClick(final CredentialsDialogBoxStatus userClick_p) {
    this.userClick = userClick_p;
  }
}