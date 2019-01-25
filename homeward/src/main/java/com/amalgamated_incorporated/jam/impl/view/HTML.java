package com.amalgamated_incorporated.jam.impl.view;

public final class HTML {
  /**
   * Text formatting TODO move to own place
   */
  private final static String htmlPrepend = "<html><body style='width: 460px; font-family: Courier, Arial, Helvetica, sans-serif;'>";
  private final static String htmlPostpend = "</body></html>";

  public static String htmlify(String string) {
    return String.format("%s%s%s", htmlPrepend, string, htmlPostpend);
  }

  public static String boldify(String string) {
    return String.format("%s%s%s", "<b>", string, "</b>");
  }

  public static String linkify(String linkName, String linkText) {
    return String.format("<a href='%s'>%s</a>", linkName, linkText);
  }
}
