package com.example.juice500.huffpuff;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivitySingleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainActivitySingleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainActivitySingleFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IMAGE = "imagePath";
    private static final String ARG_HUFF = "isHuff";
    private String imagePath;
    private boolean isHuff;
    private OnFragmentInteractionListener mListener;
    private Button buttonDelete;
    private Button buttonModify;
    private ImageView imageView;
    private ImageView iconView;
    private PhotoViewAttacher photoViewAttacher;

    public static MainActivitySingleFragment newInstance(String imagePath, boolean isHuff) {
        MainActivitySingleFragment fragment = new MainActivitySingleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE, imagePath);
        args.putBoolean(ARG_HUFF, isHuff);
        fragment.setArguments(args);
        return fragment;
    }

    public MainActivitySingleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.imagePath = getArguments().getString(ARG_IMAGE);
            this.isHuff = getArguments().getBoolean(ARG_HUFF);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity_single, container, false);

        this.buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        this.buttonModify = (Button) view.findViewById(R.id.buttonModify);
        this.imageView = (ImageView) view.findViewById(R.id.imageView);
        this.iconView = (ImageView) view.findViewById(R.id.iconView);

        Glide.with(MainActivitySingleFragment.this).load(imagePath).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                photoViewAttacher = new PhotoViewAttacher(imageView);
                return false;
            }
        }).into(imageView);

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
        //public void onFragmentInteraction(Uri uri);
    }

}
