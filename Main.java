import java.util.Arrays;
import java.util.Scanner;

//空白: 0
//白　: 1
//黒　: 2

//列: row
//行: column

public class Main {
    private static final Scanner sc = new Scanner(System.in); // 1箇所で管理
    public static void main(String [] args){
        int nextColor = 1;
        String color;
        int[][] board = makeBoard(8);
        if(nextColor == 1){
            color = "白";
        }else{
            color = "黒";
        }

        board = setBoard(board);

        outputBoardCUI(board);
        //System.out.println(countColor(board, 1));

        while(canContinue(board)){
            int nowColor = nextColor;
            if(nextColor == 1){
                color = "白";
            }else{
                color = "黒";
            }

            if(canPutSomewhere(board, nextColor)){
                ;
            }else{
                continue;
            }

            System.out.println("今は"+color+"のターンです"); //もちろん分かりやすいメッセージに変えます 追記変えました
            int[] place = hearPlace();
            int xIndex = place[0];
            int yIndex = place[1];

            if(canPut(board, xIndex, yIndex,nowColor)){   //今のcanputはひっくり返せる場所か判断できないので追加します 追記追加しました
                board = changeBoard(board, xIndex, yIndex, nowColor);
                board = turnOverAll(board, xIndex, yIndex);

                nextColor = nextColor(nowColor);
            }else{
                System.out.println("そこにはおけません");
                nextColor = nowColor;   //置けなかったからもう一回
            }
    
            outputBoardCUI(board);
            System.out.println(countColor(board, 2));
        }
        sc.close();
        int whiteNum = countColor(board, 1);
        int blackNum = countColor(board, 2);
        System.out.println("白が"+whiteNum+"個");
        System.out.println("黒が"+blackNum+"個");
        if(whiteNum > blackNum){
            System.out.println("白の勝ち");
        }else if(whiteNum == blackNum){
            System.out.println("引き分け");
        }else{
            System.out.println("黒の勝ち");
        }

    }

