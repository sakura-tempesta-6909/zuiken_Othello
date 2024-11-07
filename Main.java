import java.util.Arrays;
import java.util.Scanner;

//空白: 0
//白　: 1
//黒　: 2

//列: row
//行: column

public class Main {
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        int[][] board = makeBoard(8);
        board = setBoard(board);
        outputBoard(board);

        System.out.print("x = ");
        int x = sc.nextInt();
        System.out.print("y = ");
        int y = sc.nextInt();

        int xIndex = x-1;
        int yIndex = y-1;

        if(canPut(board, xIndex, yIndex)){
            board = changeBoard(board, xIndex, yIndex, 1); 
        }else{
            System.out.println("そこにはおけません");
        }

        outputBoard(board);

        sc.close();
    }

    //8×8のマス目を作る
    public static int[][] makeBoard(int size){
        int [][]boared = new int[size][size];
        for(int[] column:boared){
            Arrays.fill(column, 0); //すべてのマスを0で埋める(配列定義時はすべて0だからいらない気もするが一応)
        }
        return boared;
    }

    //真ん中に対角線状に白と黒が置いてある初期状態にする
    public static int[][] setBoard(int[][] board){
        int size = board.length;
        int center = size/2;

        board = changeBoard(board, center-1, center-1, 1);
        board = changeBoard(board, center, center, 1);
        board = changeBoard(board, center-1, center,2 );
        board = changeBoard(board, center, center-1, 2);

        return board;
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
        System.out.println(""); //2個の譜面が縦に繋がって見えにくいから間を空けるため
    }

    //指定の位置のインデックスの状態を変える
    //xは左から何番目か
    //yは上から何番目か
    public static int[][] changeBoard(int[][] board, int x, int y ,int color){
        switch (color) {
            case 1:
                board[y][x] = 1;
                break;
            case 2:
                board[y][x] = 2;
                break;
            default:
                break;
            }
        return board;
    }

    public static boolean canPut(int [][] board,int x,int y){
        int color = board[y][x];
        System.out.println(color);
        if(color == 0){
            return true;
        }else{
            return false;
        }
    }

    //directionに指定した方向の横のコマの座標をインデックスで返す
    //方向の指定方法
    // ul up ur
    // le 的 ri
    // dl do dr 
    public static int[] getNext(int[][] board,int x,int y,String direction){
        int xIndex = x - 1;
        int yIndex = y - 1;
        return 
    }
}


