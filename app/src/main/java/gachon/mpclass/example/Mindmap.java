package gachon.mpclass.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Mindmap extends AppCompatActivity {

    private Random randomFragmentMargins;
    private ArrayList<NodeFragment> nodeFragments;
    public NodeFragment movingFragment;

    public NodeFragment fragment;

    private Draw draw;

    private int prevX, prevY;

    private ImageButton btn_root;
    private TextView str;


    //메인
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindmap);

        getSupportActionBar().hide();

        randomFragmentMargins = new Random();

        ViewGroup drawViewContainer = (ViewGroup)findViewById(R.id.mindmap_area);
         draw = new Draw(this);
         drawViewContainer.addView(draw);

         final Mindmap mindmap = this;

         nodeFragments = new ArrayList<>();

         prevX=prevY=-1;

         btn_root = findViewById(R.id.btn_mind_root);
         str = findViewById(R.id.str_text);

        Intent i = getIntent();
        String start = i.getStringExtra("starting");
        str.setText(start);

        btn_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mindmap);
                builder.setTitle("단어 입력");

                final EditText input = new EditText(mindmap);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_Node(fragment,input.getText().toString());
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });


    }



    //상단바와 타이틀바의 길이를 합한 값값
   public int getBarsHeight(){

        View decorView = getWindow().getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        View contentView = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        int[] location = new int [2];
        contentView.getLocationInWindow(location);
        int titleBarHeight = location[1]=statusBarHeight;

        return statusBarHeight + titleBarHeight;
    }


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
        fragment.onAddToLayout = new Runnable() {
            @Override
            public void run() {
                int x_margin = randomFragmentMargins.nextInt(200)-99;
                int y_margin = randomFragmentMargins.nextInt(200)-99;

                if (parent !=null){
                    x_margin+=parent.node.x_margin;
                    y_margin+=parent.node.y_margin;
                }

                moveTo(fragment,x_margin,y_margin);

            }
        };

        return fragment;

    }

    private NodeFragment _createNodeFragmentHierarchy(final Node node)
    {
        final NodeFragment fragment = createNodeFragment(new NodeFragment(this, node));

        fragment.onAddToLayout = new Runnable() {
            @Override
            public void run() {
                moveTo(fragment, node.x_margin, node.y_margin);
            }
        };

        nodeFragments.add(fragment);

        for (Node child : node.child_node)
        {
            _createNodeFragmentHierarchy(child);
        }

        return fragment;
    }

    public NodeFragment createNodeFragmentHierarchy(Node root)
    {
        NodeFragment rootFragment = _createNodeFragmentHierarchy(root);
        rootFragment.makeRoot();

        return rootFragment;
    }


    //노드삭제
    public void remove_node(NodeFragment fragment){

        for (Node child : fragment.node.child_node){
            remove_node(child.fragment);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

        nodeFragments.remove(fragment);

    }

    //노드 옮기기
    public static void moveTo(NodeFragment fragment, int x, int y){
        moveTo(fragment.node, fragment.getView(),x,y);
    }
    public static void moveTo(Node node, View view, int x, int y){
        node.x_margin = x;
        node.y_margin = y;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        params.setMargins(x,y,0,0);
        view.requestLayout();
    }
    public static void move(NodeFragment fragment, int dx, int dy)
    {
        move(fragment.node, fragment.getView(), dx, dy);
    }

    public static void move(Node node, View view, int dx, int dy)
    {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        moveTo(node, view, params.leftMargin + dx, params.topMargin + dy);
    }

    //노드 리스트 얻기
    public ArrayList<NodeFragment> getNodeFragments(){
       return nodeFragments;
    }

    //노드 한번에 이동
    public static void moveRecursively(NodeFragment fragment, int dx, int dy){

        try{
            move(fragment,dx,dy);
        }catch (Exception e){

        }

        for(Node child:fragment.node.child_node){
            moveRecursively(child.fragment,dx,dy);
        }
    }

    //선택된 노드 이동
    public boolean onTouchEvent(MotionEvent event){

        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = x;
                prevY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int dltX = x - prevX, dltY = y-prevY;

                if (movingFragment !=null){
                    moveRecursively(movingFragment,dltX,dltY);
                }
                else
                {
                    for (NodeFragment fragment: nodeFragments){
                        move(fragment,dltX,dltY);
                    }
                }

                prevX=x;
                prevY=y;
                break;

            case MotionEvent.ACTION_UP:
                if (movingFragment!=null){
                    movingFragment=null;
                }

                prevX=prevY=-1;
                break;
        }

        return false;

    }


}