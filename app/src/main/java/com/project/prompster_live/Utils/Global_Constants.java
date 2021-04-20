package com.project.prompster_live.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;

public class Global_Constants {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public  static String regularExpresionOfEmailId =
            "^(?!.{51})([A-Za-z0-9])+([A-Za-z0-9._-])+@([A-Za-z0-9._-])+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static ProgressDialog mProgressDialog;

    public static String  arr[][] = {{"arial.ttf","arialbd.ttf","arialbi.ttf","ariali.ttf",},{"cour.ttf","courbd.ttf","courbi.ttf","couri.ttf"},
            {"pala.ttf","palab.ttf","palabi.ttf","palai.ttf"}, {"georgia.ttf","georgiab.ttf","georgia bold italic.ttf","georgiai.ttf"}, {"GIL_____.ttf","GILB____.ttf","GILBI___.ttf","GILI____.ttf"},
            {"Helvetica.ttf","Helvetica-Bold.ttf","Helvetica-Bold-Font.ttf","georgiai.ttf"},{"HelveticaNeue.ttf","HelveticaNeueBd.ttf","Helvetica-Bold-Font.ttf","HelveticaNeueIt.ttf"},
            {"pala.ttf","palab.ttf","palabi.ttf","palai.ttf"}, {"times.ttf","timesbd.ttf","timesbi.ttf","timesi.ttf"},
            {"verdana.ttf","verdanab.ttf","verdanai.ttf","timesi.ttf"}};

}
