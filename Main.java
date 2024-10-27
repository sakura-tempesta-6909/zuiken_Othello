import java.util.Arrays;

public class Main {
    public static void main(String [] args){
        System.out.println("hello");
        makeBoard(8);
    }
    public static int[][] makeBoard(int size){
        int [][]boared = new int[size][size];
        Arrays.fill(boared, 0); //すべてのマスを0で埋める(配列定義時はすべて0だからいらない気もするが一応)
        return boared;
    }
}

//列: row
//行: column
