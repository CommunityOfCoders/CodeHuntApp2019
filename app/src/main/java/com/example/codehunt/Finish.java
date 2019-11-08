package com.example.codehunt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        TextView statsTV = findViewById(R.id.statsTV);
        SharedPreferences pref = getSharedPreferences(Constants.SP, MODE_PRIVATE);
        StringBuilder stats = new StringBuilder("<h2>Your Stats</h2><br>");
        stats.append("<table>");
        stats.append("<tr> <th>Question No.\t\t\t\t\t\t</th> <th>Time\t\t\t\t\t\t\t</th> <th>Hints\t</th> <th>Effective Time\t</th> <br> </tr>");
        for (int i = 1; i <= 5; i++) {
            long time = (pref.getLong("Q" + i + "Time", 0) -
                    pref.getLong("Q" + (i - 1) + "Time", 0));
            int hints = pref.getInt("Q" + i + "Hints", 0);
            long effTime = time + TeamData.calc_hint_time(hints);
            stats.append("\t\t\t\t\t\t").append("<tr> <td>").append(i).append("\n").
                    append("\t\t\t\t\t\t").append("</td> <td>").append(time).
                    append("\t\t\t\t\t\t").append("</td> <td>").append(hints).
                    append("\t\t\t\t\t\t").append("</td> <td>").append(effTime).append("</td> </tr><br>");
        }
        stats.append("</table>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            statsTV.setText(Html.fromHtml(stats.toString(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            statsTV.setText(Html.fromHtml(stats.toString()));
        }
//        String stats = "Your Stats\n\n";
//        stats += "Question Number\tTime(s)\t\t\tHints\t\tEffective Time";
//        for(int i=1; i<=6;i++) {
//            long time = (pref.getLong("Q" + i + "Time", 0) -
//                    pref.getLong("Q" + (i - 1) + "Time", 0));
//            int hints = pref.getInt("Q" + i + "Hints", 0);
//            long effTime = time + TeamData.calc_hint_time(hints);
//            stats.concat(i+"\t\t"+time+"\t\t"+hints+"\t"+effTime);
//        }
//        statsTV.setText(stats);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You've Completed the Hunt!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, CodehuntActivity.class);
        startActivity(i);
    }
}
