package com.guaitilsoft.utils;

public class EmailNewAccountTemplate {

    private String title;
    private String fullName;
    private String email;
    private String genericPassword;
    private final String phoneNumber;

    public EmailNewAccountTemplate() {
        this.title = "GuaitilTour";
        this.fullName = "Default";
        this.email = "default@gmail.com";
        this.genericPassword = "default";
        this.phoneNumber = "59863587";
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

    private String makeTemplate() {
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\"\n" +
                "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <!--[if gte mso 9]>\n" +
                "    <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "            <o:AllowPNG/>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "    </xml>\n" +
                "    <![endif]-->\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                "    <title></title>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        a {\n" +
                "            color: #161a39;\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "\n" +
                "        @media only screen and (min-width: 620px) {\n" +
                "            .u-row {\n" +
                "                width: 600px !important;\n" +
                "            }\n" +
                "\n" +
                "            .u-row .u-col {\n" +
                "                vertical-align: top;\n" +
                "            }\n" +
                "\n" +
                "            .u-row .u-col-100 {\n" +
                "                width: 600px !important;\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        @media (max-width: 620px) {\n" +
                "            .u-row-container {\n" +
                "                max-width: 100% !important;\n" +
                "                padding-left: 0px !important;\n" +
                "                padding-right: 0px !important;\n" +
                "            }\n" +
                "\n" +
                "            .u-row .u-col {\n" +
                "                min-width: 320px !important;\n" +
                "                max-width: 100% !important;\n" +
                "                display: block !important;\n" +
                "            }\n" +
                "\n" +
                "            .u-row {\n" +
                "                width: calc(100% - 40px) !important;\n" +
                "            }\n" +
                "\n" +
                "            .u-col {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            .u-col > div {\n" +
                "                margin: 0 auto;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        tr,\n" +
                "        td {\n" +
                "            vertical-align: top;\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "\n" +
                "        .ie-container table,\n" +
                "        .mso-container table {\n" +
                "            table-layout: fixed;\n" +
                "        }\n" +
                "\n" +
                "        * {\n" +
                "            line-height: inherit;\n" +
                "        }\n" +
                "\n" +
                "        a[x-apple-data-detectors='true'] {\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: none !important;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <!--<![endif]-->\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9\">\n" +
                "<!--[if IE]>\n" +
                "<div class=\"ie-container\"><![endif]-->\n" +
                "<!--[if mso]>\n" +
                "<div class=\"mso-container\"><![endif]-->\n" +
                "<table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\"\n" +
                "       cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "    <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "            <!--[if (mso)|(IE)]>\n" +
                "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                "\n" +
                "\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "                <div class=\"u-row\"\n" +
                "                     style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #a6281c;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]>\n" +
                "                        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding: 0px;background-color: transparent;\" align=\"center\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\">\n" +
                "                                        <tr style=\"background-color: #a6281c;\"><![endif]-->\n" +
                "\n" +
                "                        <!--[if (mso)|(IE)]>\n" +
                "                        <td align=\"center\" width=\"600\"\n" +
                "                            style=\"width: 600px;padding: 15px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"\n" +
                "                            valign=\"top\"><![endif]-->\n" +
                "                        <div class=\"u-col u-col-100\"\n" +
                "                             style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                            <div style=\"width: 100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div style=\"padding: 15px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "\n" +
                "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                                           cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\"\n" +
                "                                                align=\"left\">\n" +
                "\n" +
                "                                                <div style=\"color: #000000; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                                    <p style=\"line-height: 140%; text-align: center; font-size: 14px;\">\n" +
                "                                                        <span style=\"color: #ffffff; font-size: 14px; line-height: 19.6px;\"><span\n" +
                "                                                                style=\"font-size: 28px; line-height: 39.2px;\">"+ this.title + "</span></span>\n" +
                "                                                    </p>\n" +
                "                                                </div>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "                <div class=\"u-row\"\n" +
                "                     style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]>\n" +
                "                        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding: 0px;background-color: transparent;\" align=\"center\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\">\n" +
                "                                        <tr style=\"background-color: #ffffff;\"><![endif]-->\n" +
                "\n" +
                "                        <!--[if (mso)|(IE)]>\n" +
                "                        <td align=\"center\" width=\"600\"\n" +
                "                            style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"\n" +
                "                            valign=\"top\"><![endif]-->\n" +
                "                        <div class=\"u-col u-col-100\"\n" +
                "                             style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                            <div style=\"width: 100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "\n" +
                "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                                           cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\"\n" +
                "                                                align=\"left\">\n" +
                "\n" +
                "                                                <div style=\"color: #000000; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                                            style=\"font-size: 14px; line-height: 19.6px;\">Hola " + this.fullName + ",</span><br/><span\n" +
                "                                                            style=\"font-size: 14px; line-height: 19.6px;\">Gracias por unirte a nuestra comunidad, ahora formas parte de la Asociaci&oacute;n Integral de Guaitil, </span><span\n" +
                "                                                            style=\"font-size: 14px; line-height: 19.6px;\">como miembro tendr&aacute;s la oportunidad de administrar tu local o locales en nuestra plataforma, </span><span\n" +
                "                                                            style=\"font-size: 14px; line-height: 19.6px;\">algunos de los beneficios de los cuales dispondr&aacute;s con esto ser&aacute;n:</span>\n" +
                "                                                    </p>\n" +
                "                                                    <ul>\n" +
                "                                                        <li style=\"font-size: 14px; line-height: 19.6px;\"><span\n" +
                "                                                                style=\"font-size: 14px; line-height: 19.6px;\">Tus locales ser&aacute;n promocionados en la p&aacute;gina informativa, de igual manera tambi&eacute;n</span><br/><span\n" +
                "                                                                style=\"font-size: 14px; line-height: 19.6px;\">contar&aacute;s con la posibilidad de ser invitados a nuestras actividades.</span>\n" +
                "                                                        </li>\n" +
                "                                                        <li style=\"font-size: 14px; line-height: 19.6px;\"><span\n" +
                "                                                                style=\"font-size: 14px; line-height: 19.6px;\">Podr&aacute;s registrar tus productos en tus locales, los cuales tambi&eacute;n ser&aacute;n mostrados en la p&aacute;gina informativa. </span>\n" +
                "                                                        </li>\n" +
                "                                                        <li style=\"font-size: 14px; line-height: 19.6px;\"><span\n" +
                "                                                                style=\"font-size: 14px; line-height: 19.6px;\">Tendr&aacute;s un apartado para que puedas gestionar las ventas de tus productos.</span>\n" +
                "                                                        </li>\n" +
                "                                                    </ul>\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%; text-align: left;\">\n" +
                "                                                        <span style=\"font-size: 14px; line-height: 19.6px;\">Para iniciar sesi&oacute;n, tus datos son:</span><br/><span\n" +
                "                                                            style=\"line-height: 19.6px; font-size: 14px;\">Correo: " + this.email + "</span><br/><span\n" +
                "                                                            style=\"line-height: 19.6px; font-size: 14px;\">Contrase&ntilde;a: " + this.genericPassword + "</span>\n" +
                "                                                    </p>\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%; text-align: left;\">\n" +
                "                                                        &nbsp;</p>\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%; text-align: left;\">\n" +
                "                                                        <span style=\"font-size: 14px; line-height: 19.6px;\">&iexcl;Saludos y bienvenido!</span>\n" +
                "                                                    </p>\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%; text-align: left;\">\n" +
                "                                                        &nbsp;</p>\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                                            style=\"font-size: 14px; line-height: 19.6px; color: #000000;\">Para ingresar a la p&aacute;gina, ingresa en el siguiente bot&oacute;n: </span>\n" +
                "                                                    </p>\n" +
                "                                                </div>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                                           cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px;font-family:'Lato',sans-serif;\"\n" +
                "                                                align=\"left\">\n" +
                "\n" +
                "                                                <div align=\"left\">\n" +
                "                                                    <!--[if mso]>\n" +
                "                                                    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"\n" +
                "                                                           style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:'Lato',sans-serif;\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td style=\"font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                "                                                                <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "                                                                             xmlns:w=\"urn:schemas-microsoft-com:office:word\"\n" +
                "                                                                             href=\"http://143.198.57.165/authentication/login\"\n" +
                "                                                                             style=\"height:51px; v-text-anchor:middle; width:183px;\"\n" +
                "                                                                             arcsize=\"2%\" stroke=\"f\"\n" +
                "                                                                             fillcolor=\"#a6281c\">\n" +
                "                                                                    <w:anchorlock/>\n" +
                "                                                                    <center style=\"color:#FFFFFF;font-family:'Lato',sans-serif;\">\n" +
                "                                                    <![endif]-->\n" +
                "                                                    <a href=\"http://143.198.57.165/authentication/login\" target=\"_blank\"\n" +
                "                                                       style=\"box-sizing: border-box;display: inline-block;font-family:'Lato',sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #a6281c; border-radius: 1px; -webkit-border-radius: 1px; -moz-border-radius: 1px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\">\n" +
                "                                                        <span style=\"display:block;padding:15px 40px;line-height:120%;\"><span\n" +
                "                                                                style=\"font-size: 18px; line-height: 21.6px;\">Iniciar sesi&oacute;n</span></span>\n" +
                "                                                    </a>\n" +
                "                                                    <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\n" +
                "                                                </div>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                                           cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\"\n" +
                "                                                align=\"left\">\n" +
                "\n" +
                "                                                <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\"\n" +
                "                                                       cellspacing=\"0\" width=\"100%\"\n" +
                "                                                       style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #BBBBBB;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                                    <tbody>\n" +
                "                                                    <tr style=\"vertical-align: top\">\n" +
                "                                                        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                "                                                            <span>&#160;</span>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                    </tbody>\n" +
                "                                                </table>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                                           cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:35px 40px 10px;font-family:'Lato',sans-serif;\"\n" +
                "                                                align=\"left\">\n" +
                "\n" +
                "                                                <div style=\"color: #000000; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                                            style=\"color: #888888; font-size: 16px; line-height: 22.4px;\"><em><span\n" +
                "                                                            style=\"line-height: 22.4px; font-size: 16px;\">Por favor ignore este mensaje, si usted no se ha registrado en la p&aacute;gina Guaitil Tour</span></em></span><br/><span\n" +
                "                                                            style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span\n" +
                "                                                            style=\"font-size: 16px; line-height: 22.4px;\">&nbsp;</span></em></span>\n" +
                "                                                    </p>\n" +
                "                                                </div>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "\n" +
                "            <div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                "                <div class=\"u-row\"\n" +
                "                     style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #a6281c;\">\n" +
                "                    <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\">\n" +
                "                        <!--[if (mso)|(IE)]>\n" +
                "                        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding: 0px;background-color: transparent;\" align=\"center\">\n" +
                "                                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\">\n" +
                "                                        <tr style=\"background-color: #a6281c;\"><![endif]-->\n" +
                "\n" +
                "                        <!--[if (mso)|(IE)]>\n" +
                "                        <td align=\"center\" width=\"600\"\n" +
                "                            style=\"width: 600px;padding: 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"\n" +
                "                            valign=\"top\"><![endif]-->\n" +
                "                        <div class=\"u-col u-col-100\"\n" +
                "                             style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                "                            <div style=\"width: 100% !important;\">\n" +
                "                                <!--[if (!mso)&(!IE)]><!-->\n" +
                "                                <div style=\"padding: 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "                                    <!--<![endif]-->\n" +
                "\n" +
                "                                    <table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\"\n" +
                "                                           cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                                        <tbody>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\"\n" +
                "                                                align=\"left\">\n" +
                "\n" +
                "                                                <div style=\"color: #000000; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                                            style=\"font-size: 16px; line-height: 22.4px; color: #ecf0f1;\">Contacto</span>\n" +
                "                                                    </p>\n" +
                "                                                    <p style=\"font-size: 14px; line-height: 140%;\"><span\n" +
                "                                                            style=\"font-size: 14px; line-height: 19.6px; color: #ecf0f1;\">Tel&eacute;fono: " + this.phoneNumber + "</span>\n" +
                "                                                    </p>\n" +
                "                                                </div>\n" +
                "\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "\n" +
                "                                    <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "                        <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "\n" +
                "            <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "<!--[if mso]></div><![endif]-->\n" +
                "<!--[if IE]></div><![endif]-->\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }
}
