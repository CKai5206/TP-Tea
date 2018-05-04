package com.example.b0917.tp_tea;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link content_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link content_main} factory method to
 * create an instance of this fragment.
 */
public class content_main extends Fragment {

//    private OnFragmentInteractionListener mListener;
    private Button menu_btn,branches_btn,event_btn,game_btn;
    View inflatedView = null;
    public content_main() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("sdf", "Create");

    }
    private void getButtonView(){
        menu_btn = (Button)inflatedView.findViewById(R.id.menu_btn);
        branches_btn = (Button)inflatedView.findViewById(R.id.branches_btn);
        event_btn = (Button)inflatedView.findViewById(R.id.event_btn);
        game_btn = (Button)inflatedView.findViewById(R.id.game_btn);
    }

    public void setButtonEvent(){
        menu_btn.setOnClickListener(buttonListener);
        branches_btn.setOnClickListener(buttonListener);
        event_btn.setOnClickListener(buttonListener);
        game_btn.setOnClickListener(buttonListener);
    }

    private Button.OnClickListener buttonListener= new Button.OnClickListener(){
        @Override
        public void onClick(View view){
             switch (view.getId()){
                case R.id.menu_btn:;
                    Toast.makeText(getActivity(),"menu",Toast.LENGTH_LONG).show();
                break;
                case R.id.branches_btn:
                    Toast.makeText(getActivity(),"branches",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.event_btn:
                    Toast.makeText(getActivity(),"event",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.game_btn:
                    Toast.makeText(getActivity(),"game",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.inflatedView = inflater.inflate(R.layout.fragment_content_main, container, false);
        Log.v("sdf", "Create View");

        getButtonView();
        setButtonEvent();

        return this.inflatedView;
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
}
