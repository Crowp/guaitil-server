package com.guaitilsoft.utils;

import com.guaitilsoft.models.constant.TypeEmail;

public class EmailNewAccountTemplate {

    private String title;
    private String fullName;
    private String email;
    private String genericPassword;
    private String typeInformation;
    private final String phoneNumber;
    private final String urlGuaitil;

    public EmailNewAccountTemplate() {
        this.title = GuaitilEmailInfo.getTitle();
        this.fullName = "Default";
        this.email = "default@gmail.com";
        this.genericPassword = "default";
        this.typeInformation = "";
        this.phoneNumber = GuaitilEmailInfo.getPhoneNumber();
        this.urlGuaitil = GuaitilEmailInfo.getUrlGuaitil();
    }

    public EmailNewAccountTemplate addTitle(String title) {
        this.title = title;
        return this;
    }

    public EmailNewAccountTemplate addFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public EmailNewAccountTemplate addEmail(String email) {
        this.email = email;
        return this;
    }

    public EmailNewAccountTemplate addGenericPassword(String genericPassword) {
        this.genericPassword = genericPassword;
        return this;
    }

    public String getTemplate(){
        return this.makeTemplate();
    }

    public EmailNewAccountTemplate typeEmail(TypeEmail typeEmail){
        switch (typeEmail){
            case NEWACCOUNTMEMBER:
                typeInformation = "<p style=\"font-size: 14px; color: #000000; line-height: 140%\">\n" +
                        "    <span style=\"font-size: 14px; line-height: 19.6px;\">\n" +
                        "      Gracias por unirte a nuestra comunidad,\n" +
                        "      ahora formas parte de la Asociaci&oacute;n\n" +
                        "      Integral de Guaitil, como miembro\n" +
                        "      tendr&aacute;s la oportunidad de\n" +
                        "      administrar tu local o locales en nuestra\n" +
                        "      plataforma, algunos de los beneficios de\n" +
                        "      los cuales dispondr&aacute;s con esto\n" +
                        "      ser&aacute;n:\n" +
                        "    </span>\n" +
                        "    <ul>\n" +
                        "      <li style=\" font-size: 14px; line-height: 19.6px; \" >\n" +
                        "        <span style=\" font-size: 14px; line-height: 19.6px; \">\n" +
                        "          Tus locales ser&aacute;n promocionados\n" +
                        "          en la p&aacute;gina informativa, de\n" +
                        "          igual manera tambi&eacute;n\n" +
                        "        </span><br />\n" +
                        "        <span style=\" font-size: 14px; line-height: 19.6px;\">\n" +
                        "          contar&aacute;s con la posibilidad de\n" +
                        "          ser invitados a nuestras\n" +
                        "          actividades.\n" +
                        "        </span>\n" +
                        "      </li>\n" +
                        "      <li style=\" font-size: 14px; line-height: 19.6px; \">\n" +
                        "        <span style=\" font-size: 14px; line-height: 19.6px; \">\n" +
                        "          Podr&aacute;s registrar tus productos\n" +
                        "          en tus locales, los cuales\n" +
                        "          tambi&eacute;n ser&aacute;n mostrados en\n" +
                        "          la p&aacute;gina informativa.\n" +
                        "        </span>\n" +
                        "      </li>\n" +
                        "      <li style=\" font-size: 14px; line-height: 19.6px; \">\n" +
                        "        <span style=\" font-size: 14px; line-height: 19.6px;\">\n" +
                        "          Tendr&aacute;s un apartado para que\n" +
                        "          puedas gestionar las ventas de tus\n" +
                        "          productos.\n" +
                        "        </span>\n" +
                        "      </li>\n" +
                        "    </ul>\n" +
                        "  </p>";
                break;
            case RESETPASSWORD:
                typeInformation = "  <p style=\"font-size: 14px; color: #000000; line-height: 140%\">\n" +
                        "    <span style=\" font-size: 14px; line-height: 19.6px; \">\n" +
                        "      Tu contraseña ha sido restablecida\n" +
                        "      exitosamente.\n" +
                        "    </span><br /><br />\n" +
                        "  </p>";
                break;
            case NEWACCOUNTADMIN:
                typeInformation = " <p style=\"font-size: 14px; color: #000000; line-height: 140%\">\n" +
                        "    <span  style=\" font-size: 14px; line-height: 19.6px; \">\n" +
                        "      Bienvenido a la parte administrativa de\n" +
                        "      la plataforma de la Asociación Integral de\n" +
                        "      Guaitil, como administrador, tendrás la\n" +
                        "      responsabilidad de poder gestionar los\n" +
                        "      diferentes módulos de la plataforma, es\n" +
                        "      importante que realices cualquier acto en\n" +
                        "      la plataforma con el debido cuidado, ahora\n" +
                        "      podrás llevar a cabo las siguientes\n" +
                        "      actividades:\n" +
                        "    </span>\n" +
                        "    <ul>\n" +
                        "        <li style=\"font-size: 14px;line-height: 19.6px;\" >\n" +
                        "            <span style=\"font-size: 14px; line-height: 19.6px;\">\n" +
                        "                En el módulo de galleria, podrás ver,\n" +
                        "                insertar y eliminar las imágenes que se\n" +
                        "                mostrarán en la página\n" +
                        "                informativa.\n" +
                        "            </span >\n" +
                        "        </li>\n" +
                        "        <li style=\"font-size: 14px; line-height: 19.6px; \"  >\n" +
                        "          <span style=\"font-size: 14px;line-height: 19.6px;\" >\n" +
                        "            En el módulo de administradores, podrás\n" +
                        "            ver los demás administradores de la\n" +
                        "            plataforma.\n" +
                        "          </span>\n" +
                        "        </li>\n" +
                        "        <li style=\" font-size: 14px; line-height: 19.6px; \" >\n" +
                        "          <span style=\"font-size: 14px; line-height: 19.6px; \">\n" +
                        "            En el módulo de actividades, serás\n" +
                        "            capaz de ver, exportar, filtrar,\n" +
                        "            ingresar, actualizar, eliminar y mostrar\n" +
                        "            las actividades en la pagína\n" +
                        "            informativa.\n" +
                        "        </span>\n" +
                        "        </li>\n" +
                        "        <li style=\"font-size: 14px; line-height: 19.6px; \" >\n" +
                        "            <span style=\" font-size: 14px;line-height: 19.6px;\">\n" +
                        "                En el módulo de revisión, realizarás\n" +
                        "                las revisiones de los productos\n" +
                        "                ingresados por los miembros con locales,\n" +
                        "                deberás revisar dichos productos,\n" +
                        "                comprobando que la información sea\n" +
                        "                correctamente escrita, así como también\n" +
                        "                sea relevante y las imágenes sean\n" +
                        "                apropiadas.\n" +
                        "            </span>\n" +
                        "        </li>\n" +
                        "        <li style=\" font-size: 14px; line-height: 19.6px;\">\n" +
                        "            <span style=\" font-size: 14px;line-height: 19.6px;  \">\n" +
                        "                En el módulo de miembros, podrás ver,\n" +
                        "                exportar, filtrar, ingresar, actualizar\n" +
                        "                y eliminar los miembros de la\n" +
                        "                asociación.\n" +
                        "            </span>\n" +
                        "        </li>\n" +
                        "        <li style=\" font-size: 14px;line-height: 19.6px; \">\n" +
                        "            <span style=\" font-size: 14px; line-height: 19.6px;\">\n" +
                        "                En el módulo de locales, serás capaz de\n" +
                        "                ver, exportar, filtrar, ingresar,\n" +
                        "                actualizar, eliminar y mostrar los\n" +
                        "                locales en la pagína informativa.\n" +
                        "            </span>\n" +
                        "        </li>\n" +
                        "        <li style=\" font-size: 14px; line-height: 19.6px; \" >\n" +
                        "            <span style=\"font-size: 14px; line-height: 19.6px;\">\n" +
                        "                En el módulo de reservaciones, serás\n" +
                        "                capaz de ver, exportar, filtrar,\n" +
                        "                ingresar, actualizar y eliminar las\n" +
                        "                reservaciones de los tours.\n" +
                        "            </span>\n" +
                        "        </li>\n" +
                        "      </ul>\n" +
                        "  </p>\n" +
                        " ";

        }
        return this;
    }

