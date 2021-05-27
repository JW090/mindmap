package gachon.mpclass.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;

public class Mindmap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindmap);

    }

    private ArrayList<NodeFragment> nodeFragments;


    private NodeFragment createNodeFragment(NodeFragment nodeFragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        NodeFragment fragment = nodeFragment;
        fragmentTransaction.add(R.id.node_container,fragment);
        fragmentTransaction.commit();

        return fragment;
    }

    //노드 추가
    public NodeFragment add_Node(final NodeFragment parent, String text){

        final NodeFragment fragment = createNodeFragment(new NodeFragment(this,text));

        nodeFragments.add(fragment);

        if (parent!=null){
            parent.node.add_Child(fragment.node);
        }

        return fragment;

    }
}