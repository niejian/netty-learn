import java.util.Scanner;


/**
 * @desc: PACKAGE_NAME.Wuziqi
 * @author: niejian9001@gmail.com
 * @date: 2019-09-30 10:52
 */
public class Wuziqi {

    static String play01;
    static String play02;
    static int row, col;

    public static void main(String[] args) {
        char[][] board = new char[12][12];
        showBoard(board);
        input(board);

    }

    private static void showBoard(char[][] board) {
        System.out.print("\t   ");
        for (int i = 0; i < 12; i++) {
            System.out.print("\t" + (i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {

            System.out.print(i + "\t|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("\t" + board[i][j] + "\t|");
            }
            System.out.println(
                    "\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void input(char[][] board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个用户姓名：");
        play01 = sc.nextLine();
        System.out.println("请输入第二个用户姓名：");
        play02 = sc.nextLine();
        System.out.println("按任意键下棋。。。。。");
        boolean flag = false;
        do {
            drawInfo(board, play01, 'O');
            flag = isWin(play01, board, row, col, 'O');
            if (flag == true) {
                System.out.println(play01 + "赢");
                return;
            }
            drawInfo(board, play02, 'X');
            flag = isWin(play01, board, row, col, 'X');
            if (flag == true) {
                System.out.println(play02 + "赢");
                return;
            }
//判断格子是否已画满
            flag = isGameOver(board);

        } while (flag == false);
        System.out.println("游戏已结束。。。。");
    }

    // 判断输赢
    public static boolean isWin(String play, char[][] board, int x, int y, char c) {
        int count, a;
// 对角线 左上角
        for (count = 0, a = 0; (x - a >= 0) && (y - a >= 0) && (board[x - a][y - a] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }
// 对角线 右下角
        for (a = 1; (x + a < 12) && (y + a < 12) && (board[x + a][y + a] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }

// 同一列
        for (a = 0, count = 0; (x - a >= 0) && (board[x - a][y] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }

        for (a = 1; (x + a < 12) && (board[x + a][y] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }

// 同一行
        for (a = 0, count = 0; (y - a >= 0) && (board[x][y - a] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }
        for (a = 1; (y + a < 12) && (board[x][y + a] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }
// 反对脚线
        for (a = 0, count = 0; (x - a >= 0) && (y + a < 12) && (board[x - a][y + a] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }
        for (a = 1; (y - a >= 0) && (x + a < 12) && (board[x + a][y - a] == c); a++) {
            count++;
            if (count == 5)
                return true;
        }

        return false;
    }

    public static void drawInfo(char[][] board, String play, char c) {
        Scanner sc = new Scanner(System.in);

        System.out.println(play + "请输入坐标点（0 0）：");
        row = sc.nextInt();
        col = sc.nextInt();
        if (board[row][col] == 'O' || board[row][col] == 'X') {
            System.out.println("不可用");
            drawInfo(board, play, c);
        } else {
            board[row][col] = c;
        }
        showBoard(board);
    }

    // 是否棋盘已满
    public static boolean isGameOver(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }

}
