package gachon.mpclass.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NodeFragment extends Fragment {

    public Node node;

    private ImageButton btn_node,starting_btn;
    private TextView node_text;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NodeFragment() {
        // Required empty public constructor
    }

    public NodeFragment(Mindmap mindmap, String text) {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NodeFragment newInstance(String param1, String param2) {
        NodeFragment fragment = new NodeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_node,container,false);

        btn_node = rootView.findViewById(R.id.node_img);
        starting_btn = rootView.findViewById(R.id.btn_start);

        registerForContextMenu(btn_node);

        // Inflate the layout for this fragment
        return rootView;


    }

    //수정삭제메뉴
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu,v,menuInfo);

        MenuInflater mi = getActivity().getMenuInflater();
        if(v==btn_node||v==starting_btn){
            mi.inflate(R.menu.node_menu,menu);
        }

    }

    public boolean onContextItemSelected(@NonNull MenuItem item){

        switch (item.getItemId()){
            case R.id.edit:
            return true;
            case R.id.remove: remove_text();
            return true;
        }
        return false;
    }

    MainActivity activity;

    //수정누르면 텍스트 팝업으로 입력받기
    public void get_edit_text(){

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        final EditText editText = new EditText(activity);
        alert.setView(editText);

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String edit = editText.getText().toString();
                edit_text(edit);
            }
        });
    }





    //텍스트 수정
    public void edit_text(String edit){

        node.data = edit;
        this.node_text.setText(edit);

    }

    //텍스트 삭제
    public void remove_text(){
        node.data = null;
        this.node_text.setText(null);
    }


}