    //座標を入力させる
    public static int[] hearPlace(){
        System.out.print("x = ");
        int x = sc.nextInt();
        System.out.print("y = ");
        int y = sc.nextInt();

        int xIndex = x-1;
        int yIndex = y-1;
        int[] place = {xIndex,yIndex};
        //sc.close(); なぜか動かなくなるから消した理由は知らん
        return place;
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

    //オセロの譜面の二次元配列を出力する(数字状態)
    public static void outputBoard(int[][] board){
        for(int[] column:board){
            for(int i:column){
                System.out.print(i);
                System.out.print("　");//横がぎゅうぎゅうになって見にくいから
            }
            System.out.println();
        }
        System.out.println(""); //2個の譜面が縦に繋がって見えにくいから間を空けるため
    }

    //見やすく表示(作り途中)
    public static void outputBoardCUI(int[][] board){
        //ターミナルが黒なので白と黒が逆になる
        String white = "●";
        String black= "○";
        String space = ".";

        int yCoordinate = 1;

        System.out.print("  ");
        for(int i=1; i<9; i++){
            System.out.print(i); //x座標のメモリの表示
            System.out.print("  ");
        }
        System.out.println(""); //改行する
        for(int[] column:board){
            System.out.print(yCoordinate); //y座標のメモリの表示
            System.out.print(" ");
            yCoordinate += 1;
            for(int i:column){
                switch (i) {
                    case 1:
                        System.out.print(white);
                        System.out.print(" ");
                        break;
                    case 2:
                        System.out.print(black);
                        System.out.print(" ");
                        break;
                    case 0:
                        System.out.print(space);
                        System.out.print(" ");
                    default:
                        break;
                }
                System.out.print(" ");//横がぎゅうぎゅうになって見にくいから
            }
            System.out.println();
        }
        System.out.println(""); //2個の譜面が縦に繋がって見えにくいから間を空けるため
    }

    //指定の位置のインデックスの状態を変える
    //xは左から何番目か
    //yは上から何番目か
    public static int[][] changeBoard(int[][] board, int x, int y ,int color){
        int[][] interimBoard = board;
        switch (color) {
            case 1:
                interimBoard[y][x] = 1;
                break;
            case 2:
                interimBoard[y][x] = 2;
                break;
            default:
                break;
            }
        return interimBoard;
    }

    //入力された値がボードの範囲内であるか確認する
    public static boolean inBoardRange(int x,int y){
        if(0 <= x && x <= 7){
            if(0 <= y && y <= 7){
                return true;
            }
        }
        //System.out.println("ボードの範囲においてください");
        return false;
    }

    //何も置いていない位置か確認する
    public static boolean notPlacedAnything(int[][] board,int x,int y){
        int color = board[y][x];
            if(color == 0){
                return true;
            }else{
                return false;
            }
    }

    //指定したインデックスの場所におけるかを確認する
    public static boolean canPut(int[][] board,int x,int y,int color){
        if(inBoardRange(x, y) && notPlacedAnything(board, x, y) && canTurnOverAll(board, x, y,color)){        //ボードの範囲内かを確認する＋何も置いていない位置か確認する
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

    //置いた場所の周りを探索→自分と違うコマがある方向を返す
    public static String[] searchChangeDirections(int[][] board,int x,int y,int color){
        String[] directions = {"ul","up","ur","le","ri","dl","do","dr"};
        String[] enemyDirection = new String[8];
        int index = 0;
        for(String direction:directions){
            int[] aroundPlace = getNext(board, x, y, direction);

            int aroundX = aroundPlace[0];
            int aroundY = aroundPlace[1];
            if(inBoardRange(aroundX, aroundY)){
                int aroundColor = board[aroundY][aroundX];
                if(aroundColor != color && aroundColor != 0){
                    enemyDirection[index] = direction;
                    index += 1;
                }
            }else{
                continue;
            }
        }
        return enemyDirection;
    }

    //引数に指定した方向を探索→ひっくり返せるものがあるか
    public static boolean canChangeDirection(int[][] board,int x,int y,String direction,int color){
        int enemyColor;
        if(color == 1){
            enemyColor = 2;
        }else{
            enemyColor = 1;
        }
        while(true){
            int[] nextPlace = getNext(board, x, y, direction);
            int nextX = nextPlace[0];
            int nextY = nextPlace[1];
            if(inBoardRange(nextX, nextY)){
                int nextColor = board[nextY][nextX];
                if(nextColor == enemyColor){
                    x = nextX;
                    y = nextY;
                    continue;
                }else if(nextColor == color){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    //canChangeDirectionと同じ感じで探索してひっくり返す(一方向のみ対応)
    public static int[][] changeDirection(int[][] board,int x,int y,String direction){
        int color = board[y][x];
        int enemyColor;
        if(color == 1){
            enemyColor = 2;
        }else{
            enemyColor = 1;
        }
        while(true){
            int[] nextPlace = getNext(board, x, y, direction);
            int nextX = nextPlace[0];
            int nextY = nextPlace[1];
            if(inBoardRange(nextX, nextY)){
                int nextColor = board[nextY][nextX];
                if(nextColor == enemyColor){
                    board = changeBoard(board, nextX, nextY, color);
                    x = nextX;
                    y = nextY;
                    continue;
                }else if(nextColor == color){
                    break;
                }else{
                    break;
                }
            }
        }
    return board;
    }

    //裏返す(全方向対応)
    public static int[][] turnOverAll(int[][] board,int x,int y){
        int color = board[y][x];
        String[] enemyDirection = searchChangeDirections(board, x, y,color);
        for(String i:enemyDirection){
            if(i != null && canChangeDirection(board, x, y, i,color)){
                System.out.println(i);
                board = changeDirection(board, x, y, i);
            }
        }
        return board;
    }

    //裏返せるか確認(全方向)
    public static boolean canTurnOverAll(int[][] board,int x,int y,int nowColor){
        boolean flag = false;
        String[] enemyDirection = searchChangeDirections(board, x, y,nowColor);
        for(String i:enemyDirection){
            //System.out.println(i);
            if(i != null && canChangeDirection(board, x, y, i,nowColor)){
                System.out.println(i);
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    public static int nextColor(int oldColor){
        int newColor;
        if(oldColor == 1){
            newColor = 2;
        }else if(oldColor == 2){
            newColor = 1;
        }else{
            newColor = 0;
            System.out.println("oldColorにおかしい数が入ってるよ");
        }
        return newColor;
    }

    public static boolean canContinue(int[][] board){
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                int color = board[y][x];
                if(color == 0){
                    boolean canput = canPut(board, x, y, 1)||canPut(board, x, y, 0);
                    if(canput){
                        return true;
                    }
            }
        }
    }
        return false;
    }

    // 指定された色の個数を数えるメソッド
    public static int countColor(int[][] board, int color) {
        return (int) Arrays.stream(board)             // Stream<int[]> を作成
        .flatMapToInt(Arrays::stream)             // Stream<int[]> を IntStream に変換
        .filter(num -> num == color)              // 指定された色に一致する要素をフィルタ
        .count();                                 // フィルタされた要素の数をカウント
    }

    public static boolean canPutSomewhere(int[][] board, int color){
        boolean flag = false;
        for(int x = 0;x<8;x++){
            for(int y = 0;y<8;y++){
                if(canPut(board, x, y,color)){
                    flag = true;
                }
            }
        }
        return flag;
    }
}


