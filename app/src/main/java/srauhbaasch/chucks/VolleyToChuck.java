package srauhbaasch.chucks;
import android.content.Context;


public class VolleyToChuck  {
    private static VolleyToChuck volleyToChuck;



    private VolleyToChuck (Context context){

    }
    public static synchronized VolleyToChuck getInstance(Context context){
        if (volleyToChuck == null) {
            volleyToChuck = new VolleyToChuck(context);
        }
        return volleyToChuck;
    }
}
