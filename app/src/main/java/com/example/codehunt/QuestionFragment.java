package com.example.codehunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";
    private TextView questionNumber;
    private EditText passCode;
    private Button nextButton;
    private Button hintsButton;
    SharedPreferences pref;
    private int[] passcodes = {124067, 834592, 348215, 672153, 681354};
    private String[] questions = {
            "Round 1\n\nQuestion:Given an array of size n, print the value of minimum odd element.\n" +
                    "\n" +
                    "Input format: Enter a number N, input N elements from the user\n" +
                    "\n" +
                    "Output format: A single digit representing the value of minimum odd element ,if there is no odd element found print \"Not found\" (without double quotes).\n" +
                    "\n" +
                    "Example :\n" +
                    "4 \n" +
                    "1 2 3 4 \n" +
                    "output: 1\n" +
                    "\n" +
                    "5\n" +
                    "4 6 8 9 3\n" +
                    "output :3\n" +
                    "\n" +
                    "3\n" +
                    "6 8 10\n" +
                    "output: Not found\n",          // Lab3 -> BCTLab           // 124067
                "Round 2\n\n1. I am fond of gravitational force who has coloured several lives! (2)\n" +
                    "\n" +
                    "2.  I am a programming language such that if you insert 1 ruppee in atm you get 10^6 rupees out. (6)\n" +
                    "\n" +
                    "3. Usually without a doctor, I am someone who loves travelling through wildlife.(1)\n" +
                    "\n" +
                    "4. I love eating desserts but didn't get any after pie!  (6)\n" +
                    " \n" +
                    "5. (Someone cracks a joke) \n" +
                    "A random guy: OMG! Lol.\n" +
                    "Another guy: I am rolling on the floor laughing! \n" +
                    "\n" +
                    "loool iz 20\n" +
                    "loooooool iz 74\n" +
                    "rtfm \n" +
                    "   wtf loool iz liek loooooool \n" +
                    "       tldr \n" +
                    "   brb \n" +
                    "   LMAO loool\n" +
                    "brb \n" +
                    "rofl loool \n" +
                    "can you find a character behind all this? \n" +
                    "\n" +
                    "6. A shopping center but with a barrier. (5)\n" +
                    "\n" +
                    "7. Want to shop to your fullest? Essentially gaining a victory! Let's explore near the sundarbans then! (2)\n" +
                    "\n",      // BCTLab -> VLSILab        // 834592
            "Round 3\n\nMunicipal Subdivision of Bahretal, Germany.\n" +
                    "\n" +
                    "To know the answer:\n" +
                    "“Ask  us  no  questions  and  we’ll  tell  you  no  lies”\n" +
                    "\n" +
                    "These days will help you find your next location:\n\n" +
                    "37 years since the foundation of Microsoft.\n\nThe day when Dolly the sheep died.\n\n" +
                    "Nicolas Cage’s 37th birthday.\n\nApple Inc.’s 30th anniversary.\n\n" +
                    "Justin Gatlin won 100m, World Athletics Championships, Helsinki, Finland.\n\n" +
                    "One year since the launch of Opportunity.\n\n" +
                    "7 years since the production of first digital cinema production in Europe.\n\n" +
                    "33 years since Neil Armstrong stepped on moon.",               // VLSILab -> NCC           // 348215
            "Round 4\n\nPROBLEM STATEMENT 1 :\n" +
                    "Write a function rotate(ar[], d, n) that rotates arr[] of size n by d elements.\n\n" +
                    "INPUT :\n" +
                    "arr[] = {1,2,3,4,5,6,7}\n" +
                    "d = 2\n\n" +
                    "OUTPUT:\n" +
                    "arr[] = {3,4,5,6,7,1,2}\n\n",             // Lab3Q1 -> Lab3Q2         // 672153
            "Round 4\n\nPROBLEM STATEMENT 2:\n" +
                    "\n" +
                    "For a given square matrix, find its transpose and print it on the terminal.\n" +
                    "\n" +
                    "INPUT:\n" +
                    "The first line contains a single integer n. The next lines contain a square matrix of size n\n" +
                    "\n" +
                    "OUTPUT:\n" +
                    "Print the transpose of matrix on a new line.\n"              // Lab3Q2 -> END            // 681354
    };
    private int curr_question; // Real_Q - 1
    private int curr_hints;
    private long curr_start_time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);
        questionNumber = view.findViewById(R.id.question_number);
        passCode = view.findViewById(R.id.passcode);
        nextButton = view.findViewById(R.id.next);
        hintsButton = view.findViewById(R.id.hintsButton);
        pref = getContext().getSharedPreferences(Constants.SP, MODE_PRIVATE);

        curr_question = pref.getInt(Constants.CurrentQuestion, 0); // Real_Q-1
        curr_hints = pref.getInt("Q" + (curr_question + 1) + "Hints", 0);
        curr_start_time = pref.getLong("Q" + curr_question + "Time", System.currentTimeMillis() / 1000);
        Log.e(TAG, String.format("onCreateView: %d, %d, %d", curr_question, curr_hints, curr_start_time));

        if (curr_question >= 5) {
//            Intent i = new Intent(getContext(), com.example.codehunt.Finish.class);
//            startActivity(i);
//////            return view;
            Toast.makeText(getContext(), "WOAH You did it!!", Toast.LENGTH_SHORT).show();
            passCode.setVisibility(View.GONE);
            questionNumber.setText("CONGRATULATIONS!!!");
            nextButton.setEnabled(false);
            nextButton.setVisibility(View.GONE);
            hintsButton.setEnabled(false);
            hintsButton.setVisibility(View.GONE);
            questionNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            questionNumber.setTextSize(30);
            questionNumber.setTextColor(getResources().getColor(R.color.green));

//            Intent intent = new Intent(getContext(), CodehuntActivity.class);
//            startActivity(intent);
        }
        hintsButton.setText(String.format(Locale.ENGLISH, "TAKE A HINT (%d LEFT)", 3 - curr_hints));

        if (curr_question >= 0 && curr_question <= 4)
            questionNumber.setText(questions[curr_question]);

        passCode.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_GO) {
                onClickNext();
                handled = true;
            }
            return handled;
        });

        nextButton.setOnClickListener(v -> onClickNext());
        hintsButton.setOnClickListener(v -> onClickHints());
        view.findViewById(R.id.rulesButton).setOnClickListener(v -> onClickRules());
        return view;
    }

    private void onClickHints() {
        if (curr_hints < 3) {
            long time = System.currentTimeMillis() / 1000;
            Log.e(TAG, "onCreateView: Time = " + time);
            if (time >= curr_start_time + (curr_hints + 1) * 300) { // TODO Change to 300
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Are you sure you want to take a hint?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setMessage("Ask a volunteer to give you a hint");
                        alertDialogBuilder.setPositiveButton("Yes, the volunteer gave me a hint", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                curr_hints++;
                                pref.edit().putInt("Q" + curr_question + "Hints", curr_hints).commit();
                                hintsButton.setText(String.format(Locale.ENGLISH, "TAKE A HINT (%d LEFT)", 3 - curr_hints));
                            }
                        });
                        alertDialogBuilder.create().show();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.create().show();
            } else {
                Toast.makeText(getContext(), "Think!\nThere's still time before you are allowed to take a hint", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "You don't have any hints left...", Toast.LENGTH_LONG).show();
        }
    }

    private void onClickRules() {
        startActivity(new Intent(getContext(),Rules.class));
//        final Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.rules_dialog_layout);
//        dialog.setTitle("Rules About Hints");
//        Button dialogButton = dialog.findViewById(R.id.next);
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
    }

    private void onClickNext() {
        if (passCode.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(), "Please Enter the Passcode", Toast.LENGTH_LONG).show();
        } else {
            String codeString = passCode.getText().toString().trim();
            passCode.setText("");
            passCode.setHint("Passcode");
            if (curr_question < 5 && codeString.length() == 6 && Integer.parseInt(codeString.substring(0, 6)) == passcodes[curr_question]) {
                Log.e(TAG, "onClickNext: time = " + Calendar.getInstance().getTimeInMillis());
                long time = System.currentTimeMillis() / 1000;
                curr_question++;
//                Log.e(TAG, String.format("onClick: %d, %d, %d", curr_question, curr_hints, time));
                Log.e(TAG, "onClickNext: curr_start_time = " + curr_start_time);
                Log.e(TAG, "onClickNext: time = " + time);
                Log.e(TAG, "TIME: " + (time - curr_start_time));
                updateFBDB(curr_question, curr_hints, curr_start_time, time);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(Constants.CurrentQuestion, curr_question);
                editor.putInt("Q" + curr_question + "Hints", curr_hints);
                editor.putLong("Q" + curr_question + "Time", time); // end time of ques no. curr_ques
                editor.commit();
                curr_start_time = time;
                curr_hints = 0;

                if (curr_question == 5) {   // all questions solved
                    questionNumber.setText("CONGRATULATIONS!!!");
                    passCode.setVisibility(View.GONE);
                    nextButton.setEnabled(false);
                    nextButton.setVisibility(View.GONE);
                    hintsButton.setEnabled(false);
                    hintsButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Great Going!", Toast.LENGTH_LONG).show();
                    questionNumber.setText(questions[curr_question]);
                    hintsButton.setText(String.format(Locale.ENGLISH, "TAKE A HINT (%d LEFT)", 3 - curr_hints));
                }
            } else {
                Toast.makeText(getContext(), "Incorrect Passcode", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateFBDB(final int curr_question, final int current_hints, final long start, final long end) {
        long time = end - start;
        final DatabaseReference teams = FirebaseDatabase.getInstance().getReference().child("teams");
        final String key = pref.getString(Constants.Key, Constants.Key);
        teams.child(key).child(Constants.FB_CurrentQues).setValue(curr_question + 1);
//        long time = (int) (pref.getLong("Q" + curr_question + "Time", 0) -
//                pref.getLong("Q" + (curr_question - 1) + "Time", 0));
        Log.e(TAG, "updateFBDB: start = " + start);
        Log.e(TAG, "updateFBDB: end = " + end);
        Log.e(TAG, "updateFBDB: hintsPenalty = " + TeamData.calc_hint_time(current_hints));
        time += TeamData.calc_hint_time(current_hints);
        teams.child(key).child("q" + curr_question).setValue(time);
        Log.e(TAG, "onDataChange: curr_ques = " + (curr_question + 1));
        Log.e(TAG, "onDataChange: q" + curr_question + " = " + time);
    }
}
