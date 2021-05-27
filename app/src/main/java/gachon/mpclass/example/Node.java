package gachon.mpclass.example;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {

    public Node root_Node;
    public int size;

    public ArrayList<Node> child_node;
    public String data;

    public NodeFragment fragment;


    public Node(NodeFragment fragment,String data){

        this.data = data;

        this.root_Node = null;
        this.child_node = new ArrayList<>();

        this.fragment = fragment;
    }


    public void add_Child(Node child){

        child.root_Node = this;
        child_node.add(child);
    }

   /* //원하는 위치에 노드 추가
    public void add_Node(int index, String data){

        if (index<0||index>=size){
            throw new IndexOutOfBoundsException("index:"+index+"size:"+size);
        }

        Node newNode = new Node(data);
        Node preNode = getNode(index-1);
        newNode.child_node = preNode.child_node;
        preNode.child_node = newNode;
        size++;
    }

    //마지막에 추가
    public void add_to_Last(String data){
        add_Node(size-1,data);
    }

    //맨처음에 추가
    public void add_to_First(String data){

        Node newNode = new Node(data);
        newNode.child_node = root_Node.child_node;
        root_Node.child_node = newNode;
        size++;
    }

    //루트노드 삭제
    public void remove_first_Node(){
        Node node = root_Node.child_node;
        root_Node.child_node = null;
        root_Node = node;
        size--;
    }

    //특정한 노드 삭제
    public void remove(int index){

        if (index<0||index>=size){
            throw new IndexOutOfBoundsException("index:"+index+"size:"+size);
        }
        if (index==0){
            remove_first_Node();
        }else{
            Node preNode = getNode(index-1);
            Node removeNode = preNode.child_node;
            Node postNode = removeNode.child_node;
            preNode.child_node = postNode;
            size--;
        }
    }

    //특정 노드 data가져오기
    public Object get(int index){
        return getNode(index).data;
    }

    public Node getNode(int index){
        if (index<0||index>=size){
            throw new IndexOutOfBoundsException("index:"+index+"size:"+size);
        }else{
            Node node = root_Node.child_node;
            for(int i=0;i<index;i++){
                node = node.child_node;
            }
            return node;
        }
    }

    public int size(){
        return size;
    }
*/



}
