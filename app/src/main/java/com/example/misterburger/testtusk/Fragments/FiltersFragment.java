package com.example.misterburger.testtusk.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misterburger.testtusk.Controller.Controller;
import com.example.misterburger.testtusk.Main2Activity;
import com.example.misterburger.testtusk.R;
import com.example.misterburger.testtusk.R2;
import com.example.misterburger.testtusk.Utility.FragmentFlags;
import com.example.misterburger.testtusk.Utility.TMPData;
import com.example.misterburger.testtusk.model.ResponseActive;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltersFragment extends Fragment {
    @BindView(R2.id.source_et)
    EditText source_et;
    @BindView(R2.id.date_from_btn)
    Button date_from_btn;
    @BindView(R2.id.date_from_tv)
    TextView date_from_tv;
    @BindView(R2.id.date_to_btn)
    Button date_to_btn;
    @BindView(R2.id.date_to_tv)
    TextView date_to_tv;
    @BindView(R2.id.apply_filters)
    Button apply_btn;
    @BindView(R2.id.query_et)
    EditText query_et;
    @BindView(R2.id.sort_spnr)
    Spinner sort_spnr;

    Controller controller;
    TMPData tmpData;
    ResponseActive active;
    DatePickerDialog datePickerDialog;


    private String query;
    private String source;
    private String sortBy;
    private String dateFrom;
    private String dateTo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filtres_fragment, container, false);
        ButterKnife.bind(this, view);
        controller = ((Main2Activity) getActivity()).getController();
        tmpData = controller.getTmpData();
        active = controller.getActive();
        view.setOnTouchListener((v, event) -> true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apply_btn.setOnClickListener(view1 -> {
            getQuery();
            getSource();
            choseAction();
            ((Main2Activity) getActivity()).getFragment(FragmentFlags.NEWS_FRAGMENT);
        });

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(controller.getContext(), R.array.sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort_spnr.setAdapter(adapter);
        sort_spnr.setSelection(0);

        source_et.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                source_et.setText(v.getText());
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(controller.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        query_et.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                query_et.setText(v.getText());
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(controller.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        sort_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.sort_by);
                sortBy = choose[selectedItemPosition];
                Log.d("TAG", sortBy);
                if (sortBy.equals("Date")){
                    sortBy = "publishedAt";
                }else {
                    sortBy = null;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        date_from_btn.setOnClickListener(view13 -> getDateFrom());

        date_to_btn.setOnClickListener(view12 -> getDateTo());
    }

    private void choseAction() {
        if (TextUtils.isEmpty(source_et.getText()) && dateTo != null && dateFrom != null) {
            Log.d("TAG", "1");
            filterByAll(query, sortBy, source, dateFrom, dateTo);
        }
        if (dateFrom != null && dateTo != null) {
            Log.d("TAG", "2");
            filterByDate(query, sortBy, dateFrom, dateTo);
        }
        if (checkSource(source) == true) {
            Log.d("TAG", "3");
            Log.d("TAG", "+++" + source+"+++");
            filterBySource(query, sortBy, source);
        }else {
            Log.d("TAG", "4");
            ceckQuery(query);
            tmpData.setCall(active.pushArticles(query,sortBy));
        }
    }

    private void filterBySource(String query, String sortBy, String source) {
        tmpData.setCall(active.filterArticles(query, sortBy, source));
        if (tmpData.getCall() == null) {
            Toast toast = Toast.makeText(controller.getContext(),
                    "Something wrong", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void filterByDate(String query, String sortBy, String from, String to) {
        tmpData.setCall(active.filterArticles(query, sortBy, from, to));
        if (tmpData.getCall() == null) {
            Toast toast = Toast.makeText(controller.getContext(),
                    "Something wrong", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void filterByAll(String query, String sortBy, String source, String from, String to) {
        tmpData.setCall(active.filterArticles(query, sortBy, source));
        if (tmpData.getCall() == null) {
            Toast toast = Toast.makeText(controller.getContext(),
                    "Something wrong", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void getDateFrom() {


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(controller.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    date_from_tv.setText(dayOfMonth + "/"
                            + (monthOfYear + 1) + "/" + year);
                    String s = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    if (s.matches("\\d{4}-\\d{1}-\\d{1}")) {
                        dateFrom = String.valueOf(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    }
                    if (s.matches("\\d{4}-\\d{2}-\\d{1}")) {
                        dateFrom = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    }
                    if (s.matches("\\d{4}-\\d{1}-\\d{2}")) {
                        dateFrom = String.valueOf(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                    if (s.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        dateFrom = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                    Log.d("TAG", dateFrom);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void getDateTo() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(controller.getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    date_to_tv.setText(dayOfMonth + "/"
                            + (monthOfYear + 1) + "/" + year);
                    String t = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    if (t.matches("\\d{4}-\\d{1}-\\d{1}")) {
                        dateTo = String.valueOf(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    }
                    if (t.matches("\\d{4}-\\d{2}-\\d{1}")) {
                        dateTo = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                    }
                    if (t.matches("\\d{4}-\\d{1}-\\d{2}")) {
                        dateTo = String.valueOf(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                    if (t.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        dateTo = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getQuery() {
        query = String.valueOf(query_et.getText());
    }

    private void getSource() {
        Log.d("TAG",String.valueOf(source_et.getText()) );
        if (TextUtils.isEmpty(source_et.getText())){
        source = String.valueOf(source_et.getText());
        }
    }

    private boolean checkSource(String s){
        if(s.matches("\\d{0}")){
            source = null;
            return false;
        }else return true;}

    private void ceckQuery(String q){
        if(q.matches("\\d{0}")){
            query = null;}
    }
}



