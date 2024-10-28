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
        System.out.println();
        board = changeBoard(board, 4, 4, 2);
        board = changeBoard(board, 5, 5, 1);
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

    //指定の位置の状態を変える
    //xは左から何番目か
    //yは上から何番目か
    public static int[][] changeBoard(int[][] board, int x, int y ,int color){
        int xIndex = x-1;
        int yIndex = y-1;
        switch (color) {
            case 1:
            board[yIndex][xIndex] = 1;
                break;
            case 2:
            board[yIndex][xIndex] = 2;
                break;
            default:
                break;
        }
        return board;
    }
}


