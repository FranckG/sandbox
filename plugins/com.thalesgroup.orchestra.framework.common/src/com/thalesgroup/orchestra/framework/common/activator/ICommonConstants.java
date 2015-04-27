/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.activator;

/**
 * Shared constants.
 * @author t0076261
 */
public interface ICommonConstants {
  /**
   * Close bracket string.
   */
  public static final String BRACKET_CLOSE_STRING = "]"; //$NON-NLS-1$
  /**
   * Open bracket string.
   */
  public static final String BRACKET_OPEN_STRING = "["; //$NON-NLS-1$
  /**
   * Comma character.
   */
  public static final char COMMA_CHARACTER = ',';
  /**
   * The empty string.
   */
  static final String EMPTY_STRING = ""; //$NON-NLS-1$
  /**
   * The environment version attribute key.
   */
  public static final String ENVIRONMENT_KEY_VERSION = "EnvVersion"; //$NON-NLS-1$
  /**
   * Context file extension.
   */
  public static final String FILE_EXTENSION_CONTEXTS = "contexts"; //$NON-NLS-1$
  /**
   * The platform path of the default context.
   */
  public static final String DEFAULT_CONTEXTS_PATH = "com.thalesgroup.orchestra.framework.model.handler/context/default." + FILE_EXTENSION_CONTEXTS; //$NON-NLS-1$
  /**
   * Close parenthesis character.
   */
  public static final char PARENTHESIS_CLOSE_CHARACTER = ')';
  /**
   * Open parenthesis character.
   */
  public static final char PARENTHESIS_OPEN_CHARACTER = '(';
  /**
   * The path separator for categories.
   */
  public static final String PATH_SEPARATOR = "\\"; //$NON-NLS-1$
  /**
   * % character.
   */
  public static final char PERCENT_CHARACTER = '%';
  /**
   * Point character.
   */
  public static final char POINT_CHARACTER = '.';
  /**
   * Semi-colon character.
   */
  public static final char SEMI_COLON_CHARACTER = ';';
  /**
   * Semi-colon string.
   */
  public static final String SEMI_COLON_STRING = String.valueOf(SEMI_COLON_CHARACTER);
  /**
   * Comma string.
   */
  public static final String COMMA_STRING = String.valueOf(COMMA_CHARACTER);
}