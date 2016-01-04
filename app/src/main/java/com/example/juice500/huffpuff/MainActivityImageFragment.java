package com.example.juice500.huffpuff;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivityImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivityImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivityImageFragment extends Fragment {
    private static final String ARG_LIST = "imageList";
    private ArrayList<ImageItem> imageArrayList;
    private OnFragmentInteractionListener mListener;

    public static MainActivityImageFragment newInstance(ArrayList<ImageItem> imageArrayList) {
        MainActivityImageFragment fragment = new MainActivityImageFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, imageArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivityImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity_image, container, false);
        if (getArguments() != null) {
            this.imageArrayList = getArguments().getParcelableArrayList(ARG_LIST);
            GridView gridView = (GridView) view.findViewById(R.id.grid);
            gridView.setAdapter(new GridViewAdapter<>(getActivity(), R.layout.grid_item, this.imageArrayList));
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        //void onImageSelected(int position);
    }

}
