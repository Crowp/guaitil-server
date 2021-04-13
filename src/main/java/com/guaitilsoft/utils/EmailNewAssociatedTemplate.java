package com.guaitilsoft.utils;

public class EmailNewAssociatedTemplate {

    private String fullName;
    private final String title;
    private final String phoneNumber;

    public EmailNewAssociatedTemplate(){
        this.fullName = "Not Found";
        this.title = GuaitilEmailInfo.getTitle();
        this.phoneNumber = GuaitilEmailInfo.getPhoneNumber();
    }

    public EmailNewAssociatedTemplate addFullName(String fullName){
        this.fullName = fullName;
        return this;
    }

    public String getTemplate(){
        return this.makeTemplate();
    }

    public String makeTemplate(){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" >\n" +
                "  <head>\n" +
                "    <!--[if gte mso 9]>\n" +
                "      <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "          <o:AllowPNG />\n" +
                "          <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "      </xml>\n" +
                "    <![endif]-->\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <!--<![endif]-->\n" +
                "    <title></title>\n" +
                "    <style type=\"text/css\">\n" +
                "      a { color: #161a39; text-decoration: underline; }\n" +
                "      @media only screen and (min-width: 620px) { .u-row { width: 600px !important; } .u-row .u-col { vertical-align: top; } .u-row .u-col-100 { width: 600px !important; } }\n" +
                "      @media (max-width: 620px) {b.u-row-container { max-width: 100% !important; padding-left: 0px !important; padding-right: 0px !important; } .u-row .u-col { min-width: 320px !important; max-width: 100% !important; display: block !important; } .u-row { width: calc(100% - 40px) !important; } .u-col { width: 100% !important; } .u-col > div { margin: 0 auto; } }\n" +
                "      body { margin: 0; padding: 0; }\n" +
                "      table,\n" +
                "      tr,\n" +
                "      td { vertical-align: top; border-collapse: collapse; }\n" +
                "      p { margin: 0; }\n" +
                "      .ie-container table,\n" +
                "      .mso-container table { table-layout: fixed; }\n" +
                "      * { line-height: inherit; }\n" +
                "      a[x-apple-data-detectors=\"true\"] { color: inherit !important; text-decoration: none !important; }\n" +
                "    </style>\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <!--<![endif]-->\n" +
                "  </head>\n" +
                "  <body class=\"clean-body\" style=\" margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #f9f9f9; \" >\n" +
                "    <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "    <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "    <table style=\" border-collapse: collapse; table-layout: fixed; border-spacing: 0; vertical-align: top; min-width: 320px; margin: 0 auto; background-color: #f9f9f9; width: 100%; \" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "      <tbody>\n" +
                "        <tr style=\"vertical-align: top\">\n" +
                "          <td style=\" word-break: break-word; border-collapse: collapse !important; vertical-align: top; \" >\n" +
                "            <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px; background-color: transparent\" >\n" +
                "              <div class=\"u-row\" style=\" margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #a6281c; \" >\n" +
                "                <div style=\" border-collapse: collapse; display: table; width: 100%; background-color: transparent; \" >\n" +
                "                  <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #a6281c;\"><![endif]-->\n" +
                "                  <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 15px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                  <div class=\"u-col u-col-100\" style=\" max-width: 320px; min-width: 600px; display: table-cell; vertical-align: top; \" >\n" +
                "                    <div style=\"width: 100% !important\">\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                        <div style=\" padding: 15px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-right: 0px solid transparent; border-bottom: 0px solid transparent; \" ><!--<![endif]--> \n" +
                "                        <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td style=\"overflow-wrap: break-word; word-break: break-word; padding: 10px; font-family: 'Lato', sans-serif; \" align=\"left\" >\n" +
                "                                <div style=\" color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; \" >\n" +
                "                                  <p style=\" line-height: 140%; text-align: center; font-size: 14px; \" >\n" +
                "                                    <span style=\" color: #ffffff; font-size: 14px; line-height: 19.6px; \" >\n" +
                "                                      <span style=\" font-size: 28px; line-height: 39.2px; \" >"+this.title+"</span\n" +
                "                                      ></span >\n" +
                "                                  </p>\n" +
                "                                </div>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "\n" +
                "                        <!--[if (!mso)&(!IE)]><!-->\n" +
                "                      </div>\n" +
                "                      <!--<![endif]-->\n" +
                "                    </div>\n" +
                "                  </div>\n" +
                "                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                  <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                </div>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px; background-color: transparent\" >\n" +
                "              <div class=\"u-row\" style=\" margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #ffffff; \" >\n" +
                "                <div style=\" border-collapse: collapse; display: table; width: 100%; background-color: transparent; \" >\n" +
                "                  <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "                  <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                  <div class=\"u-col u-col-100\" style=\" max-width: 320px; min-width: 600px; display: table-cell; vertical-align: top; \" >\n" +
                "                    <div style=\"width: 100% !important\">\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                        <div style=\" padding: 0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-right: 0px solid transparent; border-bottom: 0px solid transparent; \" ><!--<![endif]-->\n" +
                "                          <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                            <tbody>\n" +
                "                              <tr>\n" +
                "                                <td style=\" overflow-wrap: break-word; word-break: break-word; padding: 40px 40px 30px; font-family: 'Lato', sans-serif; \" align=\"left\" >\n" +
                "                                  <div style=\" color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; \" >\n" +
                "                                    <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "                                      <span style=\" font-size: 14px; line-height: 19.6px; \" >\n" +
                "                                        Hola "+this.fullName+"\n" +
                "                                      </span>\n" +
                "                                      <br /><br />\n" +
                "                                    </p>\n" +
                "                                    <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "                                      <span style=\"font-size: 14px; line-height: 19.6px\">\n" +
                "                                        Gracias por unirte a nuestra comunidad, ahora formas una parte importante\n" +
                "                                        de la Asociaci&oacute;n Integral de Guaitil como asociado, esperamos que tu\n" +
                "                                        estadía aquí sea muy gratificante para que podamos con nuestro trabajo\n" +
                "                                        poder crecer a nuestra comunidad de Guaitil.\n" +
                "                                      </span>\n" +
                "                                    </p>\n" +
                "                                    <br />\n" +
                "                                    <p style=\"font-size: 14px; line-height: 140%; text-align: left\">\n" +
                "                                      <span style=\"font-size: 14px; line-height: 19.6px\">\n" +
                "                                        &iexcl;Saludos y bienvenido!\n" +
                "                                      </span>\n" +
                "                                    </p>                                    \n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </tbody>\n" +
                "                          </table>\n" +
                "\n" +
                "                          <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                            <tbody>\n" +
                "                              <tr>\n" +
                "                                <td style=\" overflow-wrap: break-word; word-break: break-word; padding: 10px; font-family: 'Lato', sans-serif;\" align=\"left\">\n" +
                "                                  <table height=\"0px\" align=\"center\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\" border-collapse: collapse; table-layout: fixed; border-spacing: 0; mso-table-lspace: 0pt; mso-table-rspace: 0pt; vertical-align: top; border-top: 1px solid #bbbbbb; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; \">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td style=\" word-break: break-word; border-collapse: collapse !important; vertical-align: top; font-size: 0px; line-height: 0px; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
                "                                          <span>&#160;</span>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </tbody>\n" +
                "                          </table>\n" +
                "\n" +
                "                          <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                            <tbody>\n" +
                "                              <tr>\n" +
                "                                <td style=\" overflow-wrap: break-word; word-break: break-word; padding: 35px 40px 10px; font-family: 'Lato', sans-serif; \" align=\"left\" >\n" +
                "                                  <div style=\" color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; \">\n" +
                "                                    <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "                                      <span style=\" color: #888888; font-size: 16px; line-height: 22.4px; \">\n" +
                "                                        <em>\n" +
                "\n" +
                "                                          <span style=\"line-height: 22.4px; font-size: 16px; \">\n" +
                "                                            Por favor no contestar este mensaje\n" +
                "                                          </span>\n" +
                "\n" +
                "                                        </em>\n" +
                "                                        </span><br />\n" +
                "                                      <span style=\" color: #888888; font-size: 14px; line-height: 19.6px;\">\n" +
                "                                        <em>\n" +
                "                                          <span style=\"font-size: 16px; line-height: 22.4px;\">\n" +
                "                                            &nbsp;\n" +
                "                                          </span>\n" +
                "                                        </em>\n" +
                "                                      </span>\n" +
                "                                    </p>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </tbody>\n" +
                "                          </table>\n" +
                "\n" +
                "                          <!--[if (!mso)&(!IE)]><!-->\n" +
                "                      </div>\n" +
                "                      <!--<![endif]-->\n" +
                "                    </div>\n" +
                "                  </div>\n" +
                "                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                  <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                </div>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px; background-color: transparent\" >\n" +
                "              <div class=\"u-row\" style=\" margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #a6281c; \" >\n" +
                "                <div style=\" border-collapse: collapse; display: table; width: 100%; background-color: transparent; \" >\n" +
                "                  <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #a6281c;\"><![endif]-->\n" +
                "                  <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                  <div class=\"u-col u-col-100\" style=\" max-width: 320px; min-width: 600px; display: table-cell; vertical-align: top; \" >\n" +
                "                    <div style=\"width: 100% !important\">\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                        <div style=\" padding: 20px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-right: 0px solid transparent; border-bottom: 0px solid transparent; \" ><!--<![endif]-->\n" +
                "                        \n" +
                "                        <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td style=\" overflow-wrap: break-word; word-break: break-word; padding: 10px; font-family: 'Lato', sans-serif; \" align=\"left\" >\n" +
                "                                <div style=\" color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; \" >\n" +
                "                                  <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "                                    <span style=\" font-size: 16px; line-height: 22.4px; color: #ecf0f1;\">\n" +
                "                                      Contacto\n" +
                "                                    </span >\n" +
                "                                  </p>\n" +
                "                                  <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "                                    <span style=\" font-size: 14px; line-height: 19.6px; color: #ecf0f1; \">\n" +
                "                                      Tel&eacute;fono: +506 "+this.phoneNumber+"\n" +
                "                                    </span >\n" +
                "                                  </p>\n" +
                "                                </div>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "\n" +
                "                        <!--[if (!mso)&(!IE)]><!-->\n" +
                "                      </div>\n" +
                "                      <!--<![endif]-->\n" +
                "                    </div>\n" +
                "                  </div>\n" +
                "                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                  <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                </div>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "            <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "    <!--[if mso]></div><![endif]-->\n" +
                "    <!--[if IE]></div><![endif]-->\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
