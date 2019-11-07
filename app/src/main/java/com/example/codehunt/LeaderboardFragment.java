package com.example.codehunt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

public class LeaderboardFragment extends Fragment {
    private static final String TAG = "LeaderboardFragment";
    FirebaseDatabase database;
    SharedPreferences preferences;
    ArrayList<TeamData> teamDataArrayList;
    LinearLayout leaderboardLinearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard_fragment, container, false);
        leaderboardLinearLayout = view.findViewById(R.id.leaderboardLinearLayout);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        teamDataArrayList = new ArrayList<>();
        preferences = getContext().getSharedPreferences(Constants.SP, MODE_PRIVATE);
        DatabaseReference teams = database.getReference("teams");
        teams.keepSynced(true);
        teams.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                teamDataArrayList.clear();
                for (DataSnapshot team : dataSnapshot.getChildren()) {
                    TeamData teamData = team.getValue(TeamData.class);
                    teamDataArrayList.add(teamData);
                }
                Collections.sort(teamDataArrayList, new SortTeamData());
                leaderboardLinearLayout.removeAllViews();

                leaderboardLinearLayout.addView(new LeaderboardRow(getContext(),
                        "Team Name", "Rank", "Ques", "Time (s)", true, true));

                int rank = 0;

                for (TeamData team : teamDataArrayList) {
                    if(team.name!= null && !team.name.trim().equals(""))
                        if(team.name.equals(preferences.getString(Constants.TeamName, "")))
                            leaderboardLinearLayout.addView(new LeaderboardRow(
                                    getContext(), team.name, Integer.toString(++rank),
                                    Integer.toString(team.current_ques), Integer.toString(team.getTotalTime()), true));
                        else
                            leaderboardLinearLayout.addView(new LeaderboardRow(
                                    getContext(), team.name, Integer.toString(++rank),
                                    Integer.toString(team.current_ques), Integer.toString(team.getTotalTime())));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }

    class SortTeamData implements Comparator<TeamData> {
        public int compare(TeamData a, TeamData b) {
            // ascending in terms of time
            // desc in terms of current_ques
            if (a.current_ques != b.current_ques) {
                return (a.current_ques - b.current_ques) * -10000000;
            } else
                return a.getTotalTime() - b.getTotalTime();
        }
    }
}
