import java.util.Arrays;

//空白: 0
//白　: 1
//黒　: 2

//列: row
//行: column

public class Main {
    public static void main(String [] args){
        System.out.println("hello");
        int[][] board = makeBoard(8);
        outputBoard(board);
    }

    //8×8のマス目を作る
    public static int[][] makeBoard(int size){
        int [][]boared = new int[size][size];
        for(int[] column:boared){
            Arrays.fill(column, 0); //すべてのマスを0で埋める(配列定義時はすべて0だからいらない気もするが一応)
        }
        return boared;
    }

    //オセロの譜面の二次元配列を出力する
    public static void outputBoard(int[][] boared){
        for(int[] column:boared){
            for(int i:column){
                System.out.print(i);
                System.out.print("　");//横がぎゅうぎゅうになって見にくいから
            }
            System.out.println();
        }
    }
}