    private String makeTemplate(){
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
                "    <table\n" +
                "      style=\" border-collapse: collapse; table-layout: fixed; border-spacing: 0; vertical-align: top; min-width: 320px; margin: 0 auto; background-color: #f9f9f9; width: 100%; \" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "      <tbody>\n" +
                "        <tr style=\"vertical-align: top\">\n" +
                "          <td style=\" word-break: break-word; border-collapse: collapse !important; vertical-align: top; \" >\n" +
                "            <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px; background-color: transparent\" >\n" +
                "              <div class=\"u-row\" style=\" margin: 0 auto; min-width: 320px; max-width: 600px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #a6281c; \" >\n" +
                "                <div style=\" border-collapse: collapse; display: table; width: 100%; background-color: transparent; \" >\n" +
                "                  <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #a6281c;\"><![endif]-->\n" +
                "\n" +
                "                  <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 15px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                "                  <div class=\"u-col u-col-100\" style=\" max-width: 320px; min-width: 600px; display: table-cell; vertical-align: top; \" >\n" +
                "                    <div style=\"width: 100% !important\">\n" +
                "                      <!--[if (!mso)&(!IE)]><!-->\n" +
                "                        <div style=\" padding: 15px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-right: 0px solid transparent; border-bottom: 0px solid transparent; \" ><!--<![endif]-->\n" +
                "                          \n" +
                "                        <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td style=\"overflow-wrap: break-word; word-break: break-word; padding: 10px; font-family: 'Lato', sans-serif; \" align=\"left\" >\n" +
                "                                <div style=\" color: #000000; line-height: 140%; text-align: left; word-wrap: break-word; \" >\n" +
                "                                  <p style=\" line-height: 140%; text-align: center; font-size: 14px; \" >\n" +
                "                                    <span style=\" color: #ffffff; font-size: 14px; line-height: 19.6px; \" >\n" +
                "                                      <span style=\" font-size: 28px; line-height: 39.2px; \" >"+ this.title +"</span\n" +
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
                "                                    "+this.typeInformation+"\n" +
                "                                    <p style=\" font-size: 14px; color: #000000; line-height: 140%; text-align: left; \" >\n" +
                "\n" +
                "                                      <span style=\" font-size: 14px; line-height: 19.6px; \" >\n" +
                "                                        Para iniciar sesi&oacute;n, tus datos son:\n" +
                "                                      </span><br />\n" +
                "\n" +
                "                                      <span style=\" line-height: 19.6px; font-size: 14px; \" >\n" +
                "                                        Correo: "+ this.email +"\n" +
                "                                      </span ><br />\n" +
                "\n" +
                "                                      <span style=\"line-height: 19.6px; font-size: 14px; \">\n" +
                "                                        Contrase&ntilde;a: "+ this.genericPassword +"\n" +
                "                                      </span>\n" +
                "\n" +
                "                                    </p><br/><br/>"+
                "                                    <p style=\" font-size: 14px; line-height: 140%; text-align: left;\">\n" +
                "\n" +
                "                                      <span style=\" font-size: 14px; line-height: 19.6px;\">\n" +
                "                                        &iexcl;Saludos!\n" +
                "                                      </span>\n" +
                "                                    </p><br />\n" +
                "                                    <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "\n" +
                "                                      <span style=\"font-size: 14px; line-height: 19.6px; color: #000000; \">\n" +
                "                                        Para ingresar a la p&aacute;gina, dale click en el siguiente bot&oacute;n:\n" +
                "                                      </span>\n" +
                "\n" +
                "                                    </p>\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </tbody>\n" +
                "                          </table>\n" +
                "\n" +
                "                          <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                            <tbody>\n" +
                "                              <tr>\n" +
                "                                <td style=\" overflow-wrap: break-word; word-break: break-word; padding: 0px 40px; font-family: 'Lato', sans-serif;\" align=\"left\">\n" +
                "                                  <div align=\"left\">\n" +
                "                                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:'Lato',sans-serif;\"><tr><td style=\"font-family:'Lato',sans-serif;\" align=\"left\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://143.198.57.165/authentication/login\" style=\"height:51px; v-text-anchor:middle; width:183px;\" arcsize=\"2%\" stroke=\"f\" fillcolor=\"#a6281c\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:'Lato',sans-serif;\"><![endif]-->\n" +
                "                                    <a href=\""+this.urlGuaitil+"\" target=\"_blank\" style=\" box-sizing: border-box; display: inline-block; font-family: 'Lato', sans-serif; text-decoration: none; -webkit-text-size-adjust: none; text-align: center; color: #ffffff; background-color: #a6281c; border-radius: 1px; -webkit-border-radius: 1px; -moz-border-radius: 1px; width: auto; max-width: 100%; overflow-wrap: break-word; word-break: break-word; word-wrap: break-word; \" >\n" +
                "                                      <span style=\"display: block; padding: 15px 40px; line-height: 120%; \">\n" +
                "\n" +
                "                                          <span style=\" font-size: 18px; line-height: 21.6px; \">\n" +
                "                                            Iniciar sesi&oacute;n\n" +
                "                                          </span>\n" +
                "\n" +
                "                                      </span>\n" +
                "                                    </a>\n" +
                "                                    <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\n" +
                "                                  </div>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </tbody>\n" +
                "                          </table>\n" +
                "\n" +
                "                          <table style=\"font-family: 'Lato', sans-serif\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n" +
                "                            <tbody>\n" +
                "                              <tr>\n" +
                "                                <td style=\" overflow-wrap: break-word; word-break: break-word; padding: 40px 40px 30px; font-family: 'Lato', sans-serif; \" align=\"left\" >\n" +
                "                                  <div style=\" color: #000000; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                    <p style=\"font-size: 14px; line-height: 140%\">\n" +
                "\n" +
                "                                      <span style=\" font-size: 14px; line-height: 19.6px;\">\n" +
                "                                        En caso de que el botón no funcione, dele\n" +
                "                                        click al siguiente enlace:\n" +
                "                                      </span>\n" +
                "\n" +
                "                                      <a style=\"color: #4c7bfa\" href=\""+this.urlGuaitil+"\" target=\"_blank\">\n" +
                "                                        "+this.urlGuaitil+"\n" +
                "                                      </a>\n" +
                "\n" +
                "                                    </p>\n" +
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
                "                                      Tel&eacute;fono: +506 "+ this.phoneNumber +"\n" +
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
