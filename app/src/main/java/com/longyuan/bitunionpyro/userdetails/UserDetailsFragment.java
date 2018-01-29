package com.longyuan.bitunionpyro.userdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.pojo.action.userDetails.UserDetailsRequest;
import com.longyuan.bitunionpyro.pojo.action.userDetails.UserDetailsResponse;
import com.longyuan.bitunionpyro.utils.Constant;
import com.longyuan.bitunionpyro.utils.SharedPreferencesHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mUserName;
    private String mParam2;


    @BindView(R.id.textView)
    TextView mTextView;

    @BindView(R.id.textView2)
    TextView mTextView2;

    @BindView(R.id.textView3)
    TextView mTextView3;

    @BindView(R.id.textView4)
    TextView mTextView4;

    @BindView(R.id.textView5)
    TextView mTextView5;

    @BindView(R.id.textView6)
    TextView mTextView6;


    @Inject
    BUService mBUservice;


    // private OnFragmentInteractionListener mListener;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailsFragment newInstance(String userName) {
        UserDetailsFragment fragment = new UserDetailsFragment();

       Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_PARAM1);

        }
        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/",getContext()))
                .build().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, view);

        // TODO Use fields...

        loadData();

        return view;
    }

    private void loadData() {


        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();

        userDetailsRequest.setSession(SharedPreferencesHelper.getPrefValue(getContext(), Constant.PREF_SESSION));
        userDetailsRequest.setQueryusername(mUserName);
        userDetailsRequest.setAction("profile");
        userDetailsRequest.setUsername(SharedPreferencesHelper.getPrefValue(getContext(), Constant.PREF_USER_NAME));


        mBUservice.getUserDetails(userDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> updateData(data),
                        throwable -> handleError(throwable));


    }


    private void handleError(Throwable throwable) {
    }

    private void updateData(UserDetailsResponse data) {
        mTextView.setText(data.getMemberinfo().getPostnum());
        mTextView2.setText(data.getMemberinfo().getThreadnum());
        mTextView3.setText(data.getMemberinfo().getCredit());
        mTextView4.setText(data.getMemberinfo().getBday());
        mTextView5.setText(data.getMemberinfo().getEmail());
        mTextView6.setText(data.getMemberinfo().getSite());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
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
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
