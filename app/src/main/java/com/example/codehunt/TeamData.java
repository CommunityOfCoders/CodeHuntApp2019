package com.example.codehunt;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TeamData {
    public String name;
    public int current_ques, q1, q2, q3, q4, q5, q6;
    public TeamData(String name, int current_ques, long start_time, int q1, int q2, int q3, int q4, int q5, int q6) {
        this.name = name;
        this.current_ques = current_ques;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
    }

    public TeamData() {
        // Default constructor required for calls to DataSnapshot.getValue(TeamData.class)
    }

    int getTotalTime() {
        int tt = 0;
        if(q1 >= 0)
            tt += q1;
        if(q2 >= 0)
            tt += q2;
        if(q3 >= 0)
            tt += q3;
        if(q4 >= 0)
            tt += q4;
        if(q5 >= 0)
            tt += q5;
        if(q6 >= 0)
            tt += q6;
        return tt;
    }

    static int calc_hint_time(int hints) {
        switch (hints) {
            case 1:
                return 300; //5 mins
            case 2:
                return 720; // 5 + 7 mins
            case 3:
                return 1320; // 5 + 7 + 10 mins
            default:
                return 0;
        }
    }
}