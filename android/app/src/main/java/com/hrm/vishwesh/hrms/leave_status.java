package com.hrm.vishwesh.hrms;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link leave_status.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link leave_status#newInstance} factory method to
 * create an instance of this fragment.
 */
public class leave_status extends Fragment implements BackgroundLeaveStatus.goBackToParentFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView sick,casual,privillages;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public leave_status() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment leave_status.
     */
    // TODO: Rename and change types and number of parameters
    public static leave_status newInstance(String param1, String param2) {
        leave_status fragment = new leave_status();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }
    public void apply_leave_click(View view){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_leave_status, container, false);

        // Inflate the layout for this fragment

        sick=(TextView)rootView.findViewById(R.id.remaining_sick);
        casual=(TextView)rootView.findViewById(R.id.remaining_casual);
        privillages=(TextView)rootView.findViewById(R.id.remaining_privillages);
        return  rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HashMap<String,String> params=new HashMap<>();
        params.put("emp_id","1");
//            params.put("policy_id","STD009");
        Httpcall httpCall = new Httpcall();
        httpCall.setParams(params);
        BackgroundLeaveStatus bg_ls=new BackgroundLeaveStatus(this);
        bg_ls.execute(httpCall);

    }

    @Override
    public void setResponse(Employee e) {
//        Log.d("Leave status", "------->>> Ai gayu..." + e.toString());
      sick.setText(e.getRemaining_sick());
      casual.setText(e.getRemaining_casual());
      privillages.setText(e.getGetRemaining_privillages());


    }
}
