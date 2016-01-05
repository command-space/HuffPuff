package com.example.juice500.huffpuff;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivityFolderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivityFolderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivityFolderFragment extends ListFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST = "folderList";
    private ArrayList<ImageItem> folderArrayList;
    private OnFragmentInteractionListener mListener;

    public static MainActivityFolderFragment newInstance(ArrayList<ImageItem> folderArrayList) {
        MainActivityFolderFragment fragment = new MainActivityFolderFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, folderArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivityFolderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_activity_folder, container, false);
        (getActivity()).setTitle("Album");
        if (getArguments() != null)
            this.folderArrayList = getArguments().getParcelableArrayList(ARG_LIST);
        if(this.folderArrayList != null)
            setListAdapter(new ListViewAdapter<>(getActivity(), R.layout.list_item, this.folderArrayList));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onFolderSelected(folderArrayList.get(position).getName());
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
        void onFolderSelected(String folderName);
    }

}
