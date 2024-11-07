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
            String[] enemyDirection = searchChangeDirections(board, xIndex, yIndex);
            for(String i:enemyDirection){
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println(); 
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

    //入力された値がボードの範囲内であるか確認する
    public static boolean inBoardRange(int x,int y){
        if(0 <= x && x <= 7){
            if(0 <= y && y <= 7){
                return true;
            }
        }
        System.out.println("ボードの範囲においてください");
        return false;
    }

    //指定したインデックスの場所におけるかを確認する
    public static boolean canPut(int[][] board,int x,int y){
        if(inBoardRange(x, y)){        //ボードの範囲内かを確認する
            int color = board[y][x];
            if(color == 0){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }


    //directionに指定した方向の横のコマの座標をインデックスで返す
    //方向の指定方法
    // ul up ur
    // le 的 ri
    // dl do dr 
    public static int[] getNext(int[][] board,int x,int y,String direction){
        int [] place = new int[2];
        //有効でない数字が入力されたらboardの範囲外である-1が返されるようになっている
        //(正直無理やりな気もするが他にいい方法が思いつかなかった)
        int nextX = -1; 
        int nextY = -1;
        switch (direction) {
            case "up":
                nextY = y-1;
                nextX = x;
                break;
            case "do":
                nextY = y+1;
                nextX = x;
                break;
            case "le":
                nextX = x-1;
                nextY = y;
                break;
            case "ri":
                nextX = x+1;
                nextY = y;
                break;
            case "ul":
                nextX = x-1;
                nextY = y-1;
                break;
            case "ur":
                nextX = x+1;
                nextY = y-1;
                break;
            case "dr":
                nextX = x+1;
                nextY = y+1;
                break;
            case "dl":
                nextX = x-1;
                nextY = y+1;
                break;
            default:
                System.out.println("有効な方向を引数に入れてください");
                break;
        }

        place[0] = nextX;
        place[1] = nextY;
        return place;
    }

    public static String[] searchChangeDirections(int[][] board,int x,int y){
        int color = board[y][x];
        String[] directions = {"ul","up","ur","le","ri","dl","do","dr"};
        String[] enemyDirection = new String[8];
        int index = 0;
        for(String direction:directions){
            int[] aroundPlace = getNext(board, x, y, direction);

            int aroundX = aroundPlace[0];
            int aroundY = aroundPlace[1];
            int aroundColor = board[aroundY][aroundX];
            
            if(aroundColor != color && aroundColor != 0){
                enemyDirection[index] = direction;
                index += 1;
            }
        }
        return enemyDirection;
    }
}


