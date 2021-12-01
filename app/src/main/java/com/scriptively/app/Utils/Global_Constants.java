package com.scriptively.app.Utils;

import android.app.ProgressDialog;

public class Global_Constants {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
        public final static String PROMPT_TEXT_SIZE = "PROMPT_TEXT_SIZE";
        public final static String SCRIPT_TEXT_SIZE = "SCRIPT_TEXT_SIZE";
        public final static String PROMPT_SPEED = "PROMPT_SPEED";
        public final static String STORYBOARD_ID = "STORYBOARD ID";
        public final static String SCRIPT_PRIMARYKEY = "SCRIPT PRIMARY KEY";
    public  static String regularExpresionOfEmailId =
            "^(?!.{51})([A-Za-z0-9])+([A-Za-z0-9._-])+@([A-Za-z0-9._-])+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static ProgressDialog mProgressDialog;

    public static String  arr[][] = {{"arial.ttf","arialbd.ttf","arialbi.ttf","ariali.ttf",},{"cour.ttf","courbd.ttf","courbi.ttf","couri.ttf"},
            {"pala.ttf","palab.ttf","palabi.ttf","palai.ttf"}, {"georgia.ttf","georgiab.ttf","georgia bold italic.ttf","georgiai.ttf"}, {"GIL_____.ttf","GILB____.ttf","GILBI___.ttf","GILI____.ttf"},
            {"Helvetica.ttf","Helvetica-Bold.ttf","Helvetica-Bold-Font.ttf","georgiai.ttf"},{"HelveticaNeue.ttf","HelveticaNeueBd.ttf","Helvetica-Bold-Font.ttf","HelveticaNeueIt.ttf"},
            {"pala.ttf","palab.ttf","palabi.ttf","palai.ttf"}, {"times.ttf","timesbd.ttf","timesbi.ttf","timesi.ttf"},
            {"verdana.ttf","verdanab.ttf","verdanai.ttf","timesi.ttf"}};

}